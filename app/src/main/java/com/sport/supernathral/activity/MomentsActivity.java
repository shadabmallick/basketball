package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.AdapterClass.AdapterMoments;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class MomentsActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> newsList;
    RecyclerView recycler_moment;
    AdapterMoments adapterMoments;
    ImageView img_add;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moments_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        toolbar = findViewById(R.id.toolbar);
        img_add = findViewById(R.id.img_add);
        recycler_moment = findViewById(R.id.recycler_moment);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
     //   getSupportActionBar().setHomeAsUpIndicator(R.mipmap.add_white);
        initFooterItems();
        function();
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMessage= new Intent(getApplicationContext(),MomentMessage.class);
                startActivity(addMessage);
            }
        });

    }

    private void function() {
        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");

        recycler_moment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterMoments
                = new AdapterMoments(getApplicationContext(), newsList);
        recycler_moment.setAdapter(adapterMoments);







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
