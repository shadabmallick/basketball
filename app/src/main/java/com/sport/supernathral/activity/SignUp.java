package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.CustomAdapter;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.REGISTER;

public class SignUp extends AppCompatActivity {
    String TAG="Signup";
    TextView tv_login, tv_selected;
    EditText edt_name, edt_email, edt_password;
    ImageView iv_eye, iv_back;
    RelativeLayout rl_register;
    RelativeLayout rel_drop, rel_popup, rel_player, rel_trainer, rel_parent;



    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ProgressDialog pd;
    String[] person={"Player","Trainer","Parent"};
    int images[] = {R.mipmap.icon_athlete,R.mipmap.iconcoach, R.mipmap.icon_parents };
    boolean password_visible = true;
    String player;
    String device_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(SignUp.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(SignUp.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initViews();


    }


    private void initViews(){

        //toolbar = findViewById(R.id.toolbar);
        tv_login = findViewById(R.id.tv_login);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        iv_eye = findViewById(R.id.iv_eye);
        rl_register =  findViewById(R.id.rl_register);
        iv_back =  findViewById(R.id.iv_back);


        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        tv_selected = findViewById(R.id.tv_selected);
        rel_drop = findViewById(R.id.rel_drop);
        rel_popup = findViewById(R.id.rel_popup);
        rel_player = findViewById(R.id.rel_player);
        rel_trainer = findViewById(R.id.rel_trainer);
        rel_parent = findViewById(R.id.rel_parent);
        rel_popup.setVisibility(View.GONE);
        tv_selected.setText(getResources().getString(R.string.player));


        buttonClick();

    }

    private void buttonClick(){

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }
        });

        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible){

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.eye);

                    password_visible = true;

                }else {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.invisible);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());

            }
        });
        rl_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!edt_name.getText().toString().isEmpty()) {
                        if (!edt_email.getText().toString().isEmpty()) {
                            if (isValidEmail(edt_name.getText().toString())) {
                                if (!edt_password.getText().toString().isEmpty()) {
                              //      CheckRegister(username, email, password);
                                } else {
                                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                                }

                            } else {
                                FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.proper_email), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                            }
                        } else {
                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.enter_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        }
                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                }
                    else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();

                }
                }


        });


        rel_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rel_popup.getVisibility() == View.VISIBLE){

                    rel_popup.setVisibility(View.GONE);

                }else {

                    rel_popup.setVisibility(View.VISIBLE);
                }

            }
        });



        rel_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_selected.setText(getResources().getString(R.string.player));

                if (rel_popup.getVisibility() == View.VISIBLE){
                    rel_popup.setVisibility(View.GONE);
                }else {
                    rel_popup.setVisibility(View.VISIBLE);
                }
            }
        });

        rel_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_selected.setText(getResources().getString(R.string.trainer));

                if (rel_popup.getVisibility() == View.VISIBLE){
                    rel_popup.setVisibility(View.GONE);
                }else {
                    rel_popup.setVisibility(View.VISIBLE);
                }
            }
        });

        rel_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_selected.setText(getResources().getString(R.string.parent));


                if (rel_popup.getVisibility() == View.VISIBLE){
                    rel_popup.setVisibility(View.GONE);
                }else {
                    rel_popup.setVisibility(View.VISIBLE);
                }
            }
        });



    }

    private void CheckRegister(final String username, final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String success = jobj.get("status").toString().replaceAll("\"", "");
                    String message = jobj.get("message").toString().replaceAll("\"", "");
                    Log.d(TAG, "message: "+message);

                    if (success.equals("1")) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String user_id =data.get("id").toString().replaceAll("\"", "");
                        String email=data.get("email").toString().replaceAll("\"", "");
                        String username=data.get("username").toString().replaceAll("\"", "");
                        String phone=data.get("phone").toString().replaceAll("\"", "");
                        String device_type=data.get("device_type").toString().replaceAll("\"", "");
                        String device_id=data.get("device_id").toString().replaceAll("\"", "");
                        String fcm_token=data.get("fcm_token").toString().replaceAll("\"", "");

                        globalClass.setId(user_id);
                        globalClass.setEmail(email);
                        globalClass.setFname(username);
                        globalClass.setDeviceid(device_id);
                        globalClass.setPhone_number(phone);
                        globalClass.setLogin_status(true);

                        shared_preference.savePrefrence();

                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(SignUp.this, LoginScreen.class);
                        startActivity(intent);
                        finish();
                        pd.dismiss();

                    } else
                    {
                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }

                    //  JsonObject obj3 = jobj1.get("profileDetails").getAsJsonObject();

                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(), "Data Connection", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Registration Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("username", username);
                params.put("password", password);
                params.put("email_id", user_email);
                params.put("device_type", "android");
                params.put("device_id", device_id);

                //  params.put("user_zip", user_pin);


                Log.d(TAG, "Register: "+params);
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
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
