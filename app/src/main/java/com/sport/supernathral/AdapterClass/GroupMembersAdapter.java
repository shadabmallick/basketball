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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMembersAdapter extends
        RecyclerView.Adapter<GroupMembersAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<MembersData> arrayList;


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
                               onItemClickListner mListner){
        this.context = context;
        this.arrayList=itemList;
        this.mListner=mListner;
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



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}