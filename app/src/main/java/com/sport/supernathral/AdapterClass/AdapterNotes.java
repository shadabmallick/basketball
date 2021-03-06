package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesDetails;

import java.util.ArrayList;

public class AdapterNotes  extends RecyclerView.Adapter<AdapterNotes.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterNotes.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterNotes.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterNotes.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterNotes(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterNotes.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_notes, parent,false);
        AdapterNotes.ItemViewHolder dvh = new AdapterNotes.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterNotes.ItemViewHolder holder, final int position) {




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}