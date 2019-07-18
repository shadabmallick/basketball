package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterTeamAll;
import com.sport.supernathral.AdapterClass.PlayerAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.SearchPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.COACHTEAM;
import static com.sport.supernathral.NetworkConstant.AppConfig.SEARCH_USER;

public class Team_All extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "Team_All";
    ArrayList<String> newsList;
    ArrayList<HashMap<String,String>> coachArraylist;

    ImageView img_header;
    AdapterTeamAll adapterNotes;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    RecyclerView recycle_notes;
    TextView toolbar_title,tv_date_select;
    ProgressDialog pd;
    String sponsor,notes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_all, container, false);
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

         sponsor = getArguments().getString("from");

        Log.d(TAG, "From: "+sponsor);



        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());

        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        tv_date_select=view.findViewById(R.id.tv_date_select);
        toolbar_title.setText(getResources().getString(R.string.team));

        img_header = view.findViewById(R.id.img_header);
        recycle_notes = view.findViewById(R.id.recycle_date);

        coachArraylist = new ArrayList<>();
       /* newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");*/

        int numberOfColumns = 2;
        recycle_notes.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
       /* adapterNotes
                = new AdapterTeamAll(getActivity(), newsList,sponsor);
        recycle_notes.setAdapter(adapterNotes);*/

        CoachTeam();
    }



    private void CoachTeam() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                COACHTEAM, new Response.Listener<String>() {

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
                                String user_name = item.get("name").toString().replaceAll("\"", "");
                                String user_image = item.get("image").toString().replaceAll("\"", "");
                                String coach_id = item.get("coach_id").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);
                                hashMap.put("user_name", user_name);
                                hashMap.put("user_image", user_image);
                                hashMap.put("coach_id", coach_id);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                coachArraylist.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterNotes   = new AdapterTeamAll(getActivity(),coachArraylist,sponsor);
                            recycle_notes.setAdapter(adapterNotes);
                            adapterNotes.notifyDataSetChanged();



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

                params.put("coach_id",globalClass.getId());



                Log.d(TAG, " param3: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}