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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSKILL;


public class SkillActivity  extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> player_ArrayList;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    ArrayList<String> newsList;

    RecyclerView recycle_skill;
    AdapterSkill adapterEvent;
    TextView toolbar_title;
    String player_id,from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_set);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(SkillActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(SkillActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        player_id=getIntent().getStringExtra("player_id");
        from=getIntent().getStringExtra("from");
        initView();
        initialisation();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tv_about=findViewById(R.id.about_us);


    }


    private void initialisation() {
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.skill_set));

        recycle_skill = findViewById(R.id.recycle_skill);

        player_ArrayList = new ArrayList<>();

        recycle_skill.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SkillSet();

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
    private void SkillSet() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                PLAYERSKILL, new Response.Listener<String>() {

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




                            JSONArray news_data=main_object.getJSONArray("data");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String user_id = item.get("id").toString().replaceAll("\"", "");
                                String name = item.get("name").toString().replaceAll("\"", "");
                                String student_skill = item.get("student_skill").toString().replaceAll("\"", "");
                                String date = item.get("date").toString().replaceAll("\"", "");
                                String skill_value = item.get("skill_value").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);
                                hashMap.put("student_skill", student_skill);
                                hashMap.put("name", name);
                                hashMap.put("date", date);
                                hashMap.put("skill_value", skill_value);

                                player_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterEvent
                                    = new AdapterSkill(SkillActivity.this, player_ArrayList,player_id,from);
                            recycle_skill.setAdapter(adapterEvent);
                            //adpterStudentList.notifyDataSetChanged();


                        }else {


                            FancyToast.makeText(SkillActivity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(SkillActivity.this, "Connection error",
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
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}