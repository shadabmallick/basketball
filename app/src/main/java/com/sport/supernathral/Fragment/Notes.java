package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterAttendence;
import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.AdapterClass.AdapterNotes;
import com.sport.supernathral.AdapterClass.AdapterNotesDetails;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.NotesActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERNOTES;

public class Notes  extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        from=getArguments().getString("from");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {

        recyle_notes = view.findViewById(R.id.recyle_notes);

        //rv_category = view.findViewById(R.id.recycler_chat);
        skill_notes_Arraylist1 = new ArrayList<>();


        recyle_notes.setLayoutManager(new LinearLayoutManager(getActivity()));
       SkillNotesFromNotes();


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
                                    = new AdapterNotesDetails(getActivity(), skill_notes_Arraylist1,from);
                            recyle_notes.setAdapter(adapterNotesDetails);



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
