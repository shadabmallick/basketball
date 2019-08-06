package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.EventDetails;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AdapterSchedule extends RecyclerView.Adapter<AdapterSchedule.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;
    String from;



    AdapterSchedule.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_name,tv_date,tv_time;
        ImageView profile_image;
        RelativeLayout rl_next,complete,ongoing,upcoming;
        //  ImageView iv_track, iv_complain;
        AdapterSchedule.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterSchedule.onItemClickListner listner) {
            super(itemView);
            rl_next = itemView.findViewById(R.id.rl_next);
            tv_name = itemView.findViewById(R.id.name);
            tv_date = itemView.findViewById(R.id.date);
            tv_time = itemView.findViewById(R.id.time);






            this.listner = listner;
        }
    }


    public AdapterSchedule(Context context, ArrayList<HashMap<String,String>>itemList,String from){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;



    }

    @Override
    public AdapterSchedule.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_schedule, parent,false);
        AdapterSchedule.ItemViewHolder dvh = new AdapterSchedule.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterSchedule.ItemViewHolder holder, final int position) {

       String date=arrayList.get(position).get("event_start_date");


        holder.tv_name.setText(arrayList.get(position).get("event_name"));
        holder.tv_date.setText( getViewDateFormat(date));
        holder.tv_time.setText(getViewTime(date));





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static String getViewDateFormat(String date_s){
        String formattedDate = "";
        try {

            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date date = originalFormat.parse(date_s);

            formattedDate = targetFormat.format(date);  // 20-08-2018

        }catch (Exception e){
            e.printStackTrace();
        }

        return formattedDate;
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