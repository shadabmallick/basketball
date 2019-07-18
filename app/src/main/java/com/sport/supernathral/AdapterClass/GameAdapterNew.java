package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.GameListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameAdapterNew extends RecyclerView.Adapter<GameAdapterNew.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    GameAdapterNew.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_team1,tv_team2,tv_time,tv_day,tv_date;
        CircleImageView iv_team1,iv_team2;
        //  ImageView iv_track, iv_complain;
        GameAdapterNew.onItemClickListner listner;

        public ItemViewHolder(View itemView, GameAdapterNew.onItemClickListner listner) {
            super(itemView);

            tv_team1=itemView.findViewById(R.id.tv_team1);
            tv_team2=itemView.findViewById(R.id.tv_team2);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_day=itemView.findViewById(R.id.tv_day);
            iv_team1=itemView.findViewById(R.id.iv_team1);
            tv_date=itemView.findViewById(R.id.tv_date);
            iv_team2=itemView.findViewById(R.id.iv_team2);

            this.listner = listner;
        }
    }


    public GameAdapterNew(Context context, ArrayList<HashMap<String,String>> arrayList){

        this.context = context;
        this.arrayList=arrayList;



    }

    @Override
    public GameAdapterNew.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent,false);
        GameAdapterNew.ItemViewHolder dvh = new GameAdapterNew.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(GameAdapterNew.ItemViewHolder holder, final int position) {

        holder.tv_team1.setText(arrayList.get(position).get("team1_name"));
        holder.tv_team2.setText(arrayList.get(position).get("team2_name"));
        holder.tv_time.setText(arrayList.get(position).get("time"));
        holder.tv_date.setText(arrayList.get(position).get("date"));
        holder.tv_day.setText(arrayList.get(position).get("day"));

        Picasso.with(context).load(arrayList.get(position).get("team_image")).into(holder.iv_team1);
        Picasso.with(context).load(arrayList.get(position).get("team2_image")).into(holder.iv_team2);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsSublist=new Intent(context, GameListActivity.class);
                NewsSublist.putExtra("game_id",arrayList.get(position).get("game_id"));
                NewsSublist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                context.startActivity(NewsSublist);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
