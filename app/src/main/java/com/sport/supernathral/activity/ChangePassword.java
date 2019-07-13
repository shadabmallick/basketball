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

import static com.sport.supernathral.NetworkConstant.AppConfig.CHANGE_PASSWORD;
import static com.sport.supernathral.NetworkConstant.AppConfig.RESET_PASSWORD;

public class ChangePassword extends AppCompatActivity {
    String TAG="ChangePassword";
    TextView tv_forgetPass;
    Toolbar toolbar;
    LinearLayout linear_entry_email, linear_otp;
    RelativeLayout rl_submit, rl_save;
    ImageView iv_eye, iv_eye_confirm,iv_eye_oldpass;
    EditText edt_password, edt_confirm_password,edt_old_pass;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ProgressDialog pd;
    boolean password_visible = true;
    boolean password_visible2 = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(ChangePassword.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(ChangePassword.this);
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

       // linear_entry_email.setVisibility(View.VISIBLE);
       // linear_otp.setVisibility(View.GONE);

        tv_forgetPass=findViewById(R.id.tv_forgetPass);
        iv_eye=findViewById(R.id.iv_eye);
        iv_eye_confirm=findViewById(R.id.iv_eye_confirm);
        iv_eye_oldpass=findViewById(R.id.iv_eye_oldpass);
        edt_password=findViewById(R.id.edt_password);
        edt_confirm_password=findViewById(R.id.edt_confirm_password);
        edt_old_pass=findViewById(R.id.edt_old_pass);


        edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edt_old_pass.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        buttonClick();

    }


    private void buttonClick(){

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_entry_email.setVisibility(View.GONE);
                linear_otp.setVisibility(View.VISIBLE);

            }
        });

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String old_pass=edt_old_pass.getText().toString().trim();
                String  new_pass=edt_password.getText().toString().trim();

                if(!edt_old_pass.getText().toString().isEmpty()){
                    if(!edt_password.getText().toString().isEmpty()){

                        if((edt_password.getText().toString().equals(edt_confirm_password.getText().toString()))) {

                            ChangePassword(old_pass,new_pass);
                        }
                        else {
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_not_match), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                        }


                    }
                    else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                    }
                }
                else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

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
        iv_eye_oldpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible){

                    edt_old_pass.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye_oldpass.setImageResource(R.mipmap.invisible);

                    password_visible = true;

                }else {

                    edt_old_pass.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye_oldpass.setImageResource(R.mipmap.eye);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());

            }
        });


    }
    private void ChangePassword(final String old_pass, final String new_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        final String device_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);


        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                CHANGE_PASSWORD, new Response.Listener<String>() {

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


                            finish();



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
                params.put("old_password", old_pass);
                params.put("new_password", new_password);


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
