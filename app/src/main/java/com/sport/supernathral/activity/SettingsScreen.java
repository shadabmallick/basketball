package com.sport.supernathral.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.CHANGE_PASSWORD;
import static com.sport.supernathral.NetworkConstant.AppConfig.UPDATE_NOTIFICATION;

public class SettingsScreen extends AppCompatActivity {
    String TAG="SettingsScreen";

    TextView tv_change_password, tv_about, tv_sign_out;
    Switch switch_noti;
    ImageView iv_back;
    GlobalClass globalClass;
    Shared_Preference prefManager;
    String switch_value;
    Shared_Preference shared_preference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }


        prefManager = new Shared_Preference(this);
        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(SettingsScreen.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(SettingsScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        prefManager.loadPrefrence();

        tv_change_password = findViewById(R.id.tv_change_password);
        tv_about = findViewById(R.id.tv_about);
        tv_sign_out = findViewById(R.id.tv_sign_out);
        switch_noti = findViewById(R.id.switch_noti);
        switch_noti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch_value = "Y";
                    Log.d(TAG, "onCreate: "+switch_value);
                    globalClass.setNotify(switch_value);
                    Notify(globalClass.getNotify());
                } else {
                    switch_value = "N";
                    globalClass.setNotify(switch_value);
                    Log.d(TAG, "onCreate: "+switch_value);
                    Notify(globalClass.getNotify());
                }
            }
        });




        iv_back = findViewById(R.id.iv_back);

        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsScreen.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsScreen.this, Aboutus.class);
                startActivity(intent);
            }
        });

        tv_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogLogout();

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



    }
    private void Notify(final String notify) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";




        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                UPDATE_NOTIFICATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){



                           // finish();



                        }else {

                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(getApplicationContext(), "Connection error",
                                FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        e.printStackTrace();

                    }

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

                params.put("user_id",globalClass.getId());
                params.put("notification", notify);



                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private void dialogLogout(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        TextView tv_yes = dialogView.findViewById(R.id.tv_yes);
        TextView tv_no = dialogView.findViewById(R.id.tv_no);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, LoginScreen.class);
                prefManager.clearPrefrence();
                globalClass.setLogin_status(false);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


    }




}