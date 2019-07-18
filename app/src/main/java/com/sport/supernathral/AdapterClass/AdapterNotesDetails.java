package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesDetails;

import java.util.ArrayList;
import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;

public class AdapterNotesDetails extends RecyclerView.Adapter<AdapterNotesDetails.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;
    String from;


    AdapterNotesDetails.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, tv_notes,tv_date;
        //  ImageView iv_track, iv_complain;
        AdapterNotesDetails.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterNotesDetails.onItemClickListner listner) {
            super(itemView);
            tv_notes=itemView.findViewById(R.id.tv_notes);
            tv_date=itemView.findViewById(R.id.tv_date);


            this.listner = listner;
        }
    }


    public AdapterNotesDetails(Context context,ArrayList<HashMap<String,String>>itemList,String from){

        this.context = context;
        this.arrayList=itemList;
        this.from=from;



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
        if (from.equals("Notes")) {

            holder.tv_date.setText(arrayList.get(position).get("date"));
            holder.tv_notes.setText(arrayList.get(position).get("desc"));
        }
        else {
            Log.d(TAG, "onBindViewHolder: "+arrayList);
         holder.tv_date.setText(arrayList.get(position).get("date"));
         holder.tv_notes.setText(arrayList.get(position).get("note"));
         }

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(context, NotesDetails.class);

             intent.putExtra("note",arrayList.get(position).get("note"));
             intent.putExtra("date",arrayList.get(position).get("date"));
             context.startActivity(intent);
         }
     });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}