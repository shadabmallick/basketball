package com.sport.supernathral.Fragment;
  /* Develop by Shadab on 28/06/2019*/
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.SkillActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSKILL;

public class SkillSet  extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    RecyclerView recycle_skill;
    AdapterSkill adapterEvent;
    TextView toolbar_title;
    ArrayList<HashMap<String,String>> player_ArrayList;
    ProgressDialog pd;
    String from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skill_set_frag, container, false);
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
        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.skill_set));
        img_header = view.findViewById(R.id.img_header);
        recycle_skill = view.findViewById(R.id.recycle_skill);

        //rv_category = view.findViewById(R.id.recycler_chat);
        player_ArrayList = new ArrayList<>();


        recycle_skill.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*adapterEvent
                = new AdapterSkill(getActivity(), newsList);
        recycle_skill.setAdapter(adapterEvent);*/
        SkillSet();

    }
    private void SkillSet() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                PLAYERSKILL, new Response.Listener<String>() {

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
                                String name = item.get("name").toString().replaceAll("\"", "");
                                String student_skill = item.get("student_skill").toString().replaceAll("\"", "");
                                String date = item.get("date").toString().replaceAll("\"", "");
                                String skill_value = item.get("skill_value").toString().replaceAll("\"", "");


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);
                                hashMap.put("student_skill", student_skill);
                                hashMap.put("name", name);
                                hashMap.put("date", date);
                                hashMap.put("skill_value", skill_value);

                                player_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterEvent
                                    = new AdapterSkill(getActivity(), player_ArrayList,globalClass.getId(),from);
                            recycle_skill.setAdapter(adapterEvent);
                            //adpterStudentList.notifyDataSetChanged();


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
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
