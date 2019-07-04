package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

public class AdapterSchduleUserwise extends
        RecyclerView.Adapter<AdapterSchduleUserwise.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterSchduleUserwise.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterSchduleUserwise.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterSchduleUserwise.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterSchduleUserwise(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterSchduleUserwise.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendence_activity, parent,false);
        AdapterSchduleUserwise.ItemViewHolder dvh = new AdapterSchduleUserwise.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterSchduleUserwise.ItemViewHolder holder, final int position) {





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}