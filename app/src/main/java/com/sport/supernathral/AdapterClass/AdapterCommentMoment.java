package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.SubCommentData;
import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterCommentMoment extends RecyclerView.Adapter<AdapterCommentMoment.ItemViewHolder> {

private Context context;
ArrayList<CommentData> comment_list;
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


    public AdapterCommentMoment(Context context, ArrayList<CommentData>comment_list){

        this.context = context;
        this.comment_list=comment_list;

    }

    @Override
    public AdapterCommentMoment.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_sub_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        CommentData commentData = comment_list.get(position);

        if (!commentData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(commentData.getUser_image())
                    .placeholder(R.mipmap.profile_placeholder)
                    .into(holder.profile_image1);
        }


        holder.tv_name.setText(commentData.getUser_name());
        holder.tv_comment.setText(commentData.getComment());
      //  Log.d(TAG, "onBindViewHolder: "+subcomment_list);


    }

    @Override
    public int getItemCount() {
        return comment_list.size();
    }





}