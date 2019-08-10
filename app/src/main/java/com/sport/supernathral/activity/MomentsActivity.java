package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.AdapterClass.AdapterMoments;
import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.MomentData;
import com.sport.supernathral.DataModel.SubCommentData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MomentsActivity extends AppCompatActivity {
    String TAG = "MomentsActivity";
    Toolbar toolbar;
    ArrayList<MomentData> listMoments;
    RecyclerView recycler_moment;
    AdapterMoments adapterMoments;
    ImageView img_add;
    ProgressDialog progressDialog;
    private View currentFocusedLayout, oldFocusedLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moments_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        initViews();

    }

    private void initViews(){

        toolbar = findViewById(R.id.toolbar);
        img_add = findViewById(R.id.img_add);
        recycler_moment = findViewById(R.id.recycler_moment);
        recycler_moment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initFooterItems();

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MomentMessage.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        getMOMENTList();

    }




    public void getMOMENTList() {
        // Tag used to cancel the request

        progressDialog.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.MOMENT;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){
                        JSONArray data = main_object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++){
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


                            /// Files ...
                            ArrayList<String> listFile = new ArrayList<>();
                            JSONArray moment_files = object.getJSONArray("moment_files");
                            for (int j = 0; j < moment_files.length(); j++){
                                JSONObject object1 = moment_files.getJSONObject(j);

                                listFile.add(object1.optString("file_name"));
                            }
                            momentData.setMoment_files(listFile);


                            /// Comments ...
                            ArrayList<CommentData> listComments = new ArrayList<>();
                            JSONArray moment_comment = object.getJSONArray("moment_comment");
                            for (int j = 0; j < moment_comment.length(); j++){
                                JSONObject object1 = moment_comment.getJSONObject(j);

                                CommentData commentData = new CommentData();
                                commentData.setId(object1.optString("id"));
                                commentData.setMoment_id(object1.optString("moment_id"));
                                commentData.setUser_id(object1.optString("user_id"));
                                commentData.setComment(object1.optString("comment"));
                                commentData.setComment_id(object1.optString("comment_id"));
                                commentData.setDelete_flag(object1.optString("delete_flag"));
                                commentData.setIs_active(object1.optString("is_active"));
                                commentData.setEntry_date(object1.optString("entry_date"));
                                commentData.setModified_date(object1.optString("modified_date"));
                                commentData.setUser_name(object1.optString("user_name"));
                                commentData.setUser_image(object1.optString("user_image"));
                                commentData.setMoment_comment_like_count(object1.optString("moment_comment_like_count"));
                                commentData.setMoment_comment_sub_count(object1.optString("moment_comment_sub_count"));


                                ArrayList<SubCommentData> listSubComments = new ArrayList<>();
                                JSONArray moment_comment_sub = object1.getJSONArray("moment_comment_sub");
                                for (int k = 0; k < moment_comment_sub.length(); k++){
                                    JSONObject object2 = moment_comment_sub.getJSONObject(k);

                                    SubCommentData subCommentData = new SubCommentData();
                                    subCommentData.setId(object2.optString("id"));
                                    subCommentData.setMoment_id(object2.optString("moment_id"));
                                    subCommentData.setUser_id(object2.optString("user_id"));
                                    subCommentData.setComment(object2.optString("comment"));
                                    subCommentData.setComment_id(object2.optString("comment_id"));
                                    subCommentData.setDelete_flag(object2.optString("delete_flag"));
                                    subCommentData.setIs_active(object2.optString("is_active"));
                                    subCommentData.setEntry_date(object2.optString("entry_date"));
                                    subCommentData.setModified_date(object2.optString("modified_date"));
                                    subCommentData.setUser_name(object2.optString("user_name"));
                                    subCommentData.setUser_image(object2.optString("user_image"));
                                    subCommentData.setMoment_comment_like_count(object2.optString("moment_comment_like_count"));
                                    subCommentData.setMoment_comment_sub_count(object2.optString("moment_comment_sub_count"));

                                    listSubComments.add(subCommentData);

                                }

                                commentData.setList_sub_comment(listSubComments);

                                listComments.add(commentData);

                            }

                            momentData.setList_comment(listComments);

                            listMoments.add(momentData);
                        }


                    }

                    setMomentData();

                    progressDialog.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(MomentsActivity.this,
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

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    private void setMomentData() {

        adapterMoments = new AdapterMoments(MomentsActivity.this, listMoments);
        recycler_moment.setAdapter(adapterMoments);


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

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileScreen.class));
            }
        });

    }

}
