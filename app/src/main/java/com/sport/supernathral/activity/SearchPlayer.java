package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.NewsAdapter;
import com.sport.supernathral.AdapterClass.PlayerAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS;
import static com.sport.supernathral.NetworkConstant.AppConfig.SEARCH_USER;

public class SearchPlayer extends AppCompatActivity {
    String TAG="";
    RecyclerView recycleList;
    LinearLayout ll_search,ll_main;
    ImageView img_search;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    EditText edt_search;
    PlayerAdapter notificationListAdapter;
    ArrayList<HashMap<String,String>> newsdata_search;
    RelativeLayout rl_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        globalClass = (GlobalClass) getApplicationContext();
        preference = new Shared_Preference(SearchPlayer.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(SearchPlayer.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");



        initViews();
        recycleList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycleList.setNestedScrollingEnabled(false);
    }

    private void initViews() {
        recycleList=findViewById(R.id.recycleList);
        img_search=findViewById(R.id.img_search);
        ll_search=findViewById(R.id.ll_search);
        ll_main=findViewById(R.id.ll_main);
        edt_search=findViewById(R.id.edt_search);
        rl_login=findViewById(R.id.rl_login);
         newsdata_search=new ArrayList<>();



        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edt_search.getText().length()>0){
                    ll_main.setVisibility(View.GONE);
                    recycleList.setVisibility(View.VISIBLE);
                    NewsSearch(edt_search.getText().toString().trim());
                }


            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void NewsSearch(final String location) {


        String tag_string_req = "forget_password";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                SEARCH_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();
                    newsdata_search.clear();
                    recycleList.removeAllViews();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){




                            JSONArray news_data=main_object.getJSONArray("data");


                                for (int i = 0; i < news_data.length(); i++) {
                                    JSONObject item = news_data.getJSONObject(i);
                                    String user_id = item.get("user_id").toString().replaceAll("\"", "");
                                    String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                    String user_image = item.get("user_image").toString().replaceAll("\"", "");
                                    String user_type = item.get("user_type").toString().replaceAll("\"", "");

                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("user_id", user_id);
                                    hashMap.put("user_name", user_name);
                                    hashMap.put("user_image", user_image);
                                    hashMap.put("user_type", user_type);
                                    newsdata_search.add(hashMap);
                                    Log.d(TAG, "Hashmap " + hashMap);
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);

                                }
                            notificationListAdapter   = new PlayerAdapter(SearchPlayer.this,newsdata_search);
                            recycleList.setAdapter(notificationListAdapter);
                            notificationListAdapter.notifyDataSetChanged();



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

                params.put("user_name",location);



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


