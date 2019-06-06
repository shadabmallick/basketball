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
import com.sport.supernathral.DataModel.GameData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GameItem;
import com.sport.supernathral.Utils.HeaderItem;
import com.sport.supernathral.Utils.ListItem;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ListItem> mItems;


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView tv_header;


        public HeaderViewHolder(View itemView) {
            super(itemView);
            tv_header = itemView.findViewById(R.id.tv_header);

        }
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{
        TextView menu_item_title;
        ImageView menu_item_icon;
        RelativeLayout rel_main;

        public GameViewHolder(View itemView) {
            super(itemView);
            menu_item_title = itemView.findViewById(R.id.menu_item_title);
            menu_item_icon = itemView.findViewById(R.id.menu_item_icon);
            rel_main = itemView.findViewById(R.id.rel_main);
        }
    }


    public GameListAdapter(Context context, ArrayList<ListItem> mItems){

        this.context = context;
        this.mItems=mItems;




    }


    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == ListItem.TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_header_item, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_item, parent, false);
            return new GameViewHolder(itemView);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        int type = getItemViewType(position);
        if (type == ListItem.TYPE_HEADER) {
            HeaderItem header = (HeaderItem) mItems.get(position);
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;

            holder.tv_header.setText(header.getHeaderName());


        } else {
            GameItem gameItem = (GameItem) mItems.get(position);
            GameViewHolder holder = (GameViewHolder) viewHolder;




        }


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }











}
