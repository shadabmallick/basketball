package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

public class AdapterMoments extends RecyclerView.Adapter<AdapterMoments.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterMoments.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterMoments.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterMoments.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterMoments(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterMoments.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_moments, parent,false);
        AdapterMoments.ItemViewHolder dvh = new AdapterMoments.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterMoments.ItemViewHolder holder, final int position) {





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}