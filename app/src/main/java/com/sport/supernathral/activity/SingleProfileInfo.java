package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.sport.supernathral.AdapterClass.ProfileAdapter;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.Fragment.Profiles;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleProfileInfo extends AppCompatActivity {

    String TAG = "profile";

    RelativeLayout rel_back, rl_message;
    ImageView iv_profile;
    TextView tv_name, tv_designation, tv_full_name, tv_location, tv_email, tv_designation2;
    CircleImageView iv_user;

    ChatListData chatListData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        initViews();


    }


    private void initViews(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        rel_back = findViewById(R.id.rel_back);
        rl_message = findViewById(R.id.rl_message);
        iv_profile = findViewById(R.id.iv_profile);
        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_location = findViewById(R.id.tv_location);
        tv_email = findViewById(R.id.tv_email);
        tv_designation2 = findViewById(R.id.tv_designation2);
        iv_user = findViewById(R.id.iv_user);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            chatListData = (ChatListData) bundle.getSerializable("info");

            if (chatListData != null){

                getProfileInfo(chatListData.getReceiver_id());

            }

        }


        rel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SingleProfileInfo.this, ChatSingle.class);
                intent.putExtra("info", chatListData);
                startActivity(intent);

            }
        });

    }



    private void getProfileInfo(final String user_id) {
        // Tag used to cancel the request

        progressDialog.show();

        String tag_string_req = "user_profile";

        String url = AppConfig.user_profile;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "user_profile Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        JSONObject data = main_object.getJSONObject("data");


                        if (!data.optString("image").isEmpty()){
                            Picasso.with(SingleProfileInfo.this)
                                    .load(data.optString("image"))
                                    .placeholder(R.mipmap.profile_placeholder)
                                    .into(iv_profile);

                            Picasso.with(SingleProfileInfo.this)
                                    .load(data.optString("image"))
                                    .placeholder(R.mipmap.profile_placeholder)
                                    .into(iv_user);
                        }


                        tv_name.setText(data.optString("name"));
                        tv_full_name.setText(data.optString("name"));
                        tv_location.setText(data.optString("location"));
                        tv_email.setText(data.optString("emailid"));

                        if (Common.player.equals(data.optString("type"))){
                            tv_designation.setText("Player");
                            tv_designation2.setText("Player");
                        }else if (Common.parent.equals(data.optString("type"))){
                            tv_designation.setText("Parent");
                            tv_designation2.setText("Parent");
                        }else if (Common.trainer.equals(data.optString("type"))){
                            tv_designation.setText("Trainer");
                            tv_designation2.setText("Trainer");
                        }

                    }


                    progressDialog.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }




}