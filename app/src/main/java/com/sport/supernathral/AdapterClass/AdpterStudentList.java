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

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.activity.AttendanceActivity;
import com.sport.supernathral.activity.AttendenceParent;
import com.sport.supernathral.activity.AttendenceStudentList;
import com.sport.supernathral.activity.InfoActivity;
import com.sport.supernathral.activity.NotesActivity;
import com.sport.supernathral.activity.PlayerInfo;
import com.sport.supernathral.activity.ScheduleActvity;
import com.sport.supernathral.activity.SkillActivity;
import com.sport.supernathral.activity.SponsorActivity;
import com.sport.supernathral.activity.StatisticsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class AdpterStudentList extends
        RecyclerView.Adapter<AdpterStudentList.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;
    private String from;
    GlobalClass globalClass;


    AdpterStudentList.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_name;
        CircleImageView profile_image;
        RelativeLayout rel_main;
        //  ImageView iv_track, iv_complain;
        AdpterStudentList.onItemClickListner listner;

        public ItemViewHolder(View itemView,
                              AdpterStudentList.onItemClickListner listner) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.tv_name);
            profile_image=itemView.findViewById(R.id.profile_image);
            rel_main=itemView.findViewById(R.id.rel_main);

            this.listner = listner;
        }
    }


    public AdpterStudentList(Context context,
                             ArrayList<HashMap<String,String>>itemList,
                             String from){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;
        globalClass=(GlobalClass)context.getApplicationContext();

        Log.d(TAG, "from: "+from);

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


        holder.tv_name.setText(arrayList.get(position).get("user_name"));
        if (!arrayList.get(position).get("user_image").isEmpty()){
            Picasso.with(context)
                    .load(arrayList.get(position).get("user_image"))
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }



        holder.rel_main.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(from.equals("Notes")) {
                 Log.d(TAG, "Notes: "+position);
                 Intent intent = new Intent(context, NotesActivity.class);
                 intent.putExtra("from",from);
                 intent.putExtra("player_id_notes",arrayList.get(position).get("player_id"));

                 context.startActivity(intent);
             }
             else if(from.equals("Sponsor")){
                 Intent intent = new Intent(context, SponsorActivity.class);
                 intent.putExtra("from",from);
                 intent.putExtra("image",arrayList.get(position).get("user_image"));
                 intent.putExtra("name",arrayList.get(position).get("user_name"));
                 context.startActivity(intent);

             }
             else if(from.equals("Statistics")){
                 Intent intent = new Intent(context, StatisticsActivity.class);
                 intent.putExtra("player_id",arrayList.get(position).get("player_id"));

                 context.startActivity(intent);


             }

             else if(from.equals("Info")){
                 Intent intent = new Intent(context, InfoActivity.class);
                 intent.putExtra("from",from);
                 intent.putExtra("player_id",arrayList.get(position).get("player_id"));
                 context.startActivity(intent);


             }
             else if(from.equals("Student List")){
                 Intent intent = new Intent(context, SkillActivity.class);
                 intent.putExtra("from",from);
                 intent.putExtra("player_id",arrayList.get(position).get("player_id"));
                 context.startActivity(intent);


             }
             else if(from.equals("Schedule")){
                 Intent intent = new Intent(context, ScheduleActvity.class);
                 intent.putExtra("team_id",arrayList.get(position).get("team_id"));
                 intent.putExtra("main_access_group_id",arrayList.get(position).get("main_access_group_id"));
                 intent.putExtra("sub_access_group_id",arrayList.get(position).get("sub_access_group_id"));

                 context.startActivity(intent);


             }
             else if(from.equals("Attendance")){
                 Intent intent = new Intent(context, AttendenceParent.class);
                 intent.putExtra("player_id",arrayList.get(position).get("player_id"));

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