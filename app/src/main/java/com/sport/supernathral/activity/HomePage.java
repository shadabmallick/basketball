package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.NewsAdapter;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    RecyclerView recycle_game,recycle_news;
    ImageView img_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }



        recycle_game = findViewById(R.id.recycle_game);
        recycle_news = findViewById(R.id.recycle_news);
        img_search = findViewById(R.id.img_search);


        recycle_game.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle_news.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle_game.setNestedScrollingEnabled(false);
        recycle_news.setNestedScrollingEnabled(false);



        ArrayList<String> gamelist = new ArrayList<>();
        gamelist.add("A");
        ArrayList<String> newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        GameAdapter notificationListAdapter
                = new GameAdapter(getApplicationContext(), gamelist);
        recycle_game.setAdapter(notificationListAdapter);


        NewsAdapter newsAdapter
                = new NewsAdapter(getApplicationContext(), newsList);
        recycle_news.setAdapter(newsAdapter);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchPLayer=new Intent(getApplicationContext(),SearchPlayer.class);
                startActivity(searchPLayer);

            }
        });


        initFooterItems();


    }



    private void initFooterItems(){

        LinearLayout llnews = findViewById(R.id.llnews);
        LinearLayout llchat = findViewById(R.id.llchat);
        LinearLayout ll_games = findViewById(R.id.ll_games);
        LinearLayout ll_event = findViewById(R.id.ll_event);
        LinearLayout ll_profile = findViewById(R.id.ll_profile);


        llnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });


        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ChatScreen.class));
            }
        });

        ll_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), GamesMain.class));
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), EventScreen.class));
            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), ProfileScreen.class));
            }
        });

    }
}
