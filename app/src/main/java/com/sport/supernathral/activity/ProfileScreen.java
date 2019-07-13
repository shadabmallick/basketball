package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import static com.sport.supernathral.NetworkConstant.AppConfig.USER_PROFILE;

public class ProfileScreen extends AppCompatActivity {
    String TAG="ProfileScreen";
    TextView tv_general, tv_settings;
    ImageView profile_image;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView txt_name,txt_type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(ProfileScreen.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(ProfileScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        tv_general = findViewById(R.id.tv_general);
        txt_name = findViewById(R.id.txt_name);
        txt_type = findViewById(R.id.txt_type);
        tv_settings = findViewById(R.id.tv_settings);
        profile_image = findViewById(R.id.profile_image);

        Profile();

/*
        tv_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileScreen.this, GeneralScreen.class);
                intent.putExtra("",);
                startActivity(intent);
            }
        });
*/

        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileScreen.this, SettingsScreen.class);
                startActivity(intent);

            }
        });




        initFooterItems();


    }







    private void initFooterItems(){

        LinearLayout llnews = findViewById(R.id.llnews);
        LinearLayout llchat = findViewById(R.id.llchat);
        LinearLayout ll_games = findViewById(R.id.ll_games);
        LinearLayout ll_event = findViewById(R.id.ll_event);
        LinearLayout ll_profile = findViewById(R.id.ll_profile);


        llnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });

        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChatScreen.class));
            }
        });


        ll_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), GamesMain.class));
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), EventScreen.class));

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
    private void Profile() {
        // Tag used to cancel the request
        String tag_string_req = "forget_password";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                USER_PROFILE, new Response.Listener<String>() {

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

/*
                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false)
                                    .show();
*/

                            JSONObject data = main_object.getJSONObject("data");


                            final String id = data.get("id").toString().replaceAll("\"", "");
                            final String unique_id = data.get("unique_id").toString().replaceAll("\"", "");
                            final String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                            final String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                            final String image = data.get("image").toString().replaceAll("\"", "");
                            final String type = data.get("type").toString().replaceAll("\"", "");
                            final String name = data.get("name").toString().replaceAll("\"", "");
                            final String emailid = data.get("emailid").toString().replaceAll("\"", "");
                            final String password = data.get("password").toString().replaceAll("\"", "");
                            final String contact_no = data.get("contact_no").toString().replaceAll("\"", "");
                            final String skill = data.get("skill").toString().replaceAll("\"", "");
                            final String fax = data.get("fax").toString().replaceAll("\"", "");
                            final String dob = data.get("dob").toString().replaceAll("\"", "");
                            final String description = data.get("description").toString().replaceAll("\"", "");
                            final String notes = data.get("notes").toString().replaceAll("\"", "");
                            final String primary_contact_name = data.get("primary_contact_name").toString().replaceAll("\"", "");
                            final String primary_contact_email = data.get("primary_contact_email").toString().replaceAll("\"", "");
                            final String primary_contact_phone = data.get("primary_contact_phone").toString().replaceAll("\"", "");
                            final String secondary_contact_name = data.get("secondary_contact_name").toString().replaceAll("\"", "");
                            final String secondary_contact_email = data.get("secondary_contact_email").toString().replaceAll("\"", "");
                            final String secondary_contact_phone = data.get("secondary_contact_phone").toString().replaceAll("\"", "");
                            final String added_by = data.get("added_by").toString().replaceAll("\"", "");
                            final String child_ids = data.get("child_ids").toString().replaceAll("\"", "");
                            final String sponsor_ids = data.get("sponsor_ids").toString().replaceAll("\"", "");
                            final String device_type = data.get("device_type").toString().replaceAll("\"", "");
                            final String device_id = data.get("device_id").toString().replaceAll("\"", "");
                            final String activation_token = data.get("activation_token").toString().replaceAll("\"", "");
                            final String first_login = data.get("first_login").toString().replaceAll("\"", "");
                            final String otp_code = data.get("otp_code").toString().replaceAll("\"", "");
                            final String latitude = data.get("latitude").toString().replaceAll("\"", "");
                            final String longitude = data.get("longitude").toString().replaceAll("\"", "");
                            final String location = data.get("location").toString().replaceAll("\"", "");
                            final String admin_approved = data.get("admin_approved").toString().replaceAll("\"", "");
                            final String qa = data.get("qa").toString().replaceAll("\"", "");
                            final String notification = data.get("notification").toString().replaceAll("\"", "");
                            final String delete_flag = data.get("delete_flag").toString().replaceAll("\"", "");
                            final String is_active = data.get("is_active").toString().replaceAll("\"", "");
                            final String report_block = data.get("report_block").toString().replaceAll("\"", "");
                            final String entry_date = data.get("entry_date").toString().replaceAll("\"", "");
                            final String modified_date = data.get("modified_date").toString().replaceAll("\"", "");
                            txt_name.setText(name);
                           String last= image.substring(image.lastIndexOf("/") + 1);

                            Log.d(TAG, "onResponse: "+last);
                            Picasso.with(getApplicationContext()).load(image).placeholder(R.mipmap.profile_placeholder).into(profile_image);

                            if(type.equals("Coach/Teachers")){
                                txt_type.setText("Trainer");

                            }
                            tv_general.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(ProfileScreen.this, GeneralScreen.class);
                                    intent.putExtra("id", id);
                                    intent.putExtra("unique_id", unique_id);
                                    intent.putExtra("main_access_group_id", main_access_group_id);
                                    intent.putExtra("sub_access_group_id", sub_access_group_id);
                                    intent.putExtra("image", image);
                                    intent.putExtra("type", type);
                                    intent.putExtra("name", name);
                                    intent.putExtra("emailid", emailid);
                                    intent.putExtra("password", password);
                                    intent.putExtra("contact_no", contact_no);
                                    intent.putExtra("skill", skill);
                                    intent.putExtra("fax", fax);
                                    intent.putExtra("dob", dob);
                                    intent.putExtra("description", description);
                                    intent.putExtra("notes", notes);
                                    intent.putExtra("primary_contact_name", primary_contact_name);
                                    intent.putExtra("primary_contact_email", primary_contact_email);
                                    intent.putExtra("primary_contact_phone", primary_contact_phone);
                                    intent.putExtra("secondary_contact_name", secondary_contact_name);
                                    intent.putExtra("secondary_contact_email", secondary_contact_email);
                                    intent.putExtra("secondary_contact_phone", secondary_contact_phone);
                                    intent.putExtra("added_by", added_by);
                                    intent.putExtra("child_ids", child_ids);
                                    intent.putExtra("sponsor_ids", sponsor_ids);
                                    intent.putExtra("device_type", device_type);
                                    intent.putExtra("device_id", device_id);
                                    intent.putExtra("activation_token", activation_token);
                                    intent.putExtra("first_login", first_login);
                                    intent.putExtra("otp_code", otp_code);
                                    intent.putExtra("latitude", latitude);
                                    intent.putExtra("longitude", longitude);
                                    intent.putExtra("location", location);
                                    intent.putExtra("admin_approved", admin_approved);
                                    intent.putExtra("qa", qa);
                                    intent.putExtra("notification", notification);
                                    intent.putExtra("delete_flag", delete_flag);
                                    intent.putExtra("is_active", is_active);
                                    intent.putExtra("report_block", report_block);
                                    intent.putExtra("entry_date", entry_date);
                                    intent.putExtra("modified_date", modified_date);
                                    startActivity(intent);
                                }
                            });




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


                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}