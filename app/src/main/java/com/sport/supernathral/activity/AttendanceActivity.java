package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterSchduleUserwise;
import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.AdapterClass.AdapterTeam;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.ATTENDENCELIST;
import static com.sport.supernathral.NetworkConstant.AppConfig.GETATTENDENCE;

public class AttendanceActivity extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    AdapterSchduleUserwise adapterNotes;
    ArrayList<HashMap<String,String>> schedule_ArrayList;
    ArrayList<String> newsList;
    RecyclerView recycle_student_list;
    String schedule_id,team_id,datechange;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendence_team_datewise);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(AttendanceActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(AttendanceActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        schedule_id=getIntent().getStringExtra("schedule_id");
        datechange=getIntent().getStringExtra("datechange");
        team_id=getIntent().getStringExtra("team_id");
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

        recycle_student_list = findViewById(R.id.recycle_student_list);

        //rv_category = view.findViewById(R.id.recycler_chat);
        schedule_ArrayList = new ArrayList<>();



        recycle_student_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AttendnceList();


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
    public void AttendnceList() {
        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                ATTENDENCELIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();
                        schedule_ArrayList.clear();
                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){




                            JSONArray news_data=main_object.getJSONArray("data");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");

                                String team_id = item.get("team_id").toString().replaceAll("\"", "");
                                String player_id = item.get("player_id").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                String user_image = item.get("user_image").toString().replaceAll("\"", "");
                                String present = item.get("present").toString().replaceAll("\"", "");
                                String late = item.get("late").toString().replaceAll("\"", "");
                                String absent = item.get("absent").toString().replaceAll("\"", "");
                                String excuses = item.get("excuses").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("user_name", user_name);
                                hashMap.put("user_image", user_image);
                                hashMap.put("present", present);
                                hashMap.put("late", late);
                                hashMap.put("absent", absent);
                                hashMap.put("excuses", excuses);
                                hashMap.put("team_id", team_id);
                                hashMap.put("player_id", player_id);
                                schedule_ArrayList.add(hashMap);

                            }
                            adapterNotes
                                    = new AdapterSchduleUserwise(AttendanceActivity.this, schedule_ArrayList,pd,schedule_id,datechange);
                            recycle_student_list.setAdapter(adapterNotes);


                        }else {


                            FancyToast.makeText(AttendanceActivity.this, message,
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

                params.put("team_id",team_id);
                params.put("schedule_id",schedule_id);
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}