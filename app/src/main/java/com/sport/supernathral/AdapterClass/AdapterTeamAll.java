package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.StudentListActivity;

import java.util.ArrayList;

public class AdapterTeamAll extends
        RecyclerView.Adapter<AdapterTeamAll.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;
    private String from;


    AdapterTeamAll.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterTeamAll.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterTeamAll.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterTeamAll(Context context, ArrayList<String> itemList,String from){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;



    }

    @Override
    public AdapterTeamAll.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_team, parent,false);
        AdapterTeamAll.ItemViewHolder dvh = new AdapterTeamAll.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterTeamAll.ItemViewHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attendance=new Intent(context, StudentListActivity.class);
                attendance.putExtra("from", from);
                context.startActivity(attendance);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}