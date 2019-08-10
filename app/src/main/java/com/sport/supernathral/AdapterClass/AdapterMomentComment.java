package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMomentComment extends RecyclerView.Adapter<AdapterMomentComment.ItemViewHolder> {

    private Context context;

    ArrayList<HashMap<String,String>> arrayList;

    AdapterMomentComment.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_comment;
        CircleImageView profile_image1;
        AdapterMomentComment.onItemClickListner listner;


        public ItemViewHolder(View itemView, AdapterMomentComment.onItemClickListner listner) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            profile_image1=itemView.findViewById(R.id.profile_image1);


            this.listner = listner;
        }
    }


    public AdapterMomentComment(Context context,ArrayList<HashMap<String,String>>arrayList){

        this.context = context;
        this.arrayList=arrayList;

    }

    @Override
    public AdapterMomentComment.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_sub_item_comment, parent,false);
        AdapterMomentComment.ItemViewHolder dvh = new AdapterMomentComment.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final AdapterMomentComment.ItemViewHolder holder, final int position) {

        holder.tv_name.setText(arrayList.get(position).get("user_name"));
        holder.tv_comment.setText(arrayList.get(position).get("comment"));
        if (!arrayList.get(position).get("user_image").isEmpty()){
            Picasso.with(context)
                    .load(arrayList.get(position).get("user_image"))
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image1);
        }






    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }





}