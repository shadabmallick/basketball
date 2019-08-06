package com.sport.supernathral.pushnotification;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.HomePage;

import org.json.JSONObject;

import java.util.List;

import me.pushy.sdk.Pushy;

import static android.content.Context.ACTIVITY_SERVICE;

public class PushReceiver extends BroadcastReceiver {

    private Context context;
    private Shared_Preference shared_preference;

    private String siglechatScreen = "com.sport.supernathral.activity.ChatSingle";
    private String groupchatScreen = "com.sport.supernathral.activity.ChatGroup";

    @Override
    public void onReceive(Context context, Intent intent) {
        String notificationTitle = context.getResources().getString(R.string.app_name);
        String notificationText = "";
        this.context = context;
        shared_preference = new Shared_Preference(context);


        if (shared_preference.isLogin()){

            // {"id": 1, "success": true, "message": "Hello World"}

            // Attempt to extract the "message" property from the payload: {"message":"Hello World!"}
            if (intent.getStringExtra("body") != null) {
                notificationText = intent.getStringExtra("body");
            }

            int id = intent.getIntExtra("id", 0);
            String message = intent.getStringExtra("body");
            String content = intent.getStringExtra("content");
            //boolean success = intent.getBooleanExtra("success", false);

            Log.d("MyApp", "Pushy id: " + id);
            Log.d("MyApp", "Pushy content: " + content);
            Log.d("MyApp", "Pushy message: " + message);
            //Log.d("MyApp", "Pushy success: " + success);
            Log.d("MyApp", "Pushy notificationText: " + notificationText);

            showNotification(notificationTitle, notificationText);




            try {
                JSONObject object = new JSONObject(content);

                if (object.optString("type").equals("User")){

                    refreshSingleChatData(context, content);
                }else {

                    refreshGroupChatData(context, content);
                }


            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }


    private void showNotification(String title, String message){

        // Prepare a notification with vibration, sound and lights
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.icon0)
                .setContentTitle(title)
                .setContentText(message)
                .setLights(Color.RED, 1000, 1000)
                .setVibrate(new long[]{0, 400, 250, 400})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        new Intent(context, HomePage.class), PendingIntent.FLAG_UPDATE_CURRENT));

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context);

        // Get an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);

        // Build the notification and display it
        notificationManager.notify(1, builder.build());

    }



    static void refreshSingleChatData(Context context, String message) {
        Intent intent = new Intent(Common.Key_SingleChatNoti);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }


    static void refreshGroupChatData(Context context, String message) {
        Intent intent = new Intent(Common.Key_GroupNoti);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }




    private boolean applicationInForeground() {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;

        if (services.get(0).processName.equalsIgnoreCase(context.getPackageName())) {
            isActivityFound = true;
        }

        return isActivityFound;
    }


    private String getForegroundActivity(){
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        //Log.d("TAG", "CURRENT Activity ::" + taskInfo.get(0).topActivity.getClassName());
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        componentInfo.getPackageName();

        return taskInfo.get(0).topActivity.getClassName();
    }


}
