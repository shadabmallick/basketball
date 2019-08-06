package com.sport.supernathral.activity;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;


import com.sport.supernathral.Fragment.CommentGame;
import com.sport.supernathral.Fragment.Comments;
import com.sport.supernathral.Fragment.ResumeActivity;
import com.sport.supernathral.Fragment.ScoreGame;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.List;

public class NewsSublist extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    GlobalClass globalClass;

    String from="",id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsublist);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }
          id=getIntent().getStringExtra("id");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        globalClass = (GlobalClass) getApplicationContext();
        globalClass.setSingle_top_news_id("");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String single_top_news_id = bundle.getString("id");
            globalClass.setSingle_top_news_id(single_top_news_id);

            from = bundle.getString("from","");
        }


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);





    }

    private void setupViewPager(final ViewPager viewPager) {
        Bundle bundle = new Bundle();

        bundle.putString("news_id", id);
        ResumeActivity scoreGame = new ResumeActivity();
        scoreGame.setArguments(bundle);
        NewsSublist.ViewPagerAdapter adapter = new NewsSublist.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(scoreGame, "Resume");
        adapter.addFragment(new Comments(), "Comments");

        viewPager.setAdapter(adapter);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (from.equals("home comment")){
                    viewPager.setCurrentItem(1);
                }

            }
        }, 1000);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}