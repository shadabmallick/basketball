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
import com.sport.supernathral.activity.InfoActivity;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ItemViewHolder> {

private Context context;
private ArrayList<String> arrayList;



    PlayerAdapter.onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView action, desc,dismiss;
    //  ImageView iv_track, iv_complain;
    PlayerAdapter.onItemClickListner listner;

    public ItemViewHolder(View itemView, PlayerAdapter.onItemClickListner listner) {
        super(itemView);



        this.listner = listner;
    }
}


    public PlayerAdapter(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public PlayerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_player, parent,false);
        PlayerAdapter.ItemViewHolder dvh = new PlayerAdapter.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(PlayerAdapter.ItemViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsSublist=new Intent(context, InfoActivity.class);
                NewsSublist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                /*blogSingle.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                blogSingle.putExtra("file_name", dataArrayList.get(i).getFile_name());
                blogSingle.putExtra("heading", dataArrayList.get(i).getHeading());
                blogSingle.putExtra("content", dataArrayList.get(i).getContent());
                blogSingle.putExtra("short_content", dataArrayList.get(i).getShort_content());
                blogSingle.putExtra("date", dataArrayList.get(i).getDate());*/
                context.startActivity(NewsSublist);
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}