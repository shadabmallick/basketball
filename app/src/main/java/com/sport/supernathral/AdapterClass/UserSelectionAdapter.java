package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.activity.ChatImageFull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSelectionAdapter extends
        RecyclerView.Adapter<UserSelectionAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<MembersData> arrayList;
    private ArrayList<Boolean> arr_selection;


    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_user_name, tv_designation;
        CircleImageView profile_image;
        RelativeLayout rel_main;
        ImageView iv_selection;


        public ItemViewHolder(View itemView) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_designation = itemView.findViewById(R.id.tv_designation);
            profile_image = itemView.findViewById(R.id.profile_image);
            rel_main = itemView.findViewById(R.id.rel_main);
            iv_selection = itemView.findViewById(R.id.iv_selection);

        }
    }


    public UserSelectionAdapter(Context context, ArrayList<MembersData> itemList){
        this.context = context;
        this.arrayList=itemList;


        setInitData();

    }

    private void setInitData(){
        arr_selection = new ArrayList<>();
        for (MembersData data : arrayList){
            arr_selection.add(false);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent,false);
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
        if (Common.player.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Player");
        }else if (Common.parent.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Parent");
        }else if (Common.trainer.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Trainer");
        }



        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arr_selection.get(position)){
                    arr_selection.set(position, false);
                }else {
                    arr_selection.set(position, true);
                }

                notifyDataSetChanged();

            }
        });


        if (arr_selection.get(position)){
            holder.iv_selection.setVisibility(View.VISIBLE);
        }else {
            holder.iv_selection.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public ArrayList<String> getSelectedUserIds(){

        ArrayList<String> arrayList1 = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++){
            if (arr_selection.get(i)){
                arrayList1.add(arrayList.get(i).getId());
            }
        }

        return arrayList1;

    }

    public String getIds(){

        StringBuilder sb = new StringBuilder();
        String result = "";
        for (int i = 0; i < arrayList.size(); i++){
            if (arr_selection.get(i)){
                sb.append(arrayList.get(i).getId()).append(",");
            }
        }

        result = sb.deleteCharAt(sb.length() - 1).toString();

        return result;
    }



}