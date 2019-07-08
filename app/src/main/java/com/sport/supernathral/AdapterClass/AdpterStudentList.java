package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesActivity;
import com.sport.supernathral.activity.PlayerInfo;
import com.sport.supernathral.activity.ScheduleActvity;
import com.sport.supernathral.activity.SkillActivity;
import com.sport.supernathral.activity.SponsorActivity;
import com.sport.supernathral.activity.StatisticsActivity;

import java.util.ArrayList;

public class AdpterStudentList extends
        RecyclerView.Adapter<AdpterStudentList.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;
    private String from;


    AdpterStudentList.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdpterStudentList.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdpterStudentList.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdpterStudentList(Context context, ArrayList<String> itemList,String from){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;



    }

    @Override
    public AdpterStudentList.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_student_list_adapter, parent,false);
        AdpterStudentList.ItemViewHolder dvh = new AdpterStudentList.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdpterStudentList.ItemViewHolder holder, final int position) {

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(from.equals("Notes")) {
                 Intent intent = new Intent(context, NotesActivity.class);
                 context.startActivity(intent);
             }
             else if(from.equals("Sponsor")){
                 Intent intent = new Intent(context, SponsorActivity.class);
                 context.startActivity(intent);

             }
             else if(from.equals("Statistics")){
                 Intent intent = new Intent(context, StatisticsActivity.class);
                 context.startActivity(intent);


             }

             else if(from.equals("Info")){
                 Intent intent = new Intent(context, PlayerInfo.class);
                 context.startActivity(intent);


             }
             else if(from.equals("Student List")){
                 Intent intent = new Intent(context, SkillActivity.class);
                 context.startActivity(intent);


             }
             else if(from.equals("Schedule")){
                 Intent intent = new Intent(context, ScheduleActvity.class);
                 context.startActivity(intent);


             }

         }
     });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}