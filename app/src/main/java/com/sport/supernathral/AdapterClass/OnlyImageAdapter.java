package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnlyImageAdapter extends
        RecyclerView.Adapter<OnlyImageAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<Uri> arrayList;



    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        public ItemViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

        }
    }


    public OnlyImageAdapter(Context context, ArrayList<Uri> itemList){
        this.context = context;
        this.arrayList=itemList;


    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.only_image_item, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v);
        return dvh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        Uri uri = arrayList.get(position);

        holder.image.setImageURI(uri);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




}