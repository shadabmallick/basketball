package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.AdapterClass.AdapterMoments;
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

import static com.sport.supernathral.NetworkConstant.AppConfig.EVENTLIST;
import static com.sport.supernathral.NetworkConstant.AppConfig.PLAYERSKILLNOTES;


public class Event extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    RecyclerView recycle_event;
    AdapterEvent adapterEvent;
    ArrayList<HashMap<String,String>> event_array;
    String main_access_group_id,sub_access_group_id;

    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        main_access_group_id=getArguments().getString("main_access_group_id");
        sub_access_group_id=getArguments().getString("sub_access_group_id");
        pd.setMessage("Loading...");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {

        img_header = view.findViewById(R.id.img_header);
        recycle_event = view.findViewById(R.id.recycle_event);

        event_array = new ArrayList<>();

        recycle_event.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventList();


    }
    private void EventList() {


        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                EVENTLIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "EventList Response: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){

                            JSONArray news_data = main_object.getJSONArray("all_event");

                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String main_access_group_id = item.get("main_access_group_id").toString().replaceAll("\"", "");
                                String sub_access_group_id = item.get("sub_access_group_id").toString().replaceAll("\"", "");
                                String image = item.get("image").toString().replaceAll("\"", "");
                                String name = item.get("name").toString().replaceAll("\"", "");
                                String event_start_date = item.get("event_start_date").toString().replaceAll("\"", "");
                                String event_end_date = item.get("event_end_date").toString().replaceAll("\"", "");
                                String event_venue = item.get("event_venue").toString().replaceAll("\"", "");
                                String event_address = item.get("event_address").toString().replaceAll("\"", "");
                                String event_city = item.get("event_city").toString().replaceAll("\"", "");
                                String event_state = item.get("event_state").toString().replaceAll("\"", "");
                                String event_pincode = item.get("event_pincode").toString().replaceAll("\"", "");
                                String event_desc = item.get("event_desc").toString().replaceAll("\"", "");
                                String event_contact_name = item.get("event_contact_name").toString().replaceAll("\"", "");
                                String event_contact_designation = item.get("event_contact_designation").toString().replaceAll("\"", "");
                                String event_contact_email = item.get("event_contact_email").toString().replaceAll("\"", "");
                                String event_contact_phone = item.get("event_contact_phone").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String eventType = item.get("eventType").toString().replaceAll("\"", "");
                                String event_venue_chinese = item.optString("event_venue_chinese");
                                String event_address_chinese = item.optString("event_address_chinese");
                                String event_city_chinese = item.optString("event_city_chinese");
                                String event_state_chinese = item.optString("event_state_chinese");
                                String event_pincode_chinese = item.optString("event_pincode_chinese");



                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("main_access_group_id", main_access_group_id);
                                hashMap.put("sub_access_group_id", sub_access_group_id);
                                hashMap.put("image", image);
                                hashMap.put("name", name);
                                hashMap.put("event_start_date", event_start_date);
                                hashMap.put("event_end_date", event_end_date);
                                hashMap.put("event_venue", event_venue);
                                hashMap.put("event_address", event_address);
                                hashMap.put("event_city", event_city);
                                hashMap.put("event_state", event_state);
                                hashMap.put("event_pincode", event_pincode);
                                hashMap.put("event_desc", event_desc);
                                hashMap.put("event_contact_name", event_contact_name);
                                hashMap.put("event_contact_designation", event_contact_designation);
                                hashMap.put("event_contact_email", event_contact_email);
                                hashMap.put("event_contact_phone", event_contact_phone);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("eventType", eventType);

                                hashMap.put("event_venue_chinese", event_venue_chinese);
                                hashMap.put("event_address_chinese", event_address_chinese);
                                hashMap.put("event_city_chinese", event_city_chinese);
                                hashMap.put("event_state_chinese", event_state_chinese);
                                hashMap.put("event_pincode_chinese", event_pincode_chinese);

                                event_array.add(hashMap);

                              //  Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterEvent = new AdapterEvent(getActivity(), event_array);
                            recycle_event.setAdapter(adapterEvent);

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

                params.put("main_access_group_id",globalClass.getMain_access_group_id());
                params.put("sub_access_group_id",globalClass.getSub_access_group_id());
                Log.d(TAG, "params: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
