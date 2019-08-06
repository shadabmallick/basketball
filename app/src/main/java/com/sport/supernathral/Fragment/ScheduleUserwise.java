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
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.Activity_Schedule;
import com.sport.supernathral.activity.StudentListActivity;
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
    TextView tv_own,tv_player;
    String sponsor,main_access_group_id,sub_access_group_id;

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
        main_access_group_id=getArguments().getString("main_access_group_id");
        sub_access_group_id=getArguments().getString("sub_access_group_id");
        Log.d(TAG, "initialisation: "+sponsor);
        img_header = view.findViewById(R.id.img_header);
        tv_player = view.findViewById(R.id.text_player);
        rl_own=view.findViewById(R.id.rl_own);
        rl_players=view.findViewById(R.id.rl_player);
        if(globalClass.getType().equals("Parent")){
            tv_player.setText("CHILDREN");
        }

        rl_own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent own=new Intent(getActivity(), Activity_Schedule.class);
               own.putExtra("main_access_group_id",main_access_group_id);
               own.putExtra("sub_access_group_id",sub_access_group_id);
               startActivity(own);
            }
        });


        rl_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(globalClass.getType().equals("Coach/Teachers")){
                    Intent team=new Intent(getActivity(), TeamActivity.class);
                    team.putExtra("from", sponsor);
                    startActivity(team);
                }
                else {
                    Intent attendance=new Intent(getActivity(), StudentListActivity.class);

                    attendance.putExtra("id",globalClass.getId());
                    attendance.putExtra("from", sponsor);
                    startActivity(attendance);
                }

            }
        });


    }
}
