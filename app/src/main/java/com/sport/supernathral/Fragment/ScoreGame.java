package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.ScoreData;
import com.sport.supernathral.DataModel.SubCommentData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.sport.supernathral.NetworkConstant.AppConfig.GAMEDETAILS;
import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS_COMMENT;

public class ScoreGame extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ImageView img_first,img_second,team1_img,team2_img;
    TextView tv_team1,tv_team2,tv_point1,tv_point2,tv_type;
    ImageView img_header;
    ProgressDialog pd;
    TextView tv_Afinal,tv_scoreA1,tv_scoreA2,tv_scoreA3,tv_scoreA4,tv_scoreB1,tv_scoreB2
            ,tv_scoreB3,tv_scoreB4,tv_Bfinal;
    String id,team,team_1_name,team_2_name,team_1_image,team_2_image,live_score_team_A,live_score_team_B,match_type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_game, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        /* Accessing date from GameListActivity   */

       // id=getArguments().getString("id");
        team=getArguments().getString("game_id");
        team_1_name=getArguments().getString("team_1_name");
        team_2_name=getArguments().getString("team_2_name");
        team_1_image=getArguments().getString("team_1_image");
        team_2_image=getArguments().getString("team_2_image");
        live_score_team_A=getArguments().getString("live_score_team_A");
        live_score_team_B=getArguments().getString("live_score_team_B");
        match_type=getArguments().getString("match_type");
        Log.d(TAG, "onCreateView: "+team);

        initialisation(view);
        GameDetails();


        return view;
    }

    private void initialisation(View view) {

        img_header = view.findViewById(R.id.img_header);
        img_first = view.findViewById(R.id.img_team1);
        img_second = view.findViewById(R.id.img_team2);
        tv_point1 = view.findViewById(R.id.tv_point1);
        tv_point2 = view.findViewById(R.id.tv_point2);
        tv_team1 = view.findViewById(R.id.tv_team1);
        tv_team2 = view.findViewById(R.id.tv_team2);
        tv_type = view.findViewById(R.id.tv_type);
        team1_img = view.findViewById(R.id.team1_img);
        team2_img = view.findViewById(R.id.team2_img);
        tv_scoreA1 = view.findViewById(R.id.tv_scoreA1);
        tv_scoreA2 = view.findViewById(R.id.tv_scoreA2);
        tv_scoreA3 = view.findViewById(R.id.tv_scoreA3);
        tv_scoreA4 = view.findViewById(R.id.tv_scoreA4);
        tv_scoreB1 = view.findViewById(R.id.tv_scoreB1);
        tv_scoreB1 = view.findViewById(R.id.tv_scoreB1);
        tv_scoreB2 = view.findViewById(R.id.tv_scoreB2);
        tv_scoreB3 = view.findViewById(R.id.tv_scoreB3);
        tv_scoreB4 = view.findViewById(R.id.tv_scoreB4);
        tv_Bfinal = view.findViewById(R.id.tv_Bfinal);
        tv_Afinal = view.findViewById(R.id.tv_final);

        if(!team_1_image.isEmpty()){
            Picasso.with(getActivity()).load(team_1_image).into(img_first);
            Picasso.with(getActivity()).load(team_1_image).into(team1_img);

        }
        if(!team_2_image.isEmpty()){
            Picasso.with(getActivity()).load(team_2_image).into(img_second);
            Picasso.with(getActivity()).load(team_2_image).into(team2_img);
        }
        tv_type.setText(match_type);

        tv_team1.setText(team_1_name);
        tv_team2.setText(team_2_name);



    }



    private void GameDetails() {
        String tag_string_req = "GameList";


        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                GAMEDETAILS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "comment: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);

                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){
                                    /* Data Json Object  */
                            JSONObject news_data = main_object.getJSONObject("data");
                            String id = news_data.get("id").toString().replaceAll("\"", "");
                            String match_id = news_data.get("match_id").toString().replaceAll("\"", "");
                            String teamA = news_data.get("teamA").toString().replaceAll("\"", "");
                            String teamB = news_data.get("teamB").toString().replaceAll("\"", "");
                            String date_and_time = news_data.get("date_and_time").toString().replaceAll("\"", "");
                                 /* Match Json Object */
                            JSONObject match = news_data.getJSONObject("match");
                            String match_inner_id = match.get("id").toString().replaceAll("\"", "");
                            String image = match.get("image").toString().replaceAll("\"", "");
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

                              /*Score json Object*/
                            JSONObject score = news_data.getJSONObject("score");
                            JSONObject subObject = score.getJSONObject("final_score");
                            String team_A = subObject.get("team_A").toString().replaceAll("\"", "");
                            String team_B = subObject.get("team_B").toString().replaceAll("\"", "");
                            tv_point1.setText(team_A+"  "+":");
                            tv_point2.setText(team_B);
                            /*   Score json Array */


                            ArrayList<ScoreData> scoreDataArrayList = new ArrayList<>();

                            JSONArray score_array=score.getJSONArray("score");
                            if(score_array.length() > 0){

                                for (int i = 0; i < score_array.length(); i++) {
                                    JSONObject item = score_array.getJSONObject(i);
                                    String score_id = item.get("id").toString().replaceAll("\"", "");
                                    String score_match_id = item.get("match_id").toString().replaceAll("\"", "");
                                    String match_team_id = item.get("match_team_id").toString().replaceAll("\"", "");
                                    String teamA_score = item.get("teamA_score").toString().replaceAll("\"", "");
                                    String teamB_score = item.get("teamB_score").toString().replaceAll("\"", "");
                                    String score_date_and_time = item.get("date_and_time").toString().replaceAll("\"", "");
                                    String winner_team = item.get("winner_team").toString().replaceAll("\"", "");
                                    String score_delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                    String score_is_active = item.get("is_active").toString().replaceAll("\"", "");
                                    String score_entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                    String score_modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                    Log.d(TAG, "onResponse: "+teamA_score);
                                    ScoreData scoreData = new ScoreData();
                                    scoreData.setId(score_id);
                                    scoreData.setMatch_id(score_match_id);
                                    scoreData.setMatch_team_id(match_team_id);
                                    scoreData.setTeamA_score(teamA_score);
                                    scoreData.setTeamB_score(teamB_score);
                                    scoreData.setDate_and_time(score_date_and_time);
                                    scoreData.setWinner_team(winner_team);
                                    scoreData.setDelete_flag(score_delete_flag);
                                    scoreData.setIs_active(score_is_active);
                                    scoreData.setModified_date(score_modified_date);
                                    scoreData.setEntry_date(score_entry_date);


                                    scoreDataArrayList.add(scoreData);

                                    Log.d(TAG, "DATA ARRAY: "+scoreData);


                                }

                                setScoreData(scoreDataArrayList);
                            }



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

                params.put("game_id", team);
                //params.put("news_id", "7");

                Log.d(TAG, "get comment: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private void setScoreData(ArrayList<ScoreData> scoreDataArrayList){

        int final_score_A = 0;
        int final_score_B = 0;

        for (int i = 0; i < scoreDataArrayList.size(); i++){
            ScoreData scoreData = scoreDataArrayList.get(i);

            final_score_A = final_score_A + Integer.parseInt(scoreData.getTeamA_score());
            final_score_B = final_score_B + Integer.parseInt(scoreData.getTeamB_score());

            switch (i){

                case 0:
                    if(!scoreData.getTeamA_score().isEmpty()) {
                        tv_scoreA1.setText(scoreData.getTeamA_score());
                        if(!scoreData.getTeamB_score().isEmpty()){
                            tv_scoreB1.setText(scoreData.getTeamB_score());
                        }
                        else {
                            tv_scoreB1.setText("");
                        }

                        Log.d(TAG, "A = " + scoreData.getTeamA_score());
                        Log.d(TAG, "B = " + scoreData.getTeamB_score());
                    }
                    else {
                        tv_scoreA1.setText("");

                    }
                    break;


                case 1:

                    if(!scoreData.getTeamA_score().isEmpty()) {
                        tv_scoreA2.setText(scoreData.getTeamA_score());
                        if(!scoreData.getTeamB_score().isEmpty()){
                            tv_scoreB2.setText(scoreData.getTeamB_score());
                        }
                        else {
                            tv_scoreB2.setText("");
                        }

                        Log.d(TAG, "A = " + scoreData.getTeamA_score());
                        Log.d(TAG, "B = " + scoreData.getTeamB_score());
                    }
                    else {
                        tv_scoreA2.setText("");

                    }

                    break;

                case 2:
                    if(!scoreData.getTeamA_score().isEmpty()) {
                        tv_scoreA3.setText(scoreData.getTeamA_score());
                        if(!scoreData.getTeamB_score().isEmpty()){
                            tv_scoreB3.setText(scoreData.getTeamB_score());
                        }
                        else {
                            tv_scoreB3.setText("");
                        }

                        Log.d(TAG, "A = " + scoreData.getTeamA_score());
                        Log.d(TAG, "B = " + scoreData.getTeamB_score());
                    }
                    else {
                        tv_scoreA3.setText("");

                    }


                    break;

                case 3:
                    if(!scoreData.getTeamA_score().isEmpty()) {
                        tv_scoreA4.setText(scoreData.getTeamA_score());
                        if(!scoreData.getTeamB_score().isEmpty()){
                            tv_scoreB4.setText(scoreData.getTeamB_score());
                        }
                        else {
                            tv_scoreB4.setText("");
                        }

                        Log.d(TAG, "A = " + scoreData.getTeamA_score());
                        Log.d(TAG, "B = " + scoreData.getTeamB_score());
                    }
                    else {
                        tv_scoreA4.setText("");

                    }


                    break;


            }



        }


        /// final ..

        Log.d(TAG, "setScoreData: "+final_score_A);
        tv_Afinal.setText(String.valueOf(final_score_A));
        tv_Bfinal.setText(String.valueOf(final_score_B));

    }


}
