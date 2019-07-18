package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.EventDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    AdapterEvent.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_name,tv_start_date,tv_venue,tv_end_date;
        ImageView profile_image;
        RelativeLayout rl_next,complete,ongoing,upcoming;
        //  ImageView iv_track, iv_complain;
        AdapterEvent.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterEvent.onItemClickListner listner) {
            super(itemView);
            rl_next = itemView.findViewById(R.id.rl_next);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_start_date = itemView.findViewById(R.id.start_date);
            tv_end_date = itemView.findViewById(R.id.end_date);
            tv_venue = itemView.findViewById(R.id.venue);
            profile_image = itemView.findViewById(R.id.profile_image);
            complete = itemView.findViewById(R.id.rl_complete);
            ongoing = itemView.findViewById(R.id.rl_ongoing);
            upcoming = itemView.findViewById(R.id.rl_upcoming);



            this.listner = listner;
        }
    }


    public AdapterEvent(Context context, ArrayList<HashMap<String,String>>itemList){

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

        if (!arrayList.get(position).get("image").isEmpty()){
            Picasso.with(context)
                    .load(arrayList.get(position).get("image"))
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }

        if(arrayList.get(position).get("eventType").equals("Past")){

            holder.complete.setVisibility(View.VISIBLE);
            holder.ongoing.setVisibility(View.GONE);
            holder.upcoming.setVisibility(View.GONE);
        }
        else if(arrayList.get(position).get("eventType").equals("Ongoing")){

            holder.complete.setVisibility(View.GONE);
            holder.ongoing.setVisibility(View.VISIBLE);
            holder.upcoming.setVisibility(View.GONE);
        }
        else if(arrayList.get(position).get("eventType").equals("Upcoming")){

            holder.complete.setVisibility(View.GONE);
            holder.ongoing.setVisibility(View.GONE);
            holder.upcoming.setVisibility(View.VISIBLE);
        }

    holder.tv_name.setText(arrayList.get(position).get("name"));
    holder.tv_start_date.setText(arrayList.get(position).get("event_start_date"));
    holder.tv_end_date.setText(arrayList.get(position).get("event_end_date"));
    holder.tv_venue.setText(arrayList.get(position).get("event_venue"));

        holder.rl_next .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent event=new Intent(context, EventDetails.class);
                    event.putExtra("name",arrayList.get(position).get("name"));
                    event.putExtra("event_start_date",arrayList.get(position).get("event_start_date"));
                    event.putExtra("event_end_date",arrayList.get(position).get("event_end_date"));
                    event.putExtra("event_venue",arrayList.get(position).get("event_venue"));
                    event.putExtra("image",arrayList.get(position).get("image"));
                    event.putExtra("main_access_group_id",arrayList.get(position).get("main_access_group_id"));
                    event.putExtra("event_address",arrayList.get(position).get("event_address"));
                    event.putExtra("event_city",arrayList.get(position).get("event_city"));
                    event.putExtra("event_state",arrayList.get(position).get("event_state"));
                    event.putExtra("event_pincode",arrayList.get(position).get("event_pincode"));
                    event.putExtra("event_desc",arrayList.get(position).get("event_desc"));
                    event.putExtra("event_contact_name",arrayList.get(position).get("event_contact_name"));
                    event.putExtra("event_contact_designation",arrayList.get(position).get("event_contact_designation"));
                    event.putExtra("event_contact_email",arrayList.get(position).get("event_contact_email"));
                    event.putExtra("event_contact_phone",arrayList.get(position).get("event_contact_phone"));
                    event.putExtra("delete_flag",arrayList.get(position).get("delete_flag"));
                    event.putExtra("is_active",arrayList.get(position).get("is_active"));
                    event.putExtra("entry_date",arrayList.get(position).get("entry_date"));
                    event.putExtra("modified_date",arrayList.get(position).get("modified_date"));
                    event.putExtra("eventType",arrayList.get(position).get("eventType"));


                    context.startActivity(event);
                }
            });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}