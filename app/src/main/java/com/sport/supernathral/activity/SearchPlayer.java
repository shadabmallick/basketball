package com.sport.supernathral.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.PlayerAdapter;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class SearchPlayer extends AppCompatActivity {
    RecyclerView recycleList;
    LinearLayout ll_search,ll_main;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);



        initViews();
        recycleList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycleList.setNestedScrollingEnabled(false);
    }

    private void initViews() {
        recycleList=findViewById(R.id.recycleList);
        ll_search=findViewById(R.id.ll_search);
        ll_main=findViewById(R.id.ll_main);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_main.setVisibility(View.GONE);
                recycleList.setVisibility(View.VISIBLE);
                ArrayList<String> newsList = new ArrayList<>();
                newsList.add("A");
                newsList.add("A");
                newsList.add("A");
                newsList.add("A");

                PlayerAdapter notificationListAdapter
                        = new PlayerAdapter(getApplicationContext(), newsList);
                recycleList.setAdapter(notificationListAdapter);
            }
        });
    }
}


