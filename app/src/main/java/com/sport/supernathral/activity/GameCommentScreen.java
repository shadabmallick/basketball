package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterComment;
import com.sport.supernathral.AdapterClass.AdapterMainComment;
import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.SubCommentData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.GAMEComment;

import static com.sport.supernathral.NetworkConstant.AppConfig.game_comment_delete;
import static com.sport.supernathral.NetworkConstant.AppConfig.news_comment_delete;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_game_comment;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_game_comment_like;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_game_comment_on_comment;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_news_comment;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_news_comment_on_comment;
import static com.sport.supernathral.NetworkConstant.AppConfig.post_news_like_on_comment;

public class GameCommentScreen extends AppCompatActivity implements
        AdapterComment.onItemClickListnerLike,
        AdapterComment.onItemClickListnerComment,
        AdapterComment.onItemClickListnerDelete,
        AdapterComment.onItemClickListnerReport{


    RecyclerView rv_category;
    EditText message_text;
    ImageView send_button;
    Toolbar toolbar;

    String TAG="product";
    AdapterComment adapterComment;
    ArrayList<String> newsList;
    Shared_Preference preference;
    GlobalClass globalClass;

    ArrayList<CommentData> listComment;
    ProgressDialog pd;

    String comment_type = "", comment_id,from;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initialisation();
    }


    private void initialisation() {

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getResources().getString(R.string.loading));

        rv_category = findViewById(R.id.recycler_comment);
        message_text = findViewById(R.id.message_text);
        send_button = findViewById(R.id.send_button);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        preference = new Shared_Preference(this);
        globalClass = (GlobalClass) getApplicationContext();
        from=getIntent().getStringExtra("from");

        function();



        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = getResources().getString(R.string.typemessage);

                if (message_text.getText().toString().trim().isEmpty()){
                    FancyToast.makeText(getApplicationContext(), message,
                            FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                            .show();
                }else {

                    if (comment_type.equals("comment")){
                        postComment(message_text.getText().toString());
                    }else if (comment_type.equals("sub_comment")){
                        postSubComment(message_text.getText().toString());
                    }else {
                        postComment(message_text.getText().toString());
                    }


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

    private void function() {
        rv_category.setLayoutManager(new LinearLayoutManager(this));
        CommentList();

    }

    private void CommentList() {
        String tag_string_req = "comment";

        message_text.setText("");
        listComment = new ArrayList<>();

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GAMEComment, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "comment: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            JSONArray news_data = main_object.getJSONArray("data");

                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);

                                String id = item.get("id").toString().replaceAll("\"", "");
                                String match_id = item.get("match_id").toString().replaceAll("\"", "");
                                String user_id = item.get("user_id").toString().replaceAll("\"", "");
                                String comment = item.get("comment").toString().replaceAll("\"", "");
                                String comment_id = item.get("comment_id").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                String user_image = item.get("user_image").toString().replaceAll("\"", "");
                                String news_comment_like = item.get("match_comment_like").toString().replaceAll("\"", "");
                                String news_comment_sub_count = item.get("match_comment_sub_count").toString().replaceAll("\"", "");


                                CommentData commentData = new CommentData();
                                commentData.setId(id);
                                commentData.setNews_id(match_id);
                                commentData.setUser_id(user_id);
                                commentData.setComment(comment);
                                commentData.setComment_id(comment_id);
                                commentData.setDelete_flag(delete_flag);
                                commentData.setIs_active(is_active);
                                commentData.setEntry_date(entry_date);
                                commentData.setModified_date(modified_date);
                                commentData.setUser_name(user_name);
                                commentData.setUser_image(user_image);
                                commentData.setMoment_comment_like_count(news_comment_like);
                                commentData.setMoment_comment_sub_count(news_comment_sub_count);


                                ArrayList<SubCommentData> listSubComment = new ArrayList<>();
                                JSONArray news_comment_sub=item.getJSONArray("match_comment_sub");
                                for (int j = 0; j < news_comment_sub.length(); j++) {
                                    JSONObject item_comment = news_comment_sub.getJSONObject(j);

                                    String sub_id = item_comment.get("id").toString().replaceAll("\"", "");
                                    String sub_match_id = item_comment.get("match_id").toString().replaceAll("\"", "");
                                    String sub_user_id = item_comment.get("user_id").toString().replaceAll("\"", "");
                                    String sub_comment = item_comment.get("comment").toString().replaceAll("\"", "");
                                    String sub_comment_id = item_comment.get("comment_id").toString().replaceAll("\"", "");
                                    String sub_delete_flag = item_comment.get("delete_flag").toString().replaceAll("\"", "");
                                    String sub_is_active = item_comment.get("is_active").toString().replaceAll("\"", "");
                                    String sub_entry_date = item_comment.get("entry_date").toString().replaceAll("\"", "");
                                    String sub_modified_date = item_comment.get("modified_date").toString().replaceAll("\"", "");
                                    String sub_user_name = item_comment.get("user_name").toString().replaceAll("\"", "");
                                    String sub_user_image = item_comment.get("user_image").toString().replaceAll("\"", "");
                                    String sub_news_comment_like = item_comment.get("match_sub_comment_like").toString().replaceAll("\"", "");
                                    // String sub_news_comment_sub_count = item_comment.get("news_comment_sub_count").toString().replaceAll("\"", "");

                                    SubCommentData subCommentData = new SubCommentData();
                                    subCommentData.setId(sub_id);
                                    subCommentData.setNews_id(sub_match_id);
                                    subCommentData.setUser_id(sub_user_id);
                                    subCommentData.setComment(sub_comment);
                                    subCommentData.setComment_id(sub_comment_id);
                                    subCommentData.setDelete_flag(sub_delete_flag);
                                    subCommentData.setIs_active(sub_is_active);
                                    subCommentData.setEntry_date(sub_entry_date);
                                    subCommentData.setModified_date(sub_modified_date);
                                    subCommentData.setUser_name(sub_user_name);
                                    subCommentData.setUser_image(sub_user_image);
                                    subCommentData.setMoment_comment_like_count(sub_news_comment_like);
                                    // subCommentData.setMoment_comment_sub_count(sub_news_comment_sub_count);

                                    listSubComment.add(subCommentData);

                                }

                                commentData.setList_sub_comment(listSubComment);

                                listComment.add(commentData);


                            }

                            setAdapterComment();

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

                params.put("game_id", globalClass.getGame_id());
                //params.put("news_id", "7");

                Log.d(TAG, "get comment: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void setAdapterComment(){

        adapterComment   = new AdapterComment(GameCommentScreen.this, listComment,from);
        rv_category.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        adapterComment.setmListnerLike(this);
        adapterComment.setmListnerComment(this);
        adapterComment.setmListnerDelete(this);
        adapterComment.setmListnerReport(this);

    }


    @Override
    public void onItemClickLike(String id) {

        likeOnComment(id);
    }

    @Override
    public void onItemClickComment(String id, String type) {

        comment_id = id;
        comment_type = type;

        if (comment_type.equals("sub_comment")){
            message_text.requestFocus();
            Common.showSoftKeyboard(message_text, GameCommentScreen.this);
            message_text.setHint(getResources().getString(R.string.typemessage_oncomment));
        }

    }

    @Override
    public void onItemClickDelete(String id) {
        deleteComment(id);
    }

    @Override
    public void onItemClickReport(String id) {
        reportDialog(id);
    }



    private void postComment(final String comment) {
        String tag_string_req = "comment";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                post_game_comment, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "comment: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            CommentList();

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

                params.put("game_id", globalClass.getGame_id());
                params.put("user_id", globalClass.getId());
                params.put("comment", comment);

                Log.d(TAG, "get comment: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void postSubComment(final String comment) {
        String tag_string_req = "comment";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                post_game_comment_on_comment, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "comment: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            CommentList();

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

                //params.put("news_id", globalClass.getSingle_top_news_id());
                params.put("game_id", globalClass.getGame_id());
                params.put("user_id", globalClass.getId());
                params.put("comment_id", comment_id);
                params.put("comment", comment);

                Log.d(TAG, "get comment: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void likeOnComment(final String com_id) {
        String tag_string_req = "like";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                post_game_comment_like, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "like: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {
                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            CommentList();

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

                //params.put("news_id", globalClass.getSingle_top_news_id());
                params.put("game_id", globalClass.getGame_id());
                params.put("user_id", globalClass.getId());
                params.put("comment_id", com_id);

                Log.d(TAG, "param like: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void deleteComment(final String com_id) {
        String tag_string_req = "delete";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                game_comment_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "delete: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {
                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            CommentList();

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

                //params.put("news_id", globalClass.getSingle_top_news_id());
                params.put("game_id", globalClass.getGame_id());
                params.put("user_id", globalClass.getId());
                params.put("news_comment_id", com_id);

                Log.d(TAG, "param like: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private void reportDialog(String comm_id){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GameCommentScreen.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_news_report, null);
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