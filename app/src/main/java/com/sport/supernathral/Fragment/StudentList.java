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
import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.AdapterClass.StudentAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.StudentListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.STUDENTLIST;

public class StudentList extends Fragment {


    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    Toolbar toolbar;
    AdpterStudentList adpterStudentList;
    ArrayList<HashMap<String,String>> student_ArrayList;
    ArrayList<String> newsList;
    RecyclerView recycle_student_list;
    String from,id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_list, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        from =  getArguments().getString("from");
        Log.d(TAG, "onCreateView: "+from);
        id =  getArguments().getString("id");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {


        recycle_student_list = view.findViewById(R.id.recycle_student_list);

        //rv_category = view.findViewById(R.id.recycler_chat);
        student_ArrayList = new ArrayList<>();


        recycle_student_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        StudentList();

    }
    private void StudentList() {


        String tag_string_req = "Coach";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                STUDENTLIST, new Response.Listener<String>() {

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
                                String player_id = item.get("player_id").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                String user_image = item.get("user_image").toString().replaceAll("\"", "");
                                if(player_id.equals(null)){

                                }
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);
                                if(user_name.equals(null)){
                                    hashMap.put("user_name", "demo");
                                }
                                else {
                                    hashMap.put("user_name", user_name);
                                }

                                hashMap.put("user_image", user_image);
                                if(player_id.equals(null)){
                                    hashMap.put("player_id", "demo");
                                }
                                else {
                                    hashMap.put("player_id", player_id);
                                }

                                hashMap.put("is_active", is_active);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);

                                student_ArrayList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adpterStudentList
                                    = new AdpterStudentList(getActivity(), student_ArrayList,from);
                            recycle_student_list.setAdapter(adpterStudentList);
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

                params.put("id",globalClass.getId());

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
