package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.NewsAdapter;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    RecyclerView recycle_game,recycle_news;
    LinearLayout ll_chat,ll_profile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        recycle_game=findViewById(R.id.recycle_game);
        recycle_news=findViewById(R.id.recycle_news);
        ll_chat=findViewById(R.id.llchat);

        recycle_game.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycle_news.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
        ll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChatScreen.class));
            }
        });

    }
}
