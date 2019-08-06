package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.SimpleExoPlayer;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


import com.sport.supernathral.AdapterClass.AdapterMoments;
import com.sport.supernathral.AdapterClass.ViewPagerAdapter;
import com.sport.supernathral.R;


import java.util.ArrayList;

public class ShowImages  extends AppCompatActivity {

    String TAG="brochure";
    WebView view;
    Toolbar toolbar;
    ProgressDialog pd;
    SimpleExoPlayer exoPlayer;
    MediaPlayer mediaPlayer = new MediaPlayer();
    String file_name,file_type;
    String data;
    ViewPager viewPager;
    RelativeLayout rel_viewpager;
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_images);

        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        rel_viewpager = findViewById(R.id.rel_viewpager);
        tab_layout = findViewById(R.id.tab_layout);
       ArrayList<String> myList_image = (ArrayList<String>) getIntent().getSerializableExtra("data");
        file_name = getIntent().getStringExtra("file_name");
        file_type = getIntent().getStringExtra("file_type");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        Log.d(TAG, "brochure_file: " + myList_image);
        Log.d(TAG, "brochure_file: " + myList_image);
        setViewPager(myList_image);









        }

    private void setViewPager( ArrayList<String> list){

       tab_layout.setupWithViewPager(viewPager, true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), list);
        viewPager.setAdapter(viewPagerAdapter);

    }


        //


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                if(exoPlayer!= null){
                    exoPlayer.stop();
                    exoPlayer.release();
                    exoPlayer = null;
                }
                break;
        }
        return true;
    }

}