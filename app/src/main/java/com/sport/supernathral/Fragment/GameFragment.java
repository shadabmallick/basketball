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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GameAdapterNew;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ChatScreen;
import com.sport.supernathral.activity.EventScreen;
import com.sport.supernathral.activity.HomePage;
import com.sport.supernathral.activity.ProfileScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sport.supernathral.NetworkConstant.AppConfig.GAMELIST;

public class GameFragment extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    RecyclerView recycle_game,recycle_game1,recycle_game2;
    ImageView img_header;
    LinearLayout llnews;
    ArrayList<HashMap<String,String>> gameList;
    GameAdapterNew gameListAdapter;
    LinearLayout llchat ;
    LinearLayout ll_games;
    LinearLayout ll_event;
    LinearLayout ll_profile;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        recycle_game = view.findViewById(R.id.recycle_game);
        recycle_game1 = view.findViewById(R.id.recycle_game1);
        recycle_game2 = view.findViewById(R.id.recycle_game2);
        recycle_game.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle_game1.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycle_game2.setLayoutManager(new LinearLayoutManager(getActivity()));


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
        GameList();

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




                            JSONArray news_data=main_object.getJSONArray("past_match");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String match_id = item.get("match_id").toString().replaceAll("\"", "");
                                String teamA = item.get("teamA").toString().replaceAll("\"", "");
                                String teamB = item.get("teamB").toString().replaceAll("\"", "");
                                String date_and_time = item.get("date_and_time").toString().replaceAll("\"", "");
                                String day = item.get("day").toString().replaceAll("\"", "");
                                JSONObject match=item.getJSONObject("match");
                                String match_sub_id = match.get("id").toString().replaceAll("\"", "");
                                String image = match.get("image").toString().replaceAll("\"", "");
                                String name = match.get("name").toString().replaceAll("\"", "");
                                String match_start_date = match.get("match_start_date").toString().replaceAll("\"", "");
                                String match_end_date = match.get("match_end_date").toString().replaceAll("\"", "");
                                String match_venue = match.get("match_venue").toString().replaceAll("\"", "");
                                String match_address = match.get("match_address").toString().replaceAll("\"", "");
                                String match_city = match.get("match_city").toString().replaceAll("\"", "");
                                String match_state = match.get("match_state").toString().replaceAll("\"", "");
                                String match_pincode = match.get("match_pincode").toString().replaceAll("\"", "");
                                String match_desc = match.get("match_desc").toString().replaceAll("\"", "");
                                String match_contact_name = match.get("match_contact_name").toString().replaceAll("\"", "");
                                String match_contact_designation = match.get("match_contact_designation").toString().replaceAll("\"", "");
                                String match_contact_email = match.get("match_contact_email").toString().replaceAll("\"", "");
                                String match_contact_phone = match.get("match_contact_phone").toString().replaceAll("\"", "");
                                String type = match.get("type").toString().replaceAll("\"", "");
                                String delete_flag = match.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = match.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = match.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = match.get("modified_date").toString().replaceAll("\"", "");
                                String match_image = match.get("match_image").toString().replaceAll("\"", "");
                                String match_type = match.get("match_type").toString().replaceAll("\"", "");
                                String arr[] = date_and_time.split(" ", 2);
                                String firstWord = arr[0];   //the
                                String lastWord = match_start_date.substring(match_start_date.lastIndexOf(" ")+1);
                                JSONObject teamA_details=item.getJSONObject("teamA_details");
                                String team_id = teamA_details.get("id").toString().replaceAll("\"", "");
                                String team1_name = teamA_details.get("name").toString().replaceAll("\"", "");
                                String team_image = teamA_details.get("image").toString().replaceAll("\"", "");
                                JSONObject teamB_details=item.getJSONObject("teamB_details");

                                String team2_image = teamB_details.get("image").toString().replaceAll("\"", "");
                                String team2_id = teamB_details.get("id").toString().replaceAll("\"", "");
                                String team2_name = teamB_details.get("name").toString().replaceAll("\"", "");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("match_id", match_id);
                                hashMap.put("team_id", team_id);
                                hashMap.put("date", firstWord);
                                hashMap.put("time", lastWord);
                                hashMap.put("day", day);
                                hashMap.put("team1_name", team1_name);
                                hashMap.put("team2_name", team2_name);
                                hashMap.put("team_image", team_image);
                                hashMap.put("team2_image", team2_image);
                                hashMap.put("team2_id", team2_id);






                                //  globalClass.setFolderanme(folder_name);

                                gameList.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            gameListAdapter
                                    = new GameAdapterNew(getActivity(), gameList);
                            recycle_game.setAdapter(gameListAdapter);
                            gameListAdapter.notifyDataSetChanged();



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
