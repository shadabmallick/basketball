package com.sport.supernathral.AdapterClass;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.sport.supernathral.DataModel.MomentData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.ScalableVideoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMoments extends RecyclerView.Adapter<AdapterMoments.ItemViewHolder> {

    private Context context;
    private ArrayList<MomentData> listMoments;



    AdapterMoments.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile_image;
        TextView tv_name, tv_date, tv_like_no, tv_comment_no, tv_report;
        ImageView iv_delete, iv_like, img_comment;
        VideoView videoView;
        ViewPager viewPager;
        TabLayout tab_layout;
        RelativeLayout rel_viewpager, rel_videoview;
        ProgressBar progressBar;
        AdapterMoments.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterMoments.onItemClickListner listner) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_like_no = itemView.findViewById(R.id.tv_like_no);
            tv_comment_no = itemView.findViewById(R.id.tv_comment_no);
            tv_report = itemView.findViewById(R.id.tv_report);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_like = itemView.findViewById(R.id.iv_like);
            img_comment = itemView.findViewById(R.id.img_comment);
            videoView = itemView.findViewById(R.id.videoView);
            tab_layout = itemView.findViewById(R.id.tab_layout);
            viewPager = itemView.findViewById(R.id.viewPager);
            rel_viewpager = itemView.findViewById(R.id.rel_viewpager);
            rel_videoview = itemView.findViewById(R.id.rel_videoview);
            progressBar = itemView.findViewById(R.id.progressBar);



            this.listner = listner;
        }
    }


    public AdapterMoments(Context context, ArrayList<MomentData> itemList){
        this.context = context;
        this.listMoments=itemList;

    }

    @Override
    public AdapterMoments.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_moments, parent,false);
        AdapterMoments.ItemViewHolder dvh = new AdapterMoments.ItemViewHolder(v, mListner);
        return dvh;
    }

    @Override
    public int getItemCount() {
        return listMoments.size();
    }

    @Override
    public void onBindViewHolder(AdapterMoments.ItemViewHolder holder, final int position) {

        MomentData momentData = listMoments.get(position);

        if (!momentData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(momentData.getUser_image())
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }

        holder.tv_name.setText(momentData.getUser_name());

        if (momentData.getFile_type().equals("image")){
            setViewPager(holder, momentData.getMoment_files());
            holder.rel_videoview.setVisibility(View.GONE);
            holder.rel_viewpager.setVisibility(View.VISIBLE);
        }else {
            setVideo(holder, momentData.getMoment_files());
            holder.rel_videoview.setVisibility(View.VISIBLE);
            holder.rel_viewpager.setVisibility(View.GONE);
        }


    }


    private void setViewPager(ItemViewHolder holder, ArrayList<String> list){

        holder.tab_layout.setupWithViewPager(holder.viewPager, true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, list);
        holder.viewPager.setAdapter(viewPagerAdapter);

    }

    private void setVideo(final ItemViewHolder holder, ArrayList<String> list){

        holder.progressBar.setVisibility(View.VISIBLE);

        if (list.size() > 0){

            String VideoURL = list.get(0);
            try {

                MediaController mediacontroller = new MediaController(
                        context);
                mediacontroller.setAnchorView(null);
                //Uri video = Uri.parse(VideoURL);
                holder.videoView.setMediaController(null);
                //holder.videoView.setVideoURI(video);
                holder.videoView.setVideoPath(VideoURL);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            holder.videoView.requestFocus();

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    holder.progressBar.setVisibility(View.GONE);
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int h = displaymetrics.heightPixels;
                    int w = displaymetrics.widthPixels;
                    //holder.videoView.setLayoutParams(new FrameLayout.LayoutParams(w, h));
                    holder.videoView.start();
                }
            });
        }

    }


}