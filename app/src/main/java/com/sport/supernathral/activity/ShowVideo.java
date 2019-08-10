package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.sport.supernathral.AdapterClass.AdapterMoments;
import com.sport.supernathral.AdapterClass.ViewPagerAdapter;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class ShowVideo  extends AppCompatActivity {

    String TAG="brochure";
    WebView view;
    Toolbar toolbar;
    ProgressDialog pd;
    MediaController ctrl;
    SimpleExoPlayerView exoPlayerView;
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
        setContentView(R.layout.display_image_video);
        exoPlayerView =  findViewById(R.id.exo_player_view);
        toolbar = findViewById(R.id.toolbar);
        view = findViewById(R.id.webView);
        viewPager = findViewById(R.id.viewPager);
        rel_viewpager = findViewById(R.id.rel_viewpager);
        tab_layout = findViewById(R.id.tab_layout);
        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("file_name");
        file_name = getIntent().getStringExtra("file_name");
        file_type = getIntent().getStringExtra("type");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
      //  String frameVideo = "<html><body>Youtube video .. <br> <iframe width=\"320\" height=\"315\" src=\"https://www.youtube.com/embed/lY2H2ZP56K4\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

        Log.d(TAG, "brochure_file: " + myList);
     if(!myList.isEmpty()){
         data = myList.get(0);
     }

        Log.d(TAG, "onCreate: "+data);

        view.setWebViewClient(new AppWebViewClients());
        view.getSettings().setUseWideViewPort(true);
        view.getSettings().setJavaScriptEnabled(true);
        if (file_type.equals("image") ) {
            String imgSrcHtml = "<html><img src='" + file_name + "' /></html>";
            view.loadData(imgSrcHtml, "text/html", "UTF-8");
        }
        else if (file_type.equals("video")) {
            rel_viewpager.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.VISIBLE);

            try {


                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                Uri videoURI = Uri.parse(data);

                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);
            }catch (Exception e){
                Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
            }






        }



        //

        view.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
            }
        });
    }
    public class AppWebViewClients extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
    }
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
    private void setViewPager( ArrayList<String> list){

        tab_layout.setupWithViewPager(viewPager, true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), list);
        viewPager.setAdapter(viewPagerAdapter);

    }

}