package com.sport.supernathral.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.AdapterClass.ChatSingleAdapter;
import com.sport.supernathral.DataModel.ChatData;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.Fragment.Chats;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.ConnectivityReceiver;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatSingle extends AppCompatActivity
        implements ChatSingleAdapter.onItemClickListner {

    ImageView iv_back, imgAttachment, imgSend;
    CircleImageView profile_image;
    TextView tv_name;
    RecyclerView recycler_chat;
    EditText edt_message;

    ChatListData chatListData;
    ProgressDialog pd;
    ArrayList<ChatData> chatListDataArrayList;

    GlobalClass globalClass;
    Shared_Preference preference;
    ChatSingleAdapter chatSingleAdapter;

    Bitmap resized_bitmap;
    File p_image1;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 333;


    String TAG = "chat list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_chat_screen);
        intViews();

        this.registerReceiver(mMessageReceiver,
                new IntentFilter(Common.Key_SingleChatNoti));

    }


    private void intViews() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(this);


        iv_back = findViewById(R.id.iv_back);
        imgAttachment = findViewById(R.id.imgAttachment);
        imgSend = findViewById(R.id.imgSend);
        edt_message = findViewById(R.id.edt_message);
        profile_image = findViewById(R.id.profile_image);
        tv_name = findViewById(R.id.tv_name);
        recycler_chat = findViewById(R.id.recycler_chat);


        LinearLayoutManager layoutManager= new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        recycler_chat.setLayoutManager(layoutManager);

        chatListDataArrayList = new ArrayList<>();
        chatSingleAdapter = new ChatSingleAdapter(ChatSingle.this,
                chatListDataArrayList, this);
        recycler_chat.setAdapter(chatSingleAdapter);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            chatListData = (ChatListData) bundle.getSerializable("info");

            tv_name.setText(chatListData.getUser_name());

            if (!chatListData.getUser_image().isEmpty()) {
                Picasso.with(getApplicationContext())
                        .load(chatListData.getUser_image())
                        .placeholder(R.mipmap.avatar_gray)
                        .into(profile_image);
            }


            getChatList(globalClass.getId(), chatListData.getReceiver_id(),
                    chatListData.getChat_type());
        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ConnectivityReceiver.isConnected()){
                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.check_network),
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, false).show();
                    return;
                }


                checkPermission();

            }
        });


        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ConnectivityReceiver.isConnected()){
                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.check_network),
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, false).show();
                    return;
                }

                if (edt_message.getText().toString().trim().isEmpty()){
                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.typemessage),
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, false).show();
                }else {
                    sendTextMessage();
                }

            }
        });


    }


    private void getChatList(final String sender_id, final String receiver_id,
                             final String chat_type) {

        chatListDataArrayList = new ArrayList<>();

        String tag_string_req = "CHAT_LIST";

        final String url = AppConfig.CHAT_MSG_LIST;

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1) {

                        JSONObject receiver = main_object.getJSONObject("receiver");
                        String receiver_name = receiver.optString("receiver_name");
                        String receiver_image = receiver.optString("receiver_image");



                        JSONArray data = main_object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);

                            ChatData chatData = new ChatData();
                            chatData.setId(object.optString("id"));
                            chatData.setType(object.optString("type"));
                            chatData.setSender_id(object.optString("sender_id"));
                            chatData.setReceiver_id(object.optString("receiver_id"));
                            chatData.setMessage(object.optString("message"));
                            chatData.setMessage_type(object.optString("message_type"));
                            chatData.setDatetime(object.optString("datetime"));
                            chatData.setDelete_flag(object.optString("delete_flag"));
                            chatData.setIs_active(object.optString("is_active"));
                            chatData.setEntry_date(object.optString("entry_date"));
                            chatData.setModified_date(object.optString("modified_date"));
                            chatData.setReceiver_name(object.optString("receiver_name"));
                            chatData.setReceiver_image(object.optString("receiver_image"));
                            chatData.setSender_name(object.optString("sender_name"));
                            chatData.setSender_image(object.optString("sender_image"));

                            if (object.optString("message_type").equals("3")){
                                chatData.setImage_from("web");
                            }else {
                                chatData.setImage_from("");
                            }


                            if (globalClass.getId()
                                    .equals(object.optString("sender_id"))){
                                chatData.setIs_me(true);
                            }else {
                                chatData.setIs_me(false);
                            }

                            chatListDataArrayList.add(chatData);

                        }


                    }

                    chatSingleAdapter = new ChatSingleAdapter(ChatSingle.this,
                            chatListDataArrayList, ChatSingle.this);
                    recycler_chat.setAdapter(chatSingleAdapter);
                    chatSingleAdapter.notifyDataSetChanged();

                    pd.dismiss();

                } catch (Exception e) {

                    /*FancyToast.makeText(getApplicationContext(),
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();*/

                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("sender_id", sender_id);
                params.put("receiver_id", receiver_id);
                params.put("chat_type", chat_type);

                Log.d(TAG ,"URL- " + url);
                Log.d(TAG ,"PARAM - " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }



    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private static DateFormat dateOnlyformat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public void sendTextMessage() {

        Date today = Calendar.getInstance().getTime();
        String message = edt_message.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {

            ChatData chatData = new ChatData();
            chatData.setMessage(message);
            chatData.setType(chatListData.getChat_type());
            chatData.setMessage_type("1");
            chatData.setMessage_type_int("1");
            chatData.setDatetime(dateFormat.format(today));
            chatData.setIs_me(true);
            chatData.setEntry_date(dateOnlyformat.format(today));
            chatData.setSender_id(globalClass.getId());
            chatData.setReceiver_id(chatListData.getReceiver_id());
            chatData.setType(chatListData.getChat_type());


            edt_message.setText("");
            chatListDataArrayList.add(chatData);
            chatSingleAdapter.notifyDataSetChanged();

            recycler_chat.smoothScrollToPosition(chatListDataArrayList.size() - 1);
           // Log.d(TAG ,"array- " + chatListDataArrayList.size());

            postChat(chatData);
        }
    }



    private boolean checkPermission() {

        List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(ChatSingle.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(ChatSingle.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(ChatSingle.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) ChatSingle.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

            return false;
        } else {

            selectImage();

        }


        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                    //list is still empty
                    selectImage();
                } else {
                    checkPermission();
                    // Permission Denied

                }
                break;
        }
    }

    public void selectImage() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChatSingle.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_picture_select, null);
        dialogBuilder.setView(dialogView);

        ImageView iv_gallery = dialogView.findViewById(R.id.iv_gallery);
        ImageView iv_camera = dialogView.findViewById(R.id.iv_camera);

        final AlertDialog alertDialog = dialogBuilder.create();

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        PICK_IMAGE_REQUEST);

                alertDialog.dismiss();

            }
        });


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                alertDialog.dismiss();

            }
        });


        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        p_image1 = null;

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG , "PICK_IMAGE_REQUEST - "+uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                sendImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            sendImage(photo);
            Log.d(TAG , "CAMERA_REQUEST - "+data.getExtras().get("data"));

        }

    }


    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try {
            Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx); // Exception raised HERE
                cursor.close(); }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void sendImage(Bitmap bitmap) {

        try {
            //InputStream inputStream = getContentResolver().openInputStream(uri);
           // Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            //if (inputStream != null) inputStream.close();
            resized_bitmap = getResizedBitmap(bitmap,480);
        }catch (Exception e){
            e.printStackTrace();
        }


        long time = 0;
        time =  System.currentTimeMillis();
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Supernathral/Images/sent";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        final File image_file = new File(dir, "sup" + time+ ".jpg");

        p_image1 = image_file;

        OutputStream os;
        try {
            os = new FileOutputStream(image_file);
            resized_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
            os.flush();
            os.close();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(image_file)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Date today = Calendar.getInstance().getTime();

        ChatData chatData = new ChatData();
        chatData.setMessage(image_file.getAbsolutePath());
        chatData.setType(chatListData.getChat_type());
        chatData.setMessage_type("3");
        chatData.setMessage_type_int("3");
        chatData.setDatetime(dateFormat.format(today));
        chatData.setIs_me(true);
        chatData.setEntry_date(dateOnlyformat.format(today));
        chatData.setSender_id(globalClass.getId());
        chatData.setReceiver_id(chatListData.getReceiver_id());
        chatData.setType(chatListData.getChat_type());
        chatData.setImage_from("local");


        edt_message.setText("");
        chatListDataArrayList.add(chatData);
        chatSingleAdapter.notifyDataSetChanged();

        recycler_chat.smoothScrollToPosition(chatListDataArrayList.size() - 1);

        //Log.d(TAG ,"array- " + chatListDataArrayList.size());

        postChat(chatData);
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width < maxSize && height < maxSize){

            return image;

        }else {

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            return Bitmap.createScaledBitmap(image, width, height, true);
        }


    }


    private void postChat(final ChatData chatData) {

        String tag_string_req = "CHAT_MSG_LIST";

        String url = AppConfig.POST_USER_CHAT;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("sender_id", chatData.getSender_id());
        params.put("receiver_id", chatData.getReceiver_id());
        params.put("message_type", chatData.getMessage_type_int());
        params.put("chat_type", chatData.getType());


        if (chatData.getMessage_type_int().matches("3")){
            try {
                params.put("image", p_image1);
            }catch (Exception e) {
                e.printStackTrace();
            }

            params.put("message", "");

        }else {

            params.put("message", chatData.getMessage());
        }


        Log.d(TAG ,"URL- " + url);
        Log.d(TAG ,"PARAM - " + params.toString());

        client.setMaxRetriesAndTimeout(5 , 15000);
        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(TAG, "onSuccess- " + response.toString());

                if (response != null) {
                    try {



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.d(TAG, "onFailure- " + res);


                android.app.AlertDialog alert =
                        new android.app.AlertDialog.Builder(ChatSingle.this).create();
                alert.setMessage("Server Error");
                alert.show();
            }

        });

    }


    @Override
    public void onItemClick(ChatData chatData) {

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        this.unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            //do other stuff here
            Log.d(TAG, "Message = "+message);

           // setChatData(message);

        }

    };



}