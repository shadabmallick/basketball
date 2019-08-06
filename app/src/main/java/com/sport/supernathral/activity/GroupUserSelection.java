package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.UserSelectionAdapter;
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupUserSelection extends AppCompatActivity {

    String TAG = "create group";

    Toolbar toolbar;
    ImageView iv_goto_group;
    RecyclerView recycler_user;

    UserSelectionAdapter userSelectionAdapter;
    ProgressDialog progressDialog;
    GlobalClass globalClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_user_selection_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        toolbar = findViewById(R.id.toolbar);
        iv_goto_group = findViewById(R.id.iv_goto_group);
        recycler_user = findViewById(R.id.recycler_user);

        recycler_user.setLayoutManager(new LinearLayoutManager(this));


        globalClass = (GlobalClass) getApplicationContext();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);



        iv_goto_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userSelectionAdapter != null &&
                        userSelectionAdapter.getSelectedUsers().size() > 0){

                    Intent intent =
                            new Intent(getApplicationContext(), GroupCreate.class);
                    intent.putExtra("data", userSelectionAdapter.getSelectedUsers());
                    startActivity(intent);

                }else {

                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.select_users),
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                }
            }
        });






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

    @Override
    protected void onResume() {
        getUsers();
        super.onResume();
    }

    private void getUsers() {

        progressDialog.show();

        String tag_string_req = "group_chat_list";

        String url = AppConfig.group_chat_list;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "group_chat_list Response: " + response);

                try {

                    ArrayList<MembersData> listNewMembers = new ArrayList<>();

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        JSONArray data = main_object.getJSONArray("data");
                        for (int j = 0; j < data.length(); j++){
                            JSONObject object = data.getJSONObject(j);

                            //if (object.optString("group").equals("No")){

                                MembersData membersData = new MembersData();
                                membersData.setId(object.optString("receiver_id"));
                                membersData.setUser_name(object.optString("user_name"));
                                membersData.setUser_image(object.optString("user_image"));
                                membersData.setUser_type(object.optString("user_type"));

                                listNewMembers.add(membersData);

                           // }

                        }

                    }

                    setNewUsers(listNewMembers);

                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection Error", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }

                progressDialog.dismiss();


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
                params.put("group_id", "");
                params.put("user_id", globalClass.getId());

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    private void setNewUsers(ArrayList<MembersData> listNewMembers){

        userSelectionAdapter = new UserSelectionAdapter(GroupUserSelection.this,
                listNewMembers);
        recycler_user.setAdapter(userSelectionAdapter);


    }



}