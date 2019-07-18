package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSTATISTICS;
import static com.sport.supernathral.NetworkConstant.AppConfig.STUDENTLIST;

public class StatisticsActivity extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    String player_id,from,GPA;
    int total=100;
    int rank_percent=0;
    int new_number;
    TextView tv_average,tv_goal,tv_goal_average,tv_presence,tv_tardies,tv_absent,tv_no_activity,tv_practice,tv_skill;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(StatisticsActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(StatisticsActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        player_id=getIntent().getStringExtra("player_id");
        from=getIntent().getStringExtra("from");
        initView();
        StudentList();


    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        tv_absent = findViewById(R.id.tv_absent);
        tv_average = findViewById(R.id.tv_average);
        tv_goal = findViewById(R.id.tv_goal);
        tv_goal_average = findViewById(R.id.tv_goal_average);
        tv_presence = findViewById(R.id.tv_presence);
        tv_tardies = findViewById(R.id.tv_tardies);
        tv_no_activity = findViewById(R.id.tv_no_activity);
        tv_practice = findViewById(R.id.tv_practice);
        tv_skill = findViewById(R.id.tv_skill);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tv_about=findViewById(R.id.about_us);


    }


    private void initialisation() {

        Log.d(TAG, "initialisation: "+new_number);


        BarChart barChart =  findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(rank_percent, 0));
        entries.add(new BarEntry(globalClass.getGpa(), 2));


        Log.d(TAG, "initialisation: "+globalClass.getGpa());
        BarDataSet bardataset = new BarDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

       barChart.setDescription("");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.getLegend().setEnabled(false);
        barChart.animateY(5000);
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
    private void StudentList() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                PLAYERSTATISTICS, new Response.Listener<String>() {

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




                            JSONObject data = main_object.getJSONObject("data");



                                GPA = data.get("GPA").toString().replaceAll("\"", "");
                                String Rank = data.get("Rank").toString().replaceAll("\"", "");
                                String Average = data.get("Average").toString().replaceAll("\"", "");
                                String Goals = data.get("Goals").toString().replaceAll("\"", "");
                                String Goals_Average = data.get("Goals_Average").toString().replaceAll("\"", "");
                                String No_of_activities = data.get("No_of_activities").toString().replaceAll("\"", "");
                                String Presences = data.get("Presences").toString().replaceAll("\"", "");
                                String Tardies = data.get("Tardies").toString().replaceAll("\"", "");
                                String Absences = data.get("Absences").toString().replaceAll("\"", "");
                                String Total_Payments = data.get("Total_Payments").toString().replaceAll("\"", "");
                                String Practice = data.get("Practice").toString().replaceAll("\"", "");
                                String Skill_Set = data.get("Skill_Set").toString().replaceAll("\"", "");

                                 new_number=Integer.parseInt(GPA);
                            globalClass.setGpa(new_number);
                            Log.d(TAG, "onResponse: "+GPA);
                            Log.d(TAG, "onResponse: "+new_number);
                                int rank_value= Integer.parseInt(Rank);
                                 if(rank_value>0){
                                     rank_percent=(total+1)-rank_value;

                                 }
                                Log.d(TAG, "onResponse: "+rank_percent);
                                 tv_goal.setText(Goals);
                                 tv_goal_average.setText(Goals_Average);
                                 tv_average.setText(Average);
                                 tv_practice.setText(Practice);
                                 tv_presence.setText(Presences);
                                 tv_no_activity.setText(No_of_activities);
                                 tv_tardies.setText(Tardies);
                                 tv_absent.setText(Absences);
                                 tv_skill.setText(Skill_Set);

                            initialisation();





                        }else {


                            FancyToast.makeText(StatisticsActivity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(StatisticsActivity.this, "Connection error",
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

                if(globalClass.getType().equals("Coach/Teachers")){
                    params.put("type","team");
                }
                else{
                    params.put("type","parent");
                }




                Log.d(TAG, " STUDENTLIST: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
