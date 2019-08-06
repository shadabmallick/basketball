package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterAttendence extends
        RecyclerView.Adapter<AdapterAttendence.ItemViewHolder> {

private Context context;

    ArrayList<HashMap<String,String>> arrayList;


    AdapterAttendence.onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView action, desc,dismiss,tv_date,tv_status;
    String present,absent,late,excuse;
    ImageView img_present,img_late,img_absent,img_excuse;
    AdapterAttendence.onItemClickListner listner;

    public ItemViewHolder(View itemView, AdapterAttendence.onItemClickListner listner) {
        super(itemView);

        img_present=itemView.findViewById(R.id.img_present);
        img_late=itemView.findViewById(R.id.img_late);
        img_absent=itemView.findViewById(R.id.img_absent);
        img_excuse=itemView.findViewById(R.id.img_excuse);
        tv_date=itemView.findViewById(R.id.tv_date);
        tv_status=itemView.findViewById(R.id.tv_status);


        this.listner = listner;
    }
}


    public AdapterAttendence(Context context, ArrayList<HashMap<String,String>>  itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public AdapterAttendence.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_attendence, parent,false);
        AdapterAttendence.ItemViewHolder dvh = new AdapterAttendence.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterAttendence.ItemViewHolder holder, final int position) {

       if(arrayList.get(position).get("present").equals("1")){
           holder.img_present.setVisibility(View.VISIBLE);
           holder.img_absent.setVisibility(View.GONE);
           holder.img_excuse.setVisibility(View.GONE);
           holder.img_late.setVisibility(View.GONE);
           holder.tv_status.setText("Present");

       }
       else if(arrayList.get(position).get("late").equals("1")){
           holder.img_present.setVisibility(View.GONE);
           holder.img_absent.setVisibility(View.GONE);
           holder.img_excuse.setVisibility(View.GONE);
           holder.img_late.setVisibility(View.VISIBLE);
           holder.tv_status.setText("Late");

       }
       else if(arrayList.get(position).get("absent").equals("1")){
           holder.img_present.setVisibility(View.GONE);
           holder.img_absent.setVisibility(View.VISIBLE);
           holder.img_excuse.setVisibility(View.GONE);
           holder.img_late.setVisibility(View.GONE);
           holder.tv_status.setText("Absent");

       }
       else if(arrayList.get(position).get("excuses").equals("1")){
           holder.img_present.setVisibility(View.GONE);
           holder.img_absent.setVisibility(View.GONE);
           holder.img_excuse.setVisibility(View.VISIBLE);
           holder.img_late.setVisibility(View.GONE);
           holder.tv_status.setText("Excuses");

       }
     holder.tv_date.setText(arrayList.get(position).get("date"));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}