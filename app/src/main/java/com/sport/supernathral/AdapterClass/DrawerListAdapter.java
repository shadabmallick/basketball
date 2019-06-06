package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.DataModel.DrawerItem;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class DrawerListAdapter extends
        RecyclerView.Adapter<DrawerListAdapter.DrawerItemViewHolder> {

    private Context context;
    private ArrayList<DrawerItem> menuList;
    private ArrayList<Boolean> selectedItem;


    onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class DrawerItemViewHolder extends RecyclerView.ViewHolder{
        TextView menu_item_title;
        ImageView menu_item_icon;
        RelativeLayout rel_main;
        onItemClickListner listner;

        public DrawerItemViewHolder(View itemView, onItemClickListner listner) {
            super(itemView);
            menu_item_title = itemView.findViewById(R.id.menu_item_title);
            menu_item_icon = itemView.findViewById(R.id.menu_item_icon);
            rel_main = itemView.findViewById(R.id.rel_main);
            this.listner = listner;
        }
    }


    public DrawerListAdapter(Context context, ArrayList<DrawerItem> itemList,
                             onItemClickListner listner){

        this.context = context;
        this.menuList=itemList;
        this.mListner=listner;


        setFirstTimeData();
    }

    private void setFirstTimeData(){
        selectedItem = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++){
            if (i == 0){
                selectedItem.add(true);
            }else {
                selectedItem.add(false);
            }

        }
    }


    private void setSelectedItem(){
        selectedItem = new ArrayList<>();
        for (int i = 0; i < menuList.size(); i++){
            selectedItem.add(false);
        }
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_item, parent,false);
        DrawerItemViewHolder dvh = new DrawerItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, final int position) {

        DrawerItem currentItem = menuList.get(position);

        holder.menu_item_title.setText(currentItem.getTitle());

        holder.menu_item_icon.setImageResource(currentItem.getImgResID());



        if (selectedItem.get(position)){
            holder.rel_main.setBackgroundColor(context.getResources()
                    .getColor(R.color.deep_yellow));
        }else {
            holder.rel_main.setBackgroundColor(context.getResources()
                    .getColor(R.color.white));
        }

        holder.rel_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListner.onItemClick(position);


                setSelectedItem();
                selectedItem.add(position, true);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

}
