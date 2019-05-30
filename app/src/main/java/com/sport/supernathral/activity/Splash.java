package com.sport.supernathral.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sport.supernathral.R;

public class Splash extends AppCompatActivity {

    ViewPager viewPager;

    int[] mResources = {
            R.mipmap.slider1,
            R.mipmap.slider2,
            R.mipmap.slider3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initViews();


    }


    private void initViews(){

        viewPager = findViewById(R.id.viewpager);

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this);
        viewPager.setAdapter(customPagerAdapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 2){

                    Intent intent = new Intent(Splash.this, Splash2.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }



    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.image_single,
                    container, false);

            ImageView imageView = itemView.findViewById(R.id.iv_image);
            imageView.setImageResource(mResources[position]);



            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
