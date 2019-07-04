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
import com.sport.supernathral.R;
import com.sport.supernathral.activity.ChatImageFull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSelectionAdapter extends
        RecyclerView.Adapter<UserSelectionAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;
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


    public UserSelectionAdapter(Context context, ArrayList<String> itemList){
        this.context = context;
        this.arrayList=itemList;


        setInitData();

    }

    private void setInitData(){
        arr_selection = new ArrayList<>();
        for (String s : arrayList){
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

        // user type = Coach/Teachers  ,  Students/Players


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



    public ArrayList<String> getSelectedUsers(){

        ArrayList<String> arrayList1 = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++){
            if (arr_selection.get(i)){
                arrayList1.add(arrayList.get(i));
            }
        }

        return arrayList1;

    }



}