package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterMomentComment;
import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.DataModel.MomentData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.sport.supernathral.NetworkConstant.AppConfig.MOMENTCOMMENTLIST;
import static com.sport.supernathral.NetworkConstant.AppConfig.STUDENTLIST;

public class MomentCommentList extends AppCompatActivity {
    RecyclerView recycler_comment;
    String TAG="";
    private ArrayList<MomentData> listMoments;

    ArrayList<HashMap<String,String>> comment_ArrayList;
    Toolbar toolbar;
    ProgressDialog pd;
    AdapterMomentComment adapterMomentComment;
    GlobalClass globalClass;
    Shared_Preference preference;
    String moment_id;
    EditText message_text;
    ImageView send_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moment_comment_list);
       // initialisation();
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(MomentCommentList.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(MomentCommentList.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        moment_id=getIntent().getStringExtra("moment_id");
        toolbar = findViewById(R.id.toolbar);
        recycler_comment = findViewById(R.id.recycler_comment);
        message_text = findViewById(R.id.message_text);
        send_button = findViewById(R.id.send_button);
        comment_ArrayList = new ArrayList<>();
        recycler_comment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        MomentComment();
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendComment(moment_id,message_text.getText().toString().trim());
                message_text.setText("");
               // alertDialog.dismiss();
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
    private void MomentComment() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                MOMENTCOMMENTLIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();
                    comment_ArrayList.clear();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){




                            JSONArray news_data=main_object.getJSONArray("data");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String user_id = item.get("id").toString().replaceAll("\"", "");
                                String moment_id = item.get("moment_id").toString().replaceAll("\"", "");
                                String comment = item.get("comment").toString().replaceAll("\"", "");
                                String comment_id = item.get("comment_id").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                String user_image = item.get("user_image").toString().replaceAll("\"", "");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);


                                hashMap.put("is_active", is_active);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("moment_id", moment_id);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("comment", comment);
                                hashMap.put("comment_id", comment_id);
                                hashMap.put("user_name", user_name);
                                hashMap.put("user_image", user_image);

                                comment_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterMomentComment
                                    = new AdapterMomentComment(MomentCommentList.this, comment_ArrayList);
                            recycler_comment.setAdapter(adapterMomentComment);
                            adapterMomentComment.notifyDataSetChanged();


                        }else {


                            FancyToast.makeText(MomentCommentList.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(MomentCommentList.this, "Connection error",
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

                params.put("moment_id",moment_id);






                Log.d(TAG, " STUDENTLIST: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void SendComment(final String moment_id,final String text) {
        // Tag used to cancel the request

        pd.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.POST_MOMENT_COMMENT;

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
                        JSONArray data = main_object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);

                            MomentData momentData = new MomentData();
                            momentData.setId(object.optString("id"));
                            momentData.setUser_id(object.optString("user_id"));
                            momentData.setContent(object.optString("content"));
                            momentData.setFile_type(object.optString("file_type"));
                            momentData.setDelete_flag(object.optString("delete_flag"));
                            momentData.setIs_active(object.optString("is_active"));
                            momentData.setEntry_date(object.optString("entry_date"));
                            momentData.setModified_date(object.optString("modified_date"));
                            momentData.setUser_name(object.optString("user_name"));
                            momentData.setUser_image(object.optString("user_image"));
                            momentData.setMoment_like_count(object.optString("moment_like_count"));
                            momentData.setMoment_comment_count(object.optString("moment_comment_count"));

                            listMoments.add(momentData);
                           MomentComment();

                        }


                    }



                    pd.dismiss();

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
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id",globalClass.getId());
                params.put("moment_id",moment_id);
                params.put("comment",text);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

}

