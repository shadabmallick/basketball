package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.Activity_Schedule;
import com.sport.supernathral.activity.TeamActivity;

import java.util.ArrayList;

public class ScheduleUserwise extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    RelativeLayout rl_own,rl_players;
    ProgressDialog pd;
    String sponsor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_user_wise, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {
        sponsor = getArguments().getString("from");
        Log.d(TAG, "initialisation: "+sponsor);
        img_header = view.findViewById(R.id.img_header);
        rl_own=view.findViewById(R.id.rl_own);
        rl_players=view.findViewById(R.id.rl_player);

        rl_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent own=new Intent(getActivity(), Activity_Schedule.class);
               startActivity(own);
            }
        });


        rl_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent team=new Intent(getActivity(), TeamActivity.class);
                team.putExtra("from", sponsor);
               startActivity(team);
            }
        });


    }
}