package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ItemViewHolder> {

    private Context context;
    private ArrayList<ChatListData> arrayList;


    private onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(ChatListData chatListData);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_last_seen, tv_user_name, tv_last_message;
        CircleImageView profile_image;
        ImageView tv_last_image;
        RelativeLayout rel_main;
        onItemClickListner listner;

        public ItemViewHolder(View itemView, onItemClickListner listner) {
            super(itemView);

            tv_last_seen = itemView.findViewById(R.id.tv_last_seen);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_last_message = itemView.findViewById(R.id.tv_last_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            tv_last_image = itemView.findViewById(R.id.tv_last_image);
            rel_main = itemView.findViewById(R.id.rel_main);
            this.listner = listner;
        }
    }


    public AdapterChat(Context context, ArrayList<ChatListData> itemList,
                       onItemClickListner mListner){
        this.context = context;
        this.arrayList=itemList;
        this.mListner=mListner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_chat, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        // user type = Coach/Teachers  ,  Students/Players

        final ChatListData chatListData = arrayList.get(position);


        if (!chatListData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(chatListData.getUser_image())
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }


        holder.tv_user_name.setText(chatListData.getUser_name());


        if (chatListData.getMessage_type().isEmpty()){
            holder.tv_last_image.setVisibility(View.GONE);
            holder.tv_last_message.setVisibility(View.GONE);
        }else if (chatListData.getMessage_type().equals("3")){
            holder.tv_last_image.setVisibility(View.VISIBLE);
            holder.tv_last_message.setVisibility(View.GONE);
        }else {
            holder.tv_last_message.setText(chatListData.getMessage());
            holder.tv_last_image.setVisibility(View.GONE);
            holder.tv_last_message.setVisibility(View.VISIBLE);
        }

        holder.tv_last_seen.setText(chatListData.getDatetime());



        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListner.onItemClick(chatListData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}