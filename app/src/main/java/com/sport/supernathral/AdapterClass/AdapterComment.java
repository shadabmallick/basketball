package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ItemViewHolder> {

private Context context;
private ArrayList<String> arrayList;
private RecyclerView subCommnet;

    int row_index=-1;

    AdapterComment.onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView action, desc,dismiss;
    //  ImageView iv_track, iv_complain;
    AdapterComment.onItemClickListner listner;
    ImageButton like,comment;
    RelativeLayout rl_sublist;
    public ItemViewHolder(View itemView, AdapterComment.onItemClickListner listner) {
        super(itemView);
        comment=itemView.findViewById(R.id.img_comment);
        rl_sublist=itemView.findViewById(R.id.rl_sublist);




        this.listner = listner;
    }
}


    public AdapterComment(Context context, ArrayList<String> itemList,RecyclerView subCommnet){

        this.context = context;
        this.arrayList=itemList;
        this.subCommnet=subCommnet;



    }

    @Override
    public AdapterComment.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_comment, parent,false);
        AdapterComment.ItemViewHolder dvh = new AdapterComment.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final AdapterComment.ItemViewHolder holder, final int position) {


      holder.comment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              row_index = position;
              notifyDataSetChanged();
          }
      });
        if(row_index==position){
            holder.rl_sublist.setVisibility(View.VISIBLE);
          //  holder.plus.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rl_sublist.setVisibility(View.GONE);
           // holder.plus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}