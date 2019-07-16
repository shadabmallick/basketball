package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GameListAdapter;
import com.sport.supernathral.AdapterClass.SponsorAdapter;
import com.sport.supernathral.DataModel.GameData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GameItem;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.HeaderItem;
import com.sport.supernathral.Utils.ListItem;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ChatScreen;
import com.sport.supernathral.activity.EventScreen;
import com.sport.supernathral.activity.GamesMain;
import com.sport.supernathral.activity.HomePage;
import com.sport.supernathral.activity.ProfileScreen;
import com.sport.supernathral.activity.Sponsor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sport.supernathral.NetworkConstant.AppConfig.GAMELIST;
import static com.sport.supernathral.NetworkConstant.AppConfig.SPONSOR;

public class Games extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    RecyclerView recycle_game;
    ImageView img_header;
    LinearLayout llnews;
    ArrayList<HashMap<String,String>> gameList;
    ArrayList<GameData> gameDataArrayList;
    LinearLayout llchat ;
    LinearLayout ll_games;
            LinearLayout ll_event;
    LinearLayout ll_profile;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        recycle_game = view.findViewById(R.id.recycle_game);
        recycle_game.setLayoutManager(new LinearLayoutManager(getActivity()));


        pd.setMessage("Loading...");
        initialisation(view);
        initFooterItems();



        return view;
    }

    private void initialisation(View view) {

        img_header = view.findViewById(R.id.img_header);
        recycle_game = view.findViewById(R.id.recycle_game);
        llnews = view.findViewById(R.id.llnews);
        llchat = view.findViewById(R.id.llchat);
        ll_games = view.findViewById(R.id.ll_games);
        ll_event =view. findViewById(R.id.ll_event);
        ll_profile =view. findViewById(R.id.ll_profile);
        gameList=new ArrayList<>();
         gameDataArrayList = new ArrayList<>();
        GameData gameData = new GameData();
        gameDataArrayList.add(gameData);
        gameDataArrayList.add(gameData);


        ArrayList<HashMap<String,ArrayList<GameData>>> listMain = new ArrayList<>();

        HashMap<String,ArrayList<GameData>> map = new HashMap<>();
        map.put("Foregoing", gameDataArrayList);
        listMain.add(map);

        map = new HashMap<>();
        map.put("Ongoing", gameDataArrayList);
        listMain.add(map);

        map = new HashMap<>();
        map.put("Upcoming", gameDataArrayList);
        listMain.add(map);


        ArrayList<ListItem> mItems = new ArrayList<>();

        for (HashMap<String,ArrayList<GameData>> map1 : listMain){

            for (String string : map1.keySet()) {

                HeaderItem header = new HeaderItem();
                header.setHeader(string);
                mItems.add(header);

                for (GameData gameData1 : map1.get(string)) {
                    GameItem item = new GameItem();
                    item.setGameData(gameData1);
                    mItems.add(item);
                }

            }

        }


        GameListAdapter gameListAdapter = new GameListAdapter(getActivity(),
                mItems);
        recycle_game.setAdapter(gameListAdapter);

    }
    private void initFooterItems(){




        llnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomePage.class));
            }
        });


        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ChatScreen.class));
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EventScreen.class));

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ProfileScreen.class));

            }
        });


    }
    private void GameList() {


        String tag_string_req = "game_list";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GAMELIST, new Response.Listener<String>() {

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
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String image = item.get("image").toString().replaceAll("\"", "");
                                String name = item.get("name").toString().replaceAll("\"", "");
                                String website = item.get("website").toString().replaceAll("\"", "");
                                String descerption = item.get("descerption").toString().replaceAll("\"", "");
                                String contact_name = item.get("contact_name").toString().replaceAll("\"", "");
                                String contact_designation = item.get("contact_designation").toString().replaceAll("\"", "");
                                String contact_email = item.get("contact_email").toString().replaceAll("\"", "");
                                String contact_phone = item.get("contact_phone").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("image", image);
                                hashMap.put("name", name);
                                hashMap.put("website", website);
                                hashMap.put("descerption", descerption);
                                hashMap.put("contact_name", contact_name);
                                hashMap.put("contact_designation", contact_designation);
                                hashMap.put("contact_email", contact_email);
                                hashMap.put("contact_phone", contact_phone);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);



                                //  globalClass.setFolderanme(folder_name);

                                gameList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                          /*  notificationListAdapter   = new SponsorAdapter(Sponsor.this,sponsor);
                            recyclerSponsor.setAdapter(notificationListAdapter);
                            notificationListAdapter.notifyDataSetChanged();*/



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


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }

}
