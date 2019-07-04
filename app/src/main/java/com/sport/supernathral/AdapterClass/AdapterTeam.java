package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.AttendanceActivity;

import java.util.ArrayList;

public class AdapterTeam extends
        RecyclerView.Adapter<AdapterTeam.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterTeam.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterTeam.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterTeam.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterTeam(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



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


         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent attendance=new Intent(context, AttendanceActivity.class);
                 context.startActivity(attendance);
             }
         });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}