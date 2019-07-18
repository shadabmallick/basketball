package com.sport.supernathral.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.AdapterClass.AdapterTeam;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ScheduleActvity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.EVENTLIST;
import static com.sport.supernathral.NetworkConstant.AppConfig.GETATTENDENCE;

public class Team  extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    AdapterTeam adapterNotes;
    LinearLayout ll_data;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    RecyclerView recycle_notes;
    ArrayList<HashMap<String,String>> schedule_ArrayList;
    TextView toolbar_title,tv_date_select;
    ProgressDialog pd;
    String value;
    String access_date,end_date_str,datechange;

    Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendence_team, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());

        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        tv_date_select=view.findViewById(R.id.tv_date_select);
        toolbar_title.setText(getResources().getString(R.string.team));

        img_header = view.findViewById(R.id.img_header);
        ll_data = view.findViewById(R.id.ll_data);
        recycle_notes = view.findViewById(R.id.recycle_date);
        tv_date_select.setText(date);
        //rv_category = view.findViewById(R.id.recycler_chat);
        schedule_ArrayList = new ArrayList<>();


        int numberOfColumns = 2;
        recycle_notes.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        AttendenceList();

        ll_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener3, mYear, mMonth, mDay).show();
            }
        });



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


                    tv_date_select.setText(access_date);
                    AttendenceListOnClick();

                }
            };


    private void AttendenceList() {


        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GETATTENDENCE, new Response.Listener<String>() {

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
                                String team_id = item.get("team_id").toString().replaceAll("\"", "");
                                String team_name = item.get("team_name").toString().replaceAll("\"", "");


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
                                hashMap.put("team_id", team_id);
                                hashMap.put("attendance", attendance);
                                hashMap.put("team_name", team_name);
                                schedule_ArrayList.add(hashMap);

                            }
                            adapterNotes
                                    = new AdapterTeam(getActivity(), schedule_ArrayList,value,datechange);
                            recycle_notes.setAdapter(adapterNotes);



                        }else {


                            FancyToast.makeText(getActivity(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(getActivity(), "Connection error",
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
                try {
                    //   Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(access_date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date strDate1 = sdf.parse(date);
                    Date strDate2 = sdf.parse(access_date);
                    Log.d(TAG, "getParams: "+date);
                    Log.d(TAG, "getParams: "+access_date);

                    if (strDate1.compareTo(strDate2)==0) {
                        datechange="today";
                        Log.d(TAG, "getParams "+datechange);

                    }
                    if(strDate1.compareTo(strDate2)<0) {
                        datechange="after";
                        Log.d(TAG,"getParams  " + datechange);
                    }
                    if(strDate1.compareTo(strDate2)>0) {
                        datechange="previous";
                        Log.d(TAG,"getParams  " + datechange);
                    }




                }catch (ParseException e) {
                    //Handle exception here
                    e.printStackTrace();
                }
                params.put("coach_id",globalClass.getId());
                params.put("date",date);

                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void AttendenceListOnClick() {


        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GETATTENDENCE, new Response.Listener<String>() {

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
                                String team_id = item.get("team_id").toString().replaceAll("\"", "");
                                String team_name = item.get("team_name").toString().replaceAll("\"", "");


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
                                hashMap.put("team_id", team_id);
                                hashMap.put("attendance", attendance);
                                hashMap.put("team_name", team_name);
                                schedule_ArrayList.add(hashMap);

                            }
                            adapterNotes
                                    = new AdapterTeam(getActivity(), schedule_ArrayList,value,datechange);
                            recycle_notes.setAdapter(adapterNotes);



                        }else {


                            FancyToast.makeText(getActivity(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(getActivity(), "Connection error",
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

                try {
                 //   Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(access_date);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date strDate1 = sdf.parse(date);
                    Date strDate2 = sdf.parse(access_date);
                    Log.d(TAG, "getParams: "+date);
                    Log.d(TAG, "getParams: "+access_date);

                    if (strDate1.compareTo(strDate2)==0) {
                      datechange="today";
                        Log.d(TAG, "getParams "+datechange);

                    }
                     if(strDate1.compareTo(strDate2)<0) {
                         datechange="after";
                        Log.d(TAG,"getParams  " + datechange);
                    }
                    if(strDate1.compareTo(strDate2)>0) {
                        datechange="previous";
                        Log.d(TAG,"getParams  " + datechange);
                    }




                }catch (ParseException e) {
                    //Handle exception here
                    e.printStackTrace();
                }
                Map<String, String> params = new HashMap<>();

                params.put("coach_id",globalClass.getId());
                params.put("date",tv_date_select.getText().toString().trim());
                Log.d(TAG, " player_id: "+params);
                Log.d(TAG, " player_id: "+access_date);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
