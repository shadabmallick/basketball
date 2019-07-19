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
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.activity.GroupProfileInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMembersAdapter extends
        RecyclerView.Adapter<GroupMembersAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<MembersData> arrayList;
    private String group_admin_id;

    private GlobalClass globalClass;


    private onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(MembersData membersData);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_user_name, tv_designation, tv_remove;
        CircleImageView iv_user;
        RelativeLayout rel_main;
        onItemClickListner listner;

        public ItemViewHolder(View itemView, onItemClickListner listner) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_designation = itemView.findViewById(R.id.tv_designation);
            iv_user = itemView.findViewById(R.id.iv_user);
            tv_remove = itemView.findViewById(R.id.tv_remove);
            rel_main = itemView.findViewById(R.id.rel_main);
            this.listner = listner;
        }
    }


    public GroupMembersAdapter(Context context, ArrayList<MembersData> itemList,
                               String group_admin_id, onItemClickListner mListner){
        this.context = context;
        this.arrayList=itemList;
        this.mListner=mListner;
        this.group_admin_id=group_admin_id;

        globalClass = (GlobalClass) context.getApplicationContext();

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_member_item, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        // user type = Coach/Teachers  ,  Students/Players

        final MembersData membersData = arrayList.get(position);

        if (!membersData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(membersData.getUser_image())
                    .placeholder(R.mipmap.profile_placeholder)
                    .into(holder.iv_user);
        }

        holder.tv_user_name.setText(membersData.getUser_name());

        if (Common.player.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Player");
        }else if (Common.parent.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Parent");
        }else if (Common.trainer.equals(membersData.getUser_type())){
            holder.tv_designation.setText("Trainer");
        }


        if (group_admin_id.equals(globalClass.getId())){
            holder.tv_remove.setVisibility(View.VISIBLE);
        }else {
            holder.tv_remove.setVisibility(View.GONE);
        }


        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.onItemClick(membersData);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}