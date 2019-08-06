package com.sport.supernathral.activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.sport.supernathral.Fragment.CommentGame;
import com.sport.supernathral.Fragment.Comments;
import com.sport.supernathral.Fragment.ScoreGame;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;

import java.util.ArrayList;
import java.util.List;

public class GameListActivity extends AppCompatActivity {
    String TAG="GameListActivity";
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    GlobalClass globalClass;
    String id,game_id,team_1_name,team_2_name,team_1_image,team_2_image,live_score_team_A,live_score_team_B,match_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }
        globalClass=(GlobalClass)getApplicationContext();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);



        game_id=getIntent().getStringExtra("game_id");
        team_1_name=getIntent().getStringExtra("team_1_name");
        team_2_name=getIntent().getStringExtra("team_2_name");
        team_1_image=getIntent().getStringExtra("team_1_image");
        team_2_image=getIntent().getStringExtra("team_2_image");
        live_score_team_A=getIntent().getStringExtra("live_score_team_A");
        live_score_team_B=getIntent().getStringExtra("live_score_team_B");
        match_type=getIntent().getStringExtra("match_type");
        Log.d(TAG, "onCreate: "+game_id);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        globalClass.setGame_id(game_id);




    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();

        bundle.putString("game_id", game_id);
        bundle.putString("team_1_name", team_1_name);
        bundle.putString("team_2_name", team_2_name);
        bundle.putString("team_2_image", team_2_image);
        bundle.putString("team_1_image", team_1_image);
        bundle.putString("live_score_team_A", live_score_team_A);
        bundle.putString("live_score_team_B", live_score_team_B);
        bundle.putString("match_type", match_type);
        GameListActivity.ViewPagerAdapter adapter = new GameListActivity.ViewPagerAdapter(getSupportFragmentManager());

        ScoreGame scoreGame = new ScoreGame();
        scoreGame.setArguments(bundle);

        CommentGame comments = new CommentGame();
        comments.setArguments(bundle);

        adapter.addFragment(scoreGame,"Score");
        adapter.addFragment(comments, "Comments");

        viewPager.setAdapter(adapter);
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