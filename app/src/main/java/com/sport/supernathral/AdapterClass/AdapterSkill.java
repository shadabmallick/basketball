package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

public class AdapterSkill extends RecyclerView.Adapter<AdapterSkill.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterSkill.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterSkill.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterSkill.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterSkill(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterSkill.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_skill, parent,false);
        AdapterSkill.ItemViewHolder dvh = new AdapterSkill.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterSkill.ItemViewHolder holder, final int position) {





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}