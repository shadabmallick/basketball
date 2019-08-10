package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.activity.NewsSublist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;



    NewsAdapter.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView heading,short_content,dismiss;
        CircleImageView profile_image;
        //  ImageView iv_track, iv_complain;
        NewsAdapter.onItemClickListner listner;

        public ItemViewHolder(View itemView, NewsAdapter.onItemClickListner listner) {
            super(itemView);
            heading=  itemView.findViewById(R.id.heading);
            short_content=  itemView.findViewById(R.id.short_content);
            profile_image=  itemView.findViewById(R.id.profile_image);


            this.listner = listner;
        }
    }


    public NewsAdapter(Context context, ArrayList<HashMap<String,String>>itemList){

        this.context = context;
        this.arrayList=itemList;



    }

    @Override
    public NewsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_news, parent,false);
        NewsAdapter.ItemViewHolder dvh = new NewsAdapter.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(NewsAdapter.ItemViewHolder holder, final int position) {




        holder.heading.setText(arrayList.get(position).get("news_heading"));
        holder.short_content.setText(arrayList.get(position).get("news_short_content"));
        Picasso.with(context).load(arrayList.get(position).get("news_file_name")).into(holder.profile_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewsSublist=new Intent(context, NewsSublist.class);
                NewsSublist.putExtra("id",arrayList.get(position).get("news_id"));
                NewsSublist.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(NewsSublist);
            }
        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}