package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class AdapterSubComment extends RecyclerView.Adapter<AdapterSubComment.ItemViewHolder> {

private Context context;
    ArrayList<HashMap<String,String>> subcomment_list;
onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name, tv_comment;
    CircleImageView profile_image1;
    onItemClickListner listner;


    public ItemViewHolder(View itemView, onItemClickListner listner) {
        super(itemView);
        tv_name=itemView.findViewById(R.id.tv_name);
        tv_comment=itemView.findViewById(R.id.tv_comment);
        profile_image1=itemView.findViewById(R.id.profile_image1);


        this.listner = listner;
    }
}


    public AdapterSubComment(Context context, ArrayList<HashMap<String,String>>itemList){

        this.context = context;
        this.subcomment_list=itemList;

    }

    @Override
    public AdapterSubComment.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_sub_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        Picasso.with(context).load(subcomment_list.get(position).get("sub_user_image")).placeholder(R.mipmap.profile_placeholder).into(holder.profile_image1);
      //  holder.tv_comment.setText(subcomment_list.get(position).get("news_comment_sub_count"));
        holder.tv_name.setText(subcomment_list.get(position).get("sub_user_name"));
        holder.tv_comment.setText(subcomment_list.get(position).get("sub_comment"));
        Log.d(TAG, "onBindViewHolder: "+subcomment_list);


    }

    @Override
    public int getItemCount() {
        return subcomment_list.size();
    }





}