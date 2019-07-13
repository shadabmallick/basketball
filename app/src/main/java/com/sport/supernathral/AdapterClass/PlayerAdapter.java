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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ItemViewHolder> {

private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    PlayerAdapter.onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView name,type, desc,dismiss;
    //  ImageView iv_track, iv_complain;
    CircleImageView profile_image;
    PlayerAdapter.onItemClickListner listner;

    public ItemViewHolder(View itemView, PlayerAdapter.onItemClickListner listner) {
        super(itemView);

        name=  itemView.findViewById(R.id.name);
        type=  itemView.findViewById(R.id.type);
        profile_image=  itemView.findViewById(R.id.profile_image);

        this.listner = listner;
    }
}


    public PlayerAdapter(Context context, ArrayList<HashMap<String,String>>itemList){

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

        String type_str=arrayList.get(position).get("user_type");
        if((type_str.equals("Students/Players"))){
           holder.type.setText("Player");
        }
        else if((type_str.equals("Coach/Teachers"))){
            holder.type.setText("Trainer");
        }
        holder.name.setText(arrayList.get(position).get("user_name"));
        Picasso.with(context).load(arrayList.get(position).get("user_image")).into(holder.profile_image);





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