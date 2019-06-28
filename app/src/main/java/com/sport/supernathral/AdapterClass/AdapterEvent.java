package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterEvent.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterEvent.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterEvent.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterEvent(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterEvent.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_event, parent,false);
        AdapterEvent.ItemViewHolder dvh = new AdapterEvent.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterEvent.ItemViewHolder holder, final int position) {





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}