package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    SponsorAdapter.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
          ImageView img_StudentName;

        SponsorAdapter.onItemClickListner listner;

        public ItemViewHolder(View itemView, SponsorAdapter.onItemClickListner listner) {
            super(itemView);
            img_StudentName=itemView.findViewById(R.id.tvStudentName);


            this.listner = listner;
        }
    }


    public SponsorAdapter(Context context,ArrayList<HashMap<String,String>>itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public SponsorAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_sponsor, parent,false);
        SponsorAdapter.ItemViewHolder dvh = new SponsorAdapter.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(SponsorAdapter.ItemViewHolder holder, final int position) {


        Picasso.with(context).load(arrayList.get(position).get("image")).into(holder.img_StudentName);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
