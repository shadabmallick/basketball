package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.StatisticsActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSTATISTICS;

public class Statistics  extends Fragment {


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
    BarChart barChart;
    TextView tv_average,tv_goal,tv_goal_average,tv_presence,tv_tardies,tv_absent,tv_no_activity,tv_practice,tv_skill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_frag, container, false);
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

        globalClass=(GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        ll_txt_about = view.findViewById(R.id.ll_txt_about);
        tv_absent = view.findViewById(R.id.tv_absent);
        tv_average = view.findViewById(R.id.tv_average);
        tv_goal = view.findViewById(R.id.tv_goal);
        tv_goal_average = view.findViewById(R.id.tv_goal_average);
        tv_presence = view.findViewById(R.id.tv_presence);
        tv_tardies = view.findViewById(R.id.tv_tardies);
        tv_no_activity = view.findViewById(R.id.tv_no_activity);
        tv_practice = view.findViewById(R.id.tv_practice);
        tv_skill = view.findViewById(R.id.tv_skill);
        barChart =  view.findViewById(R.id.chart);
        StudentList();








    }
    private void initialisation() {

        Log.d(TAG, "initialisation: "+new_number);




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

                params.put("player_id",globalClass.getId());

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
