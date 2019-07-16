package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import static com.sport.supernathral.NetworkConstant.AppConfig.GET_PLAYER_INFO;

public class InfoActivity extends AppCompatActivity {
    ImageView profile_image;
    String TAG="Info";
    Toolbar toolbar;
    String player_id;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_name,tv_designation,tv_full_name,tv_email,tv_birth
            ,tv_description,tv_notes,tv_sponsor,tv_statistics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(InfoActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(InfoActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        profile_image = findViewById(R.id.profile_image);
        tv_name = findViewById(R.id.tv_name);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_email = findViewById(R.id.tv_email);
        tv_designation = findViewById(R.id.tv_designation);
        tv_birth = findViewById(R.id.tv_birth);
        tv_description = findViewById(R.id.tv_description);
        tv_notes = findViewById(R.id.tv_notes);
        tv_sponsor = findViewById(R.id.tv_sponsor);
        tv_statistics = findViewById(R.id.tv_statistics);
        player_id =  getIntent().getStringExtra("player_id");
        getPlayerInfo();
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
    private void getPlayerInfo() {
        String tag_string_req = "forget_password";

        // pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GET_PLAYER_INFO, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    // pd.dismiss();
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
                            final String statistics = data.get("statistics").toString().replaceAll("\"", "");

                            Picasso.with(getApplicationContext()).load(image).placeholder(R.mipmap.profile_placeholder).into(profile_image);
                            tv_name.setText(name);
                            if((type.equals("Students/Players"))){
                                tv_designation.setText("Player");
                            }
                            else if((type.equals("Coach/Teachers"))){
                                tv_designation.setText("Trainer");
                            }

                            tv_birth.setText(dob);
                            tv_email.setText(emailid);
                            tv_full_name.setText(contact_no);
                            tv_description.setText(description);
                            tv_notes.setText(notes);
                           // tv_sponsor.setText();
                            tv_statistics.setText(statistics);






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

                params.put("player_id",player_id);


                Log.d(TAG, " param2: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));



    }

}
