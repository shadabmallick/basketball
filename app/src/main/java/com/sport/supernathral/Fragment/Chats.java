package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ChatSingle;
import com.sport.supernathral.activity.GroupUserSelection;
import com.sport.supernathral.activity.LoginScreen;
import com.sport.supernathral.activity.SignUp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.REGISTER;

public class Chats extends Fragment implements AdapterChat.onItemClickListner{

    RecyclerView recycler_chat;
    RelativeLayout rel_create_group;

    String TAG = "chat user list";

    ArrayList<ChatListData> chatListDataArrayList;
    ProgressDialog pd;

    Shared_Preference preference;
    GlobalClass globalClass;
    AdapterChat adapterChat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chats, container, false);

        initialisation(view);



        return view;
    }

    private void initialisation(View view) {


        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getActivity().getResources().getString(R.string.loading));

        recycler_chat = view.findViewById(R.id.recycler_chat);
        rel_create_group = view.findViewById(R.id.rel_create_group);

        recycler_chat.setLayoutManager(new LinearLayoutManager(getActivity()));

        preference = new Shared_Preference(getActivity());
        globalClass = (GlobalClass) getActivity().getApplicationContext();

        getChatUserList(globalClass.getId());


        rel_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), GroupUserSelection.class);
                startActivity(intent);

            }
        });

    }


    private void getChatUserList(final String user_id) {

        chatListDataArrayList = new ArrayList<>();

        String tag_string_req = "CHAT_LIST";

        String url = AppConfig.CHAT_USER_LIST;

        pd.show();

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
                            chatListData.setDatetime(object.optString("datetime"));
                            chatListData.setChat_type(object.optString("chat_type"));

                            chatListDataArrayList.add(chatListData);

                        }


                    }

                    adapterChat = new AdapterChat(getActivity(),
                            chatListDataArrayList, Chats.this);
                    recycler_chat.setAdapter(adapterChat);

                    pd.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(getActivity(), "Data Connection", FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    e.printStackTrace();

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

        Intent intent = new Intent(getActivity(), ChatSingle.class);
        intent.putExtra("info", chatListData);
        startActivity(intent);

    }
}