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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterAttendence;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


import static com.sport.supernathral.NetworkConstant.AppConfig.GET_PLAYER_ATTENDENCE;

public class AttendenceParent  extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    CircleImageView profile_image;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> schedule_ArrayList;
    LinearLayout ll_txt_about;
    AdapterAttendence adapterNotes;
    LinearLayout ll_present,ll_absent,ll_late,ll_excuse;
    ArrayList<String> newsList;
    RecyclerView recycle_student_list;
    TextView tv_present,tv_absent,tv_late,tv_excuse,tv_name,tool_bar_title;
    String player_id,type,formattedDate,team_id,schedule_id,datechange;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendence);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(AttendenceParent.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(AttendenceParent.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        player_id=getIntent().getStringExtra("player_id");


        initView();
        initialisation();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        tv_absent = findViewById(R.id.tv_absent);
        tv_excuse = findViewById(R.id.tv_excuse);
        tv_present = findViewById(R.id.tv_present);
        tv_late = findViewById(R.id.tv_late);
        tv_name = findViewById(R.id.tv_name);
        profile_image=findViewById(R.id.profile_image);
        ll_absent=findViewById(R.id.ll_absent);
        ll_late=findViewById(R.id.ll_late);
        ll_present=findViewById(R.id.ll_present);
        ll_excuse=findViewById(R.id.ll_excuse);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tool_bar_title=findViewById(R.id.toolbar_title);
        tool_bar_title.setText("Student List");






    }

    private void initialisation() {

        recycle_student_list = findViewById(R.id.recycle_attendence);

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
    private void AttendnceList() {


        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GET_PLAYER_ATTENDENCE, new Response.Listener<String>() {

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

                            JSONObject data = main_object.getJSONObject("data");

                            String user_name = data.get("user_name").toString().replaceAll("\"", "");
                            String user_image = data.get("user_image").toString().replaceAll("\"", "");
                            String present = data.get("present").toString().replaceAll("\"", "");
                            String late = data.get("late").toString().replaceAll("\"", "");
                            String absent = data.get("absent").toString().replaceAll("\"", "");
                            String excuses = data.get("excuses").toString().replaceAll("\"", "");
                            if (!user_image.isEmpty()) {
                                Picasso.with(getApplicationContext()).load(user_image).placeholder(R.mipmap.avatar_gray).into(profile_image);
                            }
                            tv_name.setText(user_name);
                            tv_present.setText(present);
                            tv_absent.setText(absent);
                            tv_late.setText(late);
                            tv_excuse.setText(excuses);
                            JSONArray news_data=data.getJSONArray("attendance_list");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");

                                String player_id = item.get("player_id").toString().replaceAll("\"", "");
                                schedule_id = item.get("schedule_id").toString().replaceAll("\"", "");
                                team_id = item.get("team_id").toString().replaceAll("\"", "");
                                String date = item.get("date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String attendance_by = item.get("attendance_by").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String sub_present = item.get("present").toString().replaceAll("\"", "");
                                String sub_late = item.get("late").toString().replaceAll("\"", "");
                                String sub_absent = item.get("absent").toString().replaceAll("\"", "");
                                String sub_excuses = item.get("excuses").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("present", sub_present);
                                hashMap.put("late", sub_late);
                                hashMap.put("absent", sub_absent);
                                hashMap.put("excuses", sub_excuses);
                                hashMap.put("team_id", team_id);
                                hashMap.put("player_id", player_id);
                                hashMap.put("schedule_id", schedule_id);
                                hashMap.put("attendance_by", attendance_by);
                                hashMap.put("date", date);
                                schedule_ArrayList.add(hashMap);

                            }
                            adapterNotes
                                    = new AdapterAttendence(AttendenceParent.this, schedule_ArrayList);
                            recycle_student_list.setAdapter(adapterNotes);


                        }else {


                            FancyToast.makeText(AttendenceParent.this, message,
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

                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}