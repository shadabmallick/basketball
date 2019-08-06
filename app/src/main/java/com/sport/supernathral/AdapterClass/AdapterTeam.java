package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.Fragment.StudentList;
import com.sport.supernathral.R;
import com.sport.supernathral.activity.AttendanceActivity;
import com.sport.supernathral.activity.StudentListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class AdapterTeam extends
        RecyclerView.Adapter<AdapterTeam.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;
    private String from,datechange;


    AdapterTeam.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_name,tv_time;
        //  ImageView iv_track, iv_complain;
        AdapterTeam.onItemClickListner listner;
        RelativeLayout rl_main;

        public ItemViewHolder(View itemView, AdapterTeam.onItemClickListner listner) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time=itemView.findViewById(R.id.tv_time);
            rl_main=itemView.findViewById(R.id.rl_main);


            this.listner = listner;
        }
    }


    public AdapterTeam(Context context,ArrayList<HashMap<String,String>>itemList,String from,String datechange){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;
        this.datechange=datechange;



    }

    @Override
    public AdapterTeam.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_team, parent,false);
        AdapterTeam.ItemViewHolder dvh = new AdapterTeam.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterTeam.ItemViewHolder holder, final int position) {
        holder.tv_time.setVisibility(View.VISIBLE);
        String time=arrayList.get(position).get("event_start_date");
        holder.tv_time.setText(getViewTime(time));
        holder.tv_name.setText(arrayList.get(position).get("team_name"));
        Log.d(TAG, "onBindViewHolder: "+datechange);
        if(datechange.equals("after")){
            holder.rl_main.setClickable(false);

        }
        else {

            holder.rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent attendance = new Intent(context, AttendanceActivity.class);
                    attendance.putExtra("schedule_id", arrayList.get(position).get("id"));
                    attendance.putExtra("datechange", datechange);
                    attendance.putExtra("team_id", arrayList.get(position).get("team_id"));
                    //   attendance.putExtra("from",from);
                    context.startActivity(attendance);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static String getViewTime(String date_s){
        String formattedDate = "";
        try {

            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            Date date = originalFormat.parse(date_s);

            formattedDate = targetFormat.format(date);  // 20-08-2018

        }catch (Exception e){
            e.printStackTrace();
        }

        return formattedDate;
    }

}