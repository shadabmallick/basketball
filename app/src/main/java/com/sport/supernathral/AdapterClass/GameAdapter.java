package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sport.supernathral.R;
import com.sport.supernathral.activity.GameListActivity;
import com.sport.supernathral.activity.NewsSublist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    GameAdapter.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_point1,tv_point2,tv_match_type,tv_team1,tv_team2;
        ImageView img_team1,img_team2;
        //  ImageView iv_track, iv_complain;
        GameAdapter.onItemClickListner listner;

        public ItemViewHolder(View itemView, GameAdapter.onItemClickListner listner) {
            super(itemView);
            tv_match_type=itemView.findViewById(R.id.match_type);
            tv_point1=itemView.findViewById(R.id.tv_point1);
            tv_point2=itemView.findViewById(R.id.tv_point2);
            tv_team1=itemView.findViewById(R.id.tv_team1);
            tv_team2=itemView.findViewById(R.id.tv_team2);
            img_team1=itemView.findViewById(R.id.img_team1);
            img_team2=itemView.findViewById(R.id.img_team2);


            this.listner = listner;
        }
    }


    public GameAdapter(Context context, ArrayList<HashMap<String,String>>itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public GameAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_game, parent,false);
        GameAdapter.ItemViewHolder dvh = new GameAdapter.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(GameAdapter.ItemViewHolder holder, final int position) {


        if (!arrayList.get(position).get("team_1_image").isEmpty()) {
            Picasso.with(context).load(arrayList.get(position).get("team_1_image")).into(holder.img_team1);
        }

        if (!arrayList.get(position).get("team_2_image").isEmpty()) {
            Picasso.with(context).load(arrayList.get(position).get("team_2_image")).into(holder.img_team2);
        }

        holder.tv_team1.setText(arrayList.get(position).get("team_1_name"));
        holder.tv_team2.setText(arrayList.get(position).get("team_2_name"));
        holder.tv_point1.setText(arrayList.get(position).get("live_score_team_A")+"  "+":");
        holder.tv_point2.setText(arrayList.get(position).get("live_score_team_B"));
        holder.tv_match_type.setText(arrayList.get(position).get("match_type"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsSublist=new Intent(context, GameListActivity.class);
                NewsSublist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   NewsSublist.putExtra("game_id",arrayList.get(position).get("game_id"));
                   NewsSublist.putExtra("team_1_name",arrayList.get(position).get("team_1_name"));
                   NewsSublist.putExtra("team_2_name",arrayList.get(position).get("team_2_name"));
                   NewsSublist.putExtra("team_1_image",arrayList.get(position).get("team_1_image"));
                   NewsSublist.putExtra("team_2_image",arrayList.get(position).get("team_2_image"));
                   NewsSublist.putExtra("live_score_team_A",arrayList.get(position).get("live_score_team_A"));
                   NewsSublist.putExtra("live_score_team_B",arrayList.get(position).get("live_score_team_B"));
                   NewsSublist.putExtra("match_type",arrayList.get(position).get("match_type"));

                context.startActivity(NewsSublist);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
