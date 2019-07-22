package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupUserAdapter extends
        RecyclerView.Adapter<GroupUserAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<MembersData> arrayList;



    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_user_name;
        CircleImageView profile_image;
        RelativeLayout rel_main;
        ImageView iv_cancel;


        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            profile_image = itemView.findViewById(R.id.profile_image);
            rel_main = itemView.findViewById(R.id.rel_main);
            iv_cancel = itemView.findViewById(R.id.iv_cancel);

        }
    }


    public GroupUserAdapter(Context context, ArrayList<MembersData> itemList){
        this.context = context;
        this.arrayList=itemList;


    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_user_item, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v);
        return dvh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        MembersData membersData = arrayList.get(position);

        if (!membersData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(membersData.getUser_image())
                    .placeholder(R.mipmap.profile_placeholder)
                    .into(holder.profile_image);
        }

        holder.tv_user_name.setText(membersData.getUser_name());


        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public String getSelectedUserIds(){

        StringBuilder sb = new StringBuilder();
        String result = "";
        for (int i = 0; i < arrayList.size(); i++){
            sb.append(arrayList.get(i).getId()).append(",");

        }

        result = sb.deleteCharAt(sb.length() - 1).toString();

        return result;

    }



}