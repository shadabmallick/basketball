package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.supernathral.Fragment.SkillSet;
import com.sport.supernathral.R;
import com.sport.supernathral.activity.InfoActivity;
import com.sport.supernathral.activity.NotesActivity;

import java.util.ArrayList;

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;



    StudentAdapter.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss;
        //  ImageView iv_track, iv_complain;
        StudentAdapter.onItemClickListner listner;

        public ItemViewHolder(View itemView, StudentAdapter.onItemClickListner listner) {
            super(itemView);



            this.listner = listner;
        }
    }


    public StudentAdapter(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public StudentAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_student_list, parent,false);
        StudentAdapter.ItemViewHolder dvh = new StudentAdapter.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(StudentAdapter.ItemViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent notesActivity=new Intent(context, NotesActivity.class);
              context.startActivity(notesActivity);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}