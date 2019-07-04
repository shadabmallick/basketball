package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesDetails;

import java.util.ArrayList;

public class AdapterNotesDetails extends RecyclerView.Adapter<AdapterNotesDetails.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdapterNotesDetails.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdapterNotesDetails.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterNotesDetails.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdapterNotesDetails(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterNotesDetails.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_notes, parent,false);
        AdapterNotesDetails.ItemViewHolder dvh = new AdapterNotesDetails.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterNotesDetails.ItemViewHolder holder, final int position) {

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(context, NotesDetails.class);
             context.startActivity(intent);
         }
     });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}