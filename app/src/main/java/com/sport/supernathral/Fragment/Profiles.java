package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.AdapterClass.ProfileAdapter;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profiles extends Fragment implements ProfileAdapter.onItemClickListner {

    RecyclerView recycler_profile;

    String TAG="product";

    ArrayList<ChatListData> chatListDataArrayList;
    ProfileAdapter profileAdapter;

    Shared_Preference preference;
    GlobalClass globalClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profiles, container, false);

        initialisation(view);




        return view;
    }

    private void initialisation(View view) {

        preference = new Shared_Preference(getActivity());
        globalClass = (GlobalClass) getActivity().getApplicationContext();

        recycler_profile = view.findViewById(R.id.recycler_profile);
        recycler_profile.setLayoutManager(new LinearLayoutManager(getActivity()));


        getChatList(globalClass.getId());

    }


    private void getChatList(final String user_id) {
        // Tag used to cancel the request

        chatListDataArrayList = new ArrayList<>();

        String tag_string_req = "CHAT_LIST";

        String url = AppConfig.CHAT_USER_LIST;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){
                        JSONArray data = main_object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++){
                            JSONObject object = data.getJSONObject(i);

                            ChatListData chatListData = new ChatListData();
                            chatListData.setReceiver_id(object.optString("receiver_id"));
                            chatListData.setUser_name(object.optString("user_name"));
                            chatListData.setUser_image(object.optString("user_image"));
                            chatListData.setUser_type(object.optString("user_type"));
                            chatListData.setMessage(object.optString("message"));
                            chatListData.setMessage_type(object.optString("message_type"));
                            chatListData.setReceiver_id(object.optString("datetime"));
                            chatListData.setReceiver_id(object.optString("chat_type"));

                            chatListDataArrayList.add(chatListData);

                        }


                    }

                    profileAdapter = new ProfileAdapter(getActivity(),
                            chatListDataArrayList, Profiles.this);
                    recycler_profile.setAdapter(profileAdapter);


                } catch (Exception e) {

                    FancyToast.makeText(getActivity(), "Data Connection", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public void onItemClick(ChatListData chatListData) {

        if (chatListData.getChat_type().equals("User")){

        }else {

        }

    }
}