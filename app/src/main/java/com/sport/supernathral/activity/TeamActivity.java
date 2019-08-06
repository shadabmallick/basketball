package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.sport.supernathral.AdapterClass.AdapterTeam;
import com.sport.supernathral.AdapterClass.AdapterTeamAll;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.COACHTEAM;

public class TeamActivity
        extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    ArrayList<HashMap<String,String>> coachArraylist;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    AdapterTeamAll adapterNotes;
    ArrayList<String> newsList;
    RecyclerView recycle_notes;
    String from;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(TeamActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(TeamActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        initView();
        initialisation();

    }
    public void initView(){
        from =  getIntent().getStringExtra("from");
        Log.d(TAG, "initView: "+from);
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

        recycle_notes = findViewById(R.id.recycler_team);

        coachArraylist = new ArrayList<>();

        int numberOfColumns = 2;
        recycle_notes.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
        CoachTeam();


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
                            adapterNotes   = new AdapterTeamAll(TeamActivity.this,coachArraylist,from);
                            recycle_notes.setAdapter(adapterNotes);
                            adapterNotes.notifyDataSetChanged();



                        }else {


                            FancyToast.makeText(TeamActivity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(TeamActivity.this, "Connection error",
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