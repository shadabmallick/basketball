package com.sport.supernathral.AdapterClass;

import android.content.Context;
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

public class AdapterSubComment extends RecyclerView.Adapter<AdapterSubComment.ItemViewHolder> {

private Context context;
private ArrayList<String> arrayList;
onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name, tv_comment;
    CircleImageView profile_image1;
    onItemClickListner listner;


    public ItemViewHolder(View itemView, onItemClickListner listner) {
        super(itemView);
        tv_name=itemView.findViewById(R.id.tv_name);
        tv_comment=itemView.findViewById(R.id.tv_comment);
        profile_image1=itemView.findViewById(R.id.profile_image1);


        this.listner = listner;
    }
}


    public AdapterSubComment(Context context, ArrayList<String> itemList){

        this.context = context;
        this.arrayList=itemList;

    }

    @Override
    public AdapterSubComment.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_sub_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }





}