package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import static com.sport.supernathral.NetworkConstant.AppConfig.FORGOT_PASSWORD;
import static com.sport.supernathral.NetworkConstant.AppConfig.LOGIN;
import static com.sport.supernathral.NetworkConstant.AppConfig.RESET_PASSWORD;

public class OtpActivity extends AppCompatActivity {
    String TAG="TOP";
    TextView tv_forgetPass;
    Toolbar toolbar;
    LinearLayout linear_entry_email, linear_otp;
    RelativeLayout rl_submit, rl_save;
    ImageView iv_eye, iv_eye_confirm;
    EditText edt_password, edt_confirm_password,edt_email,edt_otp;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ProgressDialog pd;
    boolean password_visible = true;
    boolean password_visible2 = true;
    String email_new;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);
        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(OtpActivity.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(OtpActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initViews();

    }

    public void initViews(){

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        linear_entry_email = findViewById(R.id.linear_entry_email);
        linear_otp = findViewById(R.id.linear_otp);
        rl_submit = findViewById(R.id.rl_submit);
        rl_save = findViewById(R.id.rl_save);

        linear_entry_email.setVisibility(View.VISIBLE);
        linear_otp.setVisibility(View.GONE);

        tv_forgetPass=findViewById(R.id.tv_forgetPass);
        iv_eye=findViewById(R.id.iv_eye);
        iv_eye_confirm=findViewById(R.id.iv_eye_confirm);
        edt_password=findViewById(R.id.edt_password);
        edt_confirm_password=findViewById(R.id.edt_confirm_password);
        edt_email=findViewById(R.id.edt_email);
        edt_otp=findViewById(R.id.edt_otp);


        edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        buttonClick();

    }


    private void buttonClick(){

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_entry_email.setVisibility(View.GONE);
                linear_otp.setVisibility(View.VISIBLE);
                email_new = edt_email.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!edt_email.getText().toString().isEmpty()) {

                        OTP(email_new);


                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }

            }
        });

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String otp = edt_otp.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (globalClass.isNetworkAvailable()) {
                    if (!edt_otp.getText().toString().isEmpty()) {

                        if(!edt_password.getText().toString().isEmpty()){
                            if(!edt_confirm_password.getText().toString().isEmpty()){

                                if((edt_password.getText().toString().equals(edt_confirm_password.getText().toString()))){
                                    ResetPassword(otp,password);

                                }
                                else {
                                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_not_match), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                                }

                            }
                            else {
                                FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.confirm_pass), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                            }


                        }
                        else{
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                        }




                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_otp), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }

            }
        });


        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible){

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.invisible);

                    password_visible = true;

                }else {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.eye);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());

            }
        });

        iv_eye_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible2){

                    edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye_confirm.setImageResource(R.mipmap.invisible);

                    password_visible2 = true;

                }else {

                    edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye_confirm.setImageResource(R.mipmap.eye);

                    password_visible2 = false;

                }

                edt_confirm_password.setSelection(edt_confirm_password.length());
            }
        });


    }
    private void ResetPassword(final String otp, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        final String device_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);


        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                RESET_PASSWORD, new Response.Listener<String>() {

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

                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false)
                                    .show();


                            Intent intent = new Intent(OtpActivity.this, LoginScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);



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

                params.put("email",email_new);
                params.put("new_password", password);
                params.put("otp_code", otp);


                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }




    private void OTP(final String user_email) {
        // Tag used to cancel the request
        String tag_string_req = "forget_password";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                FORGOT_PASSWORD, new Response.Listener<String>() {

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

                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false)
                                    .show();

                            JSONObject data = main_object.getJSONObject("data");


                            String otp_code = data.get("otp_code").toString().replaceAll("\"", "");




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

                params.put("email", user_email);


                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}