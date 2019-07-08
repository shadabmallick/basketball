package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ItemViewHolder> {

private Context context;
private ArrayList<String> arrayList;
int row_index=-1;
onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView action, desc,dismiss, tv_show_more;
    onItemClickListner listner;
    ImageView like, comment;
    RelativeLayout rl_sublist;
    CircleImageView profile_image;
    RecyclerView recycler_sub_comments;

    public ItemViewHolder(View itemView, onItemClickListner listner) {
        super(itemView);
        comment=itemView.findViewById(R.id.img_comment);
        rl_sublist=itemView.findViewById(R.id.rl_sublist);
        tv_show_more=itemView.findViewById(R.id.tv_show_more);
        profile_image=itemView.findViewById(R.id.profile_image);

        recycler_sub_comments=itemView.findViewById(R.id.recycler_sub_comments);

        this.listner = listner;
    }
}


    public AdapterComment(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {


      holder.comment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              row_index = position;
              notifyDataSetChanged();
          }
      });

      holder.tv_show_more.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


          }
      });

      if(row_index==position){

            ArrayList<String> newsList = new ArrayList<>();
            newsList.add("A");
            newsList.add("A");
            newsList.add("A");
            setSubComment(holder.recycler_sub_comments, newsList);

            holder.rl_sublist.setVisibility(View.VISIBLE);
          //  holder.plus.setVisibility(View.INVISIBLE);

        } else {

            holder.rl_sublist.setVisibility(View.GONE);
           // holder.plus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void setSubComment(RecyclerView recyclerView, ArrayList<String> list){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdapterSubComment adapterChat = new AdapterSubComment(context, list);
        recyclerView.setAdapter(adapterChat);


    }



}