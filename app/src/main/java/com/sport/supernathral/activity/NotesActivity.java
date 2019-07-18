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
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterNotesDetails;

import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERNOTES;
import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSKILL;
import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSKILLNOTES;

public class NotesActivity  extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    Toolbar toolbar;
    ArrayList<HashMap<String,String>> skill_notes_Arraylist;
    ArrayList<HashMap<String,String>> skill_notes_Arraylist1;
    AdapterNotesDetails adapterNotesDetails;
    ArrayList<String> newsList;
    RecyclerView recyle_notes;
    String player_id,player_id_notes,skill_name,from;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(NotesActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(NotesActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        player_id=getIntent().getStringExtra("player_id");
        skill_name=getIntent().getStringExtra("skill_name");
        from=getIntent().getStringExtra("from");
        player_id_notes=getIntent().getStringExtra("player_id_notes");

        initView();
        initialisation();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


    }

    private void initialisation() {

        recyle_notes = findViewById(R.id.recyle_notes);

        //rv_category = view.findViewById(R.id.recycler_chat);
        skill_notes_Arraylist = new ArrayList<>();
        skill_notes_Arraylist1 = new ArrayList<>();


        recyle_notes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if(from.equals("Notes")){
            SkillNotesFromNotes();
        }
        else if(from.equals("Student List")) {
            SkillNotes();
        }


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
    private void SkillNotes() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                PLAYERSKILLNOTES, new Response.Listener<String>() {

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




                            JSONArray news_data=main_object.getJSONArray("player_note");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String note = item.get("note").toString().replaceAll("\"", "");
                                String date = item.get("date").toString().replaceAll("\"", "");



                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("note", note);
                                hashMap.put("date", date);
                                skill_notes_Arraylist.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterNotesDetails
                                    = new AdapterNotesDetails(NotesActivity.this, skill_notes_Arraylist,from);
                            recyle_notes.setAdapter(adapterNotesDetails);



                        }else {


                            FancyToast.makeText(NotesActivity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(NotesActivity.this, "Connection error",
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
                params.put("skill_name",skill_name);
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }
    private void SkillNotesFromNotes() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                PLAYERNOTES, new Response.Listener<String>() {

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




                            JSONArray news_data=main_object.getJSONArray("player_note");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String note = item.get("note").toString().replaceAll("\"", "");
                                String title = item.get("title").toString().replaceAll("\"", "");
                                String date = item.get("entry_date").toString().replaceAll("\"", "");
                                String desc = item.get("desc").toString().replaceAll("\"", "");



                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("note", note);
                                hashMap.put("title", title);
                                hashMap.put("date", date);
                                hashMap.put("desc", desc);
                                skill_notes_Arraylist1.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterNotesDetails
                                    = new AdapterNotesDetails(NotesActivity.this, skill_notes_Arraylist1,from);
                            recyle_notes.setAdapter(adapterNotesDetails);



                        }else {


                            FancyToast.makeText(NotesActivity.this, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(NotesActivity.this, "Connection error",
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

                params.put("player_id",player_id_notes);
              //  params.put("skill_name",skill_name);
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}