package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.sport.supernathral.R;
import com.sport.supernathral.activity.ShowImages;
import com.sport.supernathral.activity.ShowVideo;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> images;
    private LayoutInflater layoutInflater;


    public ViewPagerAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.viewpager_single, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);


        Glide.with(context).load(images.get(position))
                .placeholder(R.mipmap.image_black)
                .into(imageView);
       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent image = new Intent(context, ShowImages.class);
               image.putExtra("data",images);
               image.putExtra("type","image");
               context.startActivity(image);
           }
       });

        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


}