package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterSkill extends RecyclerView.Adapter<AdapterSkill.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;

    String player_id,from;

    AdapterSkill.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,tv_skill_set,tv_progress_percent;
        ProgressBar p_bar;
        ImageView img_profile;

        //  ImageView iv_track, iv_complain;
        AdapterSkill.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterSkill.onItemClickListner listner) {
            super(itemView);
            img_profile=itemView.findViewById(R.id.profile_image);
            tv_skill_set=itemView.findViewById(R.id.tv_skill_set);
            p_bar=itemView.findViewById(R.id.ProgressBar);
            tv_progress_percent=itemView.findViewById(R.id.tv_progress_percent);



            this.listner = listner;
        }
    }


    public AdapterSkill(Context context,ArrayList<HashMap<String,String>>itemList ,String player_id,String from){

        this.context = context;
        this.arrayList=itemList;
        this.player_id=player_id;
        this.from=from;



    }

    @Override
    public AdapterSkill.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_skill, parent,false);
        AdapterSkill.ItemViewHolder dvh = new AdapterSkill.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterSkill.ItemViewHolder holder, final int position) {


        holder.tv_skill_set.setText("Skill Set"+"-"+" "+arrayList.get(position).get("name"));
         holder.p_bar.setProgress(Integer.parseInt(arrayList.get(position).get("student_skill")));
         holder.tv_progress_percent.setText(arrayList.get(position).get("student_skill")+"%");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                Intent notes=new Intent(context, NotesActivity.class);

              notes.putExtra("player_id",player_id);
              notes.putExtra("from",from);
              notes.putExtra("skill_name",arrayList.get(position).get("name"));
               context.startActivity(notes);
          }
      });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}