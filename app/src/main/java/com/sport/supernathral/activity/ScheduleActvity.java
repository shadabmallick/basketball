package com.sport.supernathral.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterSchedule;
import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.ABOUT_US;
import static com.sport.supernathral.NetworkConstant.AppConfig.GETSCHEDULE;
import static com.sport.supernathral.NetworkConstant.AppConfig.STUDENTLIST;

public class ScheduleActvity extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> schedule_ArrayList;
    ProgressDialog pd;
    LinearLayout ll_find_schedule,ll_end_date;
    TextView tv_about,start_date,end_date;
    RecyclerView recycle_schedule;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    AdapterSchedule adapterSchedule;
    LinearLayout ll_start_date;
    Calendar myCalendar = Calendar.getInstance();
    String access_date,end_date_str,sub_access_group_id,main_access_group_id;

    String from,team_id,today,newDate;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_actiivty);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(ScheduleActvity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(ScheduleActvity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        from=getIntent().getStringExtra("from");
        team_id=getIntent().getStringExtra("team_id");
        team_id=getIntent().getStringExtra("team_id");
        sub_access_group_id=getIntent().getStringExtra("sub_access_group_id");
        main_access_group_id=getIntent().getStringExtra("main_access_group_id");
        Date date = new Date();
        today= new SimpleDateFormat("yyyy-MM-dd").format(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Log.d(TAG, "onCreate: "+today);
        myCalendar.add(Calendar.DAY_OF_MONTH, 7);
        newDate = sdf.format(myCalendar.getTime());
        initView();
        ScheduleListOnLoad();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        recycle_schedule = findViewById(R.id.recycle_schedule);
        ll_find_schedule = findViewById(R.id.ll_find_schedule);
        ll_start_date=findViewById(R.id.ll_start_date);
        start_date=findViewById(R.id.start_date);
        end_date=findViewById(R.id.end_date);
        ll_end_date=findViewById(R.id.ll_end_date);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tv_about=findViewById(R.id.about_us);
        ll_find_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(globalClass.getType().equals("Coach/Teachers")){
                    ScheduleList();
                }
                else {
                    ScheduleListOnLoad();
                }
            }
        });
        ll_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(ScheduleActvity.this, datePickerListener3, mYear, mMonth, mDay).show();
            }
        });
        ll_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(ScheduleActvity.this, datePickerListener1, mYear, mMonth, mDay).show();
            }
        });
        schedule_ArrayList = new ArrayList<>();

        recycle_schedule.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private DatePickerDialog.OnDateSetListener datePickerListener3 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {


                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    access_date = sdf1.format(myCalendar.getTime());


                    start_date.setText(access_date);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {


                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    end_date_str = sdf1.format(myCalendar.getTime());


                    end_date.setText(end_date_str);

                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    private void ScheduleList() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GETSCHEDULE, new Response.Listener<String>() {

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
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String parent_id = item.get("parent_id").toString().replaceAll("\"", "");
                                String schedule_type = item.get("schedule_type").toString().replaceAll("\"", "");
                                String main_access_group_id = item.get("main_access_group_id").toString().replaceAll("\"", "");
                                String sub_access_group_id = item.get("sub_access_group_id").toString().replaceAll("\"", "");
                                String team = item.get("team").toString().replaceAll("\"", "");
                                String event_name = item.get("event_name").toString().replaceAll("\"", "");
                                String event_start_date = item.get("event_start_date").toString().replaceAll("\"", "");
                                String event_end_date = item.get("event_end_date").toString().replaceAll("\"", "");
                                String event_desc = item.get("event_desc").toString().replaceAll("\"", "");
                                String location = item.get("location").toString().replaceAll("\"", "");
                                String comment = item.get("comment").toString().replaceAll("\"", "");
                                String push_noti = item.get("push_noti").toString().replaceAll("\"", "");
                                String email_noti = item.get("email_noti").toString().replaceAll("\"", "");
                                String file_name = item.get("file_name").toString().replaceAll("\"", "");
                                String access_1 = item.get("access_1").toString().replaceAll("\"", "");
                                String access_2 = item.get("access_2").toString().replaceAll("\"", "");
                                String access_3 = item.get("access_3").toString().replaceAll("\"", "");
                                String sub_access_1 = item.get("sub_access_1").toString().replaceAll("\"", "");
                                String sub_access_2 = item.get("sub_access_2").toString().replaceAll("\"", "");
                                String sub_access_3 = item.get("sub_access_3").toString().replaceAll("\"", "");
                                String recurring = item.get("recurring").toString().replaceAll("\"", "");
                                String interval = item.get("interval").toString().replaceAll("\"", "");
                                String recurring_end_date = item.get("recurring_end_date").toString().replaceAll("\"", "");
                                String attendance = item.get("attendance").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("parent_id", parent_id);
                                hashMap.put("schedule_type", schedule_type);
                                hashMap.put("main_access_group_id", main_access_group_id);
                                hashMap.put("is_active", is_active);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("sub_access_group_id", sub_access_group_id);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("team", team);
                                hashMap.put("event_name", event_name);
                                hashMap.put("event_start_date", event_start_date);
                                hashMap.put("event_end_date", event_end_date);
                                hashMap.put("event_desc", event_desc);
                                hashMap.put("location", location);
                                hashMap.put("comment", comment);
                                hashMap.put("push_noti", push_noti);
                                hashMap.put("email_noti", email_noti);
                                hashMap.put("file_name", file_name);
                                hashMap.put("access_1", access_1);
                                hashMap.put("access_2", access_2);
                                hashMap.put("access_3", access_3);
                                hashMap.put("sub_access_1", sub_access_1);
                                hashMap.put("sub_access_2", sub_access_2);
                                hashMap.put("sub_access_3", sub_access_3);
                                hashMap.put("sub_access_3", sub_access_3);
                                hashMap.put("interval", interval);
                                hashMap.put("recurring", recurring);
                                hashMap.put("recurring_end_date", recurring_end_date);
                                hashMap.put("attendance", attendance);
                                schedule_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterSchedule
                                    = new AdapterSchedule(ScheduleActvity.this, schedule_ArrayList,from);
                            recycle_schedule.setAdapter(adapterSchedule);
                            //adpterStudentList.notifyDataSetChanged();


                        }else {


                            FancyToast.makeText(ScheduleActvity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(ScheduleActvity.this, "Connection error",
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

                params.put("start_date",start_date.getText().toString());
                params.put("end_date",end_date.getText().toString());
                params.put("team_id",team_id);







                Log.d(TAG, " STUDENTLIST: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void ScheduleListOnLoad() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GETSCHEDULE, new Response.Listener<String>() {

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
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String parent_id = item.get("parent_id").toString().replaceAll("\"", "");
                                String schedule_type = item.get("schedule_type").toString().replaceAll("\"", "");
                                String main_access_group_id = item.get("main_access_group_id").toString().replaceAll("\"", "");
                                String sub_access_group_id = item.get("sub_access_group_id").toString().replaceAll("\"", "");
                                String team = item.get("team").toString().replaceAll("\"", "");
                                String event_name = item.get("event_name").toString().replaceAll("\"", "");
                                String event_start_date = item.get("event_start_date").toString().replaceAll("\"", "");
                                String event_end_date = item.get("event_end_date").toString().replaceAll("\"", "");
                                String event_desc = item.get("event_desc").toString().replaceAll("\"", "");
                                String location = item.get("location").toString().replaceAll("\"", "");
                                String comment = item.get("comment").toString().replaceAll("\"", "");
                                String push_noti = item.get("push_noti").toString().replaceAll("\"", "");
                                String email_noti = item.get("email_noti").toString().replaceAll("\"", "");
                                String file_name = item.get("file_name").toString().replaceAll("\"", "");
                                String access_1 = item.get("access_1").toString().replaceAll("\"", "");
                                String access_2 = item.get("access_2").toString().replaceAll("\"", "");
                                String access_3 = item.get("access_3").toString().replaceAll("\"", "");
                                String sub_access_1 = item.get("sub_access_1").toString().replaceAll("\"", "");
                                String sub_access_2 = item.get("sub_access_2").toString().replaceAll("\"", "");
                                String sub_access_3 = item.get("sub_access_3").toString().replaceAll("\"", "");
                                String recurring = item.get("recurring").toString().replaceAll("\"", "");
                                String interval = item.get("interval").toString().replaceAll("\"", "");
                                String recurring_end_date = item.get("recurring_end_date").toString().replaceAll("\"", "");
                                String attendance = item.get("attendance").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("parent_id", parent_id);
                                hashMap.put("schedule_type", schedule_type);
                                hashMap.put("main_access_group_id", main_access_group_id);
                                hashMap.put("is_active", is_active);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("sub_access_group_id", sub_access_group_id);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("team", team);
                                hashMap.put("event_name", event_name);
                                hashMap.put("event_start_date", event_start_date);
                                hashMap.put("event_end_date", event_end_date);
                                hashMap.put("event_desc", event_desc);
                                hashMap.put("location", location);
                                hashMap.put("comment", comment);
                                hashMap.put("push_noti", push_noti);
                                hashMap.put("email_noti", email_noti);
                                hashMap.put("file_name", file_name);
                                hashMap.put("access_1", access_1);
                                hashMap.put("access_2", access_2);
                                hashMap.put("access_3", access_3);
                                hashMap.put("sub_access_1", sub_access_1);
                                hashMap.put("sub_access_2", sub_access_2);
                                hashMap.put("sub_access_3", sub_access_3);
                                hashMap.put("sub_access_3", sub_access_3);
                                hashMap.put("interval", interval);
                                hashMap.put("recurring", recurring);
                                hashMap.put("recurring_end_date", recurring_end_date);
                                hashMap.put("attendance", attendance);
                                schedule_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterSchedule
                                    = new AdapterSchedule(ScheduleActvity.this, schedule_ArrayList,from);
                            recycle_schedule.setAdapter(adapterSchedule);
                            //adpterStudentList.notifyDataSetChanged();


                        }else {


                            FancyToast.makeText(ScheduleActvity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(ScheduleActvity.this, "Connection error",
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

                params.put("start_date",today);
                params.put("end_date",newDate);
                params.put("main_access_group_id",main_access_group_id);
                params.put("sub_access_group_id",sub_access_group_id);






                Log.d(TAG, " STUDENTLIST: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
