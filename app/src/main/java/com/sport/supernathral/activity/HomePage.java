package com.sport.supernathral.activity;
        /* Develop by shadab on 12/07/2019 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.NewsAdapter;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.sport.supernathral.NetworkConstant.AppConfig.GET_PLAYER_INFO;
import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS;
import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS_SEARCH;
import static com.sport.supernathral.NetworkConstant.AppConfig.USER_PROFILE;

public class HomePage extends AppCompatActivity {
    String TAG="Homepage";
    RecyclerView recycle_game,recycle_news;
    ImageView img_search,img_top,img_comment,img_like,img_unlike;
    RelativeLayout rl_homescreen;
    LinearLayout linear_like, linear_comment;
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> newsdata;
    ArrayList<HashMap<String,String>> newsdata_search;
    ArrayList<HashMap<String,String>> gamedata_search;
    ProgressDialog pd;
    String playerid,location,main_access_group_id,sub_access_group_id;
    NewsAdapter newsAdapter;
    TextView heading_text,comment,like;
    EditText edt_search;
    String single_top_news_id;
    GameAdapter gameAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(HomePage.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(HomePage.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        recycle_game = findViewById(R.id.recycle_game);
        recycle_news = findViewById(R.id.recycle_news);
        img_search = findViewById(R.id.img_search);
        rl_homescreen = findViewById(R.id.rl_homescreen);
        img_top = findViewById(R.id.img_top);
        heading_text = findViewById(R.id.tv_home_text);
        like = findViewById(R.id.tv_like);
        comment = findViewById(R.id.tv_comment);
        edt_search = findViewById(R.id.edt_search);
        img_comment = findViewById(R.id.img_comment);
        img_like = findViewById(R.id.img_like);
        img_unlike = findViewById(R.id.img_unlike);
        linear_like = findViewById(R.id.linear_like);
        linear_comment = findViewById(R.id.linear_comment);

        newsdata=new ArrayList<>();
        newsdata_search=new ArrayList<>();
        gamedata_search=new ArrayList<>();
        Profile();

        recycle_game.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle_news.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle_game.setNestedScrollingEnabled(false);
        recycle_news.setNestedScrollingEnabled(false);

        linear_comment.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent newsPage = new Intent(HomePage.this,NewsSublist.class);
                 newsPage.putExtra("id", single_top_news_id);
                 newsPage.putExtra("from", "home comment");
                 startActivity(newsPage);
             }
        });

       img_like.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                    NewsLike();
                    img_like.setVisibility(View.VISIBLE);
                    img_unlike.setVisibility(View.GONE);
           }
       });

       img_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsLike();
                img_like.setVisibility(View.GONE);
                img_unlike.setVisibility(View.VISIBLE);
            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(globalClass.isNetworkAvailable()){
                    if (editable.length() != 0) {
                        NewsSearch(edt_search.getText().toString());
                    }
                    else {
                        GetNews();
                    }




                }else{
                    FancyToast.makeText(HomePage.this, "Check Internet Connecton", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }

            }
        });


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchPLayer = new Intent(HomePage.this,SearchPlayer.class);
                startActivity(searchPLayer);

            }
        });
        img_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newsPage = new Intent(HomePage.this,NewsSublist.class);
                newsPage.putExtra("id", single_top_news_id);
                startActivity(newsPage);
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
                startActivity(new Intent(HomePage.this,HomePage.class));
            }
        });


        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePage.this,ChatScreen.class));
            }
        });

        ll_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent event=new Intent(HomePage.this,GamesMain.class);
                event.putExtra("main_access_group_id",main_access_group_id);
                event.putExtra("sub_access_group_id",sub_access_group_id);
                Log.d(TAG, "onClick: "+main_access_group_id+ " "+sub_access_group_id);
                startActivity(event);
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent event=new Intent(HomePage.this,EventScreen.class);
               event.putExtra("main_access_group_id",main_access_group_id);
               event.putExtra("sub_access_group_id",sub_access_group_id);
               startActivity(event);
            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomePage.this, ProfileScreen.class));
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


                           playerid = data.get("id").toString().replaceAll("\"", "");
                            final String unique_id = data.get("unique_id").toString().replaceAll("\"", "");
                             main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                             sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
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
                            location = data.get("location").toString().replaceAll("\"", "");
                            final String admin_approved = data.get("admin_approved").toString().replaceAll("\"", "");
                            final String qa = data.get("qa").toString().replaceAll("\"", "");
                            final String notification = data.get("notification").toString().replaceAll("\"", "");
                            final String delete_flag = data.get("delete_flag").toString().replaceAll("\"", "");
                            final String is_active = data.get("is_active").toString().replaceAll("\"", "");
                            final String report_block = data.get("report_block").toString().replaceAll("\"", "");
                            final String entry_date = data.get("entry_date").toString().replaceAll("\"", "");
                            final String modified_date = data.get("modified_date").toString().replaceAll("\"", "");
                              globalClass.setMain_access_group_id(main_access_group_id);
                              globalClass.setSub_access_group_id(sub_access_group_id);
                             if(is_active.equals("Y")){
                                  getPlayerInfo();
                             }
                             else{

                             }




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


                Log.d(TAG, " param1: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    /*PLayer Information*/
    private void NewsLike() {
        // Tag used to cancel the request

        pd.show();

        String tag_string_req = "MOMENT";

        String url = AppConfig.POST_NEWS_LIKE;

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

                          GetNews();


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
                params.put("news_id",single_top_news_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

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

                            GetNews();



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

                params.put("player_id",playerid);


                Log.d(TAG, " param2: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));



    }

    private void GetNews() {


        String tag_string_req = "forget_password";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                NEWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();
                    newsdata_search.clear();
                    newsdata.clear();
                    recycle_game.removeAllViews();
                    recycle_news.removeAllViews();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            rl_homescreen.setVisibility(View.VISIBLE);

                            JSONObject news_one = main_object.getJSONObject("news_one");

                            final String id = news_one.get("id").toString().replaceAll("\"", "");
                            final String star = news_one.get("star").toString().replaceAll("\"", "");
                            final String content = news_one.get("content").toString().replaceAll("\"", "");
                            final String heading = news_one.get("heading").toString().replaceAll("\"", "");
                            final String short_content = news_one.get("short_content").toString().replaceAll("\"", "");
                            final String location = news_one.get("location").toString().replaceAll("\"", "");
                            final String main_access_group_id = news_one.get("main_access_group_id").toString().replaceAll("\"", "");
                            final String sub_access_group_id = news_one.get("sub_access_group_id").toString().replaceAll("\"", "");
                            final String file_type = news_one.get("file_type").toString().replaceAll("\"", "");
                            final String file_name = news_one.get("file_name").toString().replaceAll("\"", "");
                            final String delete_flag = news_one.get("delete_flag").toString().replaceAll("\"", "");
                            final String is_active = news_one.get("is_active").toString().replaceAll("\"", "");
                            final String entry_date = news_one.get("entry_date").toString().replaceAll("\"", "");
                            final String modified_date = news_one.get("modified_date").toString().replaceAll("\"", "");
                            final String news_like = news_one.get("news_like").toString().replaceAll("\"", "");
                            final String news_comment = news_one.get("news_comment").toString().replaceAll("\"", "");

                            single_top_news_id = id;
                            Picasso.with(getApplicationContext())
                                    .load(file_name)
                                    .into(img_top);
                             heading_text.setText(short_content);

                             if (Integer.parseInt(news_like) == 0){
                                 img_like.setVisibility(View.GONE);
                                 img_unlike.setVisibility(View.VISIBLE);
                             }else {
                                 img_like.setVisibility(View.VISIBLE);
                                 img_unlike.setVisibility(View.GONE);
                             }

                             like.setText(news_like);
                             comment.setText(news_comment);

                            JSONArray product=main_object.getJSONArray("ongoing_game");

                              if(product.length()==0){
                                recycle_game.removeAllViews();
                              }
                              else {
                                  for (int i = 0; i < product.length(); i++) {

                                  }
                              }

                            JSONArray news_data=main_object.getJSONArray("news_data");
                            if(news_data.length()==0){
                                recycle_news.removeAllViews();
                            }
                            else {
                                for (int i = 0; i < news_data.length(); i++) {
                                    JSONObject item = news_data.getJSONObject(i);
                                    String news_id = item.get("id").toString().replaceAll("\"", "");
                                    String news_star = item.get("star").toString().replaceAll("\"", "");
                                    String news_content = item.get("content").toString().replaceAll("\"", "");
                                    String news_short_content = item.get("short_content").toString().replaceAll("\"", "");
                                    String news_heading = item.get("heading").toString().replaceAll("\"", "");
                                    String news_location = item.get("location").toString().replaceAll("\"", "");
                                    String news_main_access_group_id = item.get("main_access_group_id").toString().replaceAll("\"", "");
                                    String news_sub_access_group_id = item.get("sub_access_group_id").toString().replaceAll("\"", "");
                                    String news_file_type = item.get("file_type").toString().replaceAll("\"", "");
                                    String news_file_name = item.get("file_name").toString().replaceAll("\"", "");
                                    String news_delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                    String news_is_active = item.get("is_active").toString().replaceAll("\"", "");
                                    String news_entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                    String news_modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                    String news_video_img = item.get("video_img").toString().replaceAll("\"", "");
                                    String news_news_like = item.get("news_like").toString().replaceAll("\"", "");
                                    String news_news_comment = item.get("news_comment").toString().replaceAll("\"", "");

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("news_id", news_id);
                                    hashMap.put("news_star", news_star);
                                    hashMap.put("news_content", news_content);
                                    hashMap.put("news_short_content", news_short_content);
                                    hashMap.put("news_heading", news_heading);
                                    hashMap.put("news_location", news_location);
                                    hashMap.put("news_main_access_group_id", news_main_access_group_id);
                                    hashMap.put("news_sub_access_group_id", news_sub_access_group_id);
                                    hashMap.put("news_file_type", news_file_type);
                                    hashMap.put("news_file_name", news_file_name);
                                    hashMap.put("news_delete_flag", news_delete_flag);
                                    hashMap.put("news_is_active", news_is_active);
                                    hashMap.put("news_entry_date", news_entry_date);
                                    hashMap.put("news_modified_date", news_modified_date);
                                    hashMap.put("news_video_img", news_video_img);
                                    hashMap.put("news_news_like", news_news_like);
                                    hashMap.put("news_news_comment", news_news_comment);

                                    //  globalClass.setFolderanme(folder_name);

                                    newsdata.add(hashMap);
                                    Log.d(TAG, "Hashmap " + hashMap);

                                }
                                newsAdapter = new NewsAdapter(HomePage.this,newsdata);
                                recycle_news.setAdapter(newsAdapter);
                                newsAdapter.notifyDataSetChanged();
                            }
                            JSONArray ongoing_game=main_object.getJSONArray("ongoing_game");
                            if(ongoing_game.length()==0){
                                recycle_news.removeAllViews();
                            }
                            else {
                                for (int i = 0; i < ongoing_game.length(); i++) {
                                    JSONObject item = ongoing_game.getJSONObject(i);
                                    String game_id = item.get("id").toString().replaceAll("\"", "");
                                    String match_id = item.get("match_id").toString().replaceAll("\"", "");
                                    String teamA = item.get("teamA").toString().replaceAll("\"", "");
                                    String teamB = item.get("teamB").toString().replaceAll("\"", "");
                                    String date_and_time = item.get("date_and_time").toString().replaceAll("\"", "");
                                    String match_name = item.get("match_name").toString().replaceAll("\"", "");
                                    String image = item.get("image").toString().replaceAll("\"", "");
                                    String match_type = item.get("match_type").toString().replaceAll("\"", "");
                                    String team_1_name = item.get("team_1_name").toString().replaceAll("\"", "");
                                    String team_1_image = item.get("team_1_image").toString().replaceAll("\"", "");
                                    String team_2_name = item.get("team_2_name").toString().replaceAll("\"", "");
                                    String team_2_image = item.get("team_2_image").toString().replaceAll("\"", "");
                                    String live_score_team_A = item.get("live_score_team_A").toString().replaceAll("\"", "");
                                    String live_score_team_B = item.get("live_score_team_B").toString().replaceAll("\"", "");


                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("game_id", game_id);
                                    hashMap.put("match_id", match_id);
                                    hashMap.put("teamA", teamA);
                                    hashMap.put("teamB", teamB);
                                    hashMap.put("date_and_time", date_and_time);
                                    hashMap.put("match_name", match_name);
                                    hashMap.put("image", image);
                                    hashMap.put("match_type", match_type);
                                    hashMap.put("team_1_name", team_1_name);
                                    hashMap.put("team_1_image", team_1_image);
                                    hashMap.put("team_2_name", team_2_name);
                                    hashMap.put("team_2_image", team_2_image);
                                    hashMap.put("live_score_team_A", live_score_team_A);
                                    hashMap.put("live_score_team_B", live_score_team_B);


                                    //  globalClass.setFolderanme(folder_name);

                                    gamedata_search.add(hashMap);
                                    Log.d(TAG, "Hashmap " + hashMap);

                                }
                                gameAdapter = new GameAdapter(HomePage.this,gamedata_search);
                                recycle_game.setAdapter(gameAdapter);
                               // newsAdapter.notifyDataSetChanged();
                            }

                        }else {

                            rl_homescreen.setVisibility(View.GONE);
                            if (message.equals("News not found.")){
                                FancyToast.makeText(getApplicationContext(),"",
                                        FancyToast.LENGTH_LONG, FancyToast.INFO, false)
                                        .show();
                            }
                            else {

                                FancyToast.makeText(getApplicationContext(), message,
                                        FancyToast.LENGTH_LONG, FancyToast.INFO, false)
                                        .show();
                            }

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

                params.put("location",location);
                params.put("main_access_group_id", main_access_group_id);
                params.put("sub_access_group_id", sub_access_group_id);


                Log.d(TAG, " param3: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    private void NewsSearch(final String search_text) {


        String tag_string_req = "forget_password";

      //  pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                NEWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                 //   pd.dismiss();
                    newsdata_search.clear();
                    newsdata.clear();
                    recycle_game.removeAllViews();
                    recycle_news.removeAllViews();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            rl_homescreen.setVisibility(View.VISIBLE);

                            JSONObject news_one = main_object.getJSONObject("news_one");

                            final String id = news_one.get("id").toString().replaceAll("\"", "");
                            final String star = news_one.get("star").toString().replaceAll("\"", "");
                            final String content = news_one.get("content").toString().replaceAll("\"", "");
                            final String heading = news_one.get("heading").toString().replaceAll("\"", "");
                            final String short_content = news_one.get("short_content").toString().replaceAll("\"", "");
                            final String location = news_one.get("location").toString().replaceAll("\"", "");
                            final String main_access_group_id = news_one.get("main_access_group_id").toString().replaceAll("\"", "");
                            final String sub_access_group_id = news_one.get("sub_access_group_id").toString().replaceAll("\"", "");
                            final String file_type = news_one.get("file_type").toString().replaceAll("\"", "");
                            final String file_name = news_one.get("file_name").toString().replaceAll("\"", "");
                            final String delete_flag = news_one.get("delete_flag").toString().replaceAll("\"", "");
                            final String is_active = news_one.get("is_active").toString().replaceAll("\"", "");
                            final String entry_date = news_one.get("entry_date").toString().replaceAll("\"", "");
                            final String modified_date = news_one.get("modified_date").toString().replaceAll("\"", "");
                            final String news_like = news_one.get("news_like").toString().replaceAll("\"", "");
                            final String news_comment = news_one.get("news_comment").toString().replaceAll("\"", "");

                            single_top_news_id = id;

                            Picasso.with(getApplicationContext())
                                    .load(file_name)
                                    .into(img_top);
                            heading_text.setText(short_content);
                            like.setText(news_like);
                            comment.setText(news_comment);
                            JSONArray ongoing_game=main_object.getJSONArray("ongoing_game");

                            if(ongoing_game.length()==0){
                                recycle_game.removeAllViews();
                            } else {
                                for (int i = 0; i < ongoing_game.length(); i++) {

                                }
                            }

                            JSONArray news_data=main_object.getJSONArray("news_data");
                            if(news_data.length()==0){
                                recycle_news.removeAllViews();
                            } else {
                                for (int i = 0; i < news_data.length(); i++) {
                                    JSONObject item = news_data.getJSONObject(i);
                                    String news_id = item.get("id").toString().replaceAll("\"", "");
                                    String news_star = item.get("star").toString().replaceAll("\"", "");
                                    String news_content = item.get("content").toString().replaceAll("\"", "");
                                    String news_short_content = item.get("short_content").toString().replaceAll("\"", "");
                                    String news_heading = item.get("heading").toString().replaceAll("\"", "");
                                    String news_location = item.get("location").toString().replaceAll("\"", "");
                                    String news_main_access_group_id = item.get("main_access_group_id").toString().replaceAll("\"", "");
                                    String news_sub_access_group_id = item.get("sub_access_group_id").toString().replaceAll("\"", "");
                                    String news_file_type = item.get("file_type").toString().replaceAll("\"", "");
                                    String news_file_name = item.get("file_name").toString().replaceAll("\"", "");
                                    String news_delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                    String news_is_active = item.get("is_active").toString().replaceAll("\"", "");
                                    String news_entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                    String news_modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                    String news_video_img = item.get("video_img").toString().replaceAll("\"", "");
                                    String news_news_like = item.get("news_like").toString().replaceAll("\"", "");
                                    String news_news_comment = item.get("news_comment").toString().replaceAll("\"", "");

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("news_id", news_id);
                                    hashMap.put("news_star", news_star);
                                    hashMap.put("news_content", news_content);
                                    hashMap.put("news_short_content", news_short_content);
                                    hashMap.put("news_heading", news_heading);
                                    hashMap.put("news_location", news_location);
                                    hashMap.put("news_main_access_group_id", news_main_access_group_id);
                                    hashMap.put("news_sub_access_group_id", news_sub_access_group_id);
                                    hashMap.put("news_file_type", news_file_type);
                                    hashMap.put("news_file_name", news_file_name);
                                    hashMap.put("news_delete_flag", news_delete_flag);
                                    hashMap.put("news_is_active", news_is_active);
                                    hashMap.put("news_entry_date", news_entry_date);
                                    hashMap.put("news_modified_date", news_modified_date);
                                    hashMap.put("news_video_img", news_video_img);
                                    hashMap.put("news_news_like", news_news_like);
                                    hashMap.put("news_news_comment", news_news_comment);

                                    //  globalClass.setFolderanme(folder_name);

                                    newsdata_search.add(hashMap);
                                    Log.d(TAG, "Hashmap " + hashMap);

                                }
                                newsAdapter = new NewsAdapter(HomePage.this, newsdata_search);
                                recycle_news.setAdapter(newsAdapter);
                                newsAdapter.notifyDataSetChanged();
                            }
                            JSONArray ongoing_game1=main_object.getJSONArray("ongoing_game");
                            if(ongoing_game1.length()==0){
                                recycle_news.removeAllViews();
                            }
                            else {
                                for (int i = 0; i < ongoing_game1.length(); i++) {
                                    JSONObject item = ongoing_game1.getJSONObject(i);
                                    String game_id = item.get("id").toString().replaceAll("\"", "");
                                    String match_id = item.get("match_id").toString().replaceAll("\"", "");
                                    String teamA = item.get("teamA").toString().replaceAll("\"", "");
                                    String teamB = item.get("teamB").toString().replaceAll("\"", "");
                                    String date_and_time = item.get("date_and_time").toString().replaceAll("\"", "");
                                    String match_name = item.get("match_name").toString().replaceAll("\"", "");
                                    String image = item.get("image").toString().replaceAll("\"", "");
                                    String match_type = item.get("match_type").toString().replaceAll("\"", "");
                                    String team_1_name = item.get("team_1_name").toString().replaceAll("\"", "");
                                    String team_1_image = item.get("team_1_image").toString().replaceAll("\"", "");
                                    String team_2_name = item.get("team_2_name").toString().replaceAll("\"", "");
                                    String team_2_image = item.get("team_2_image").toString().replaceAll("\"", "");
                                    String live_score_team_A = item.get("live_score_team_A").toString().replaceAll("\"", "");
                                    String live_score_team_B = item.get("live_score_team_B").toString().replaceAll("\"", "");


                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("game_id", game_id);
                                    hashMap.put("match_id", match_id);
                                    hashMap.put("teamA", teamA);
                                    hashMap.put("teamB", teamB);
                                    hashMap.put("date_and_time", date_and_time);
                                    hashMap.put("match_name", match_name);
                                    hashMap.put("image", image);
                                    hashMap.put("match_type", match_type);
                                    hashMap.put("team_1_name", team_1_name);
                                    hashMap.put("team_1_image", team_1_image);
                                    hashMap.put("team_2_name", team_2_name);
                                    hashMap.put("team_2_image", team_2_image);
                                    hashMap.put("live_score_team_A", live_score_team_A);
                                    hashMap.put("live_score_team_B", live_score_team_B);


                                    //  globalClass.setFolderanme(folder_name);

                                    gamedata_search.add(hashMap);
                                    Log.d(TAG, "Hashmap " + hashMap);

                                }
                                gameAdapter = new GameAdapter(HomePage.this, gamedata_search);
                                recycle_game.setAdapter(gameAdapter);
                            }
                        }else {

                            rl_homescreen.setVisibility(View.GONE);
                            if (message.equals("News not found.")){

                            }
                            else {

                                FancyToast.makeText(getApplicationContext(), message,
                                        FancyToast.LENGTH_LONG, FancyToast.INFO, false)
                                        .show();
                            }


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

                params.put("location", search_text);
                params.put("main_access_group_id", "0");
                params.put("sub_access_group_id", "0");


                Log.d(TAG, " param3: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

    @Override
    protected void onPause() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);
        super.onPause();
    }
}
