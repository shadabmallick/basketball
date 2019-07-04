package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NotesActivity;

import java.util.ArrayList;

public class AdpterStudentList extends
        RecyclerView.Adapter<AdpterStudentList.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    AdpterStudentList.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        AdpterStudentList.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdpterStudentList.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public AdpterStudentList(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdpterStudentList.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_student_list_adapter, parent,false);
        AdpterStudentList.ItemViewHolder dvh = new AdpterStudentList.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdpterStudentList.ItemViewHolder holder, final int position) {

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(context, NotesActivity.class);
             context.startActivity(intent);
         }
     });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}