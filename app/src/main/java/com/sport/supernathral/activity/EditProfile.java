package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;

public class EditProfile extends AppCompatActivity {

    TextView tv_name, tv_designation, tv_email, tv_post, tv_full_name, tv_country;
    ImageView profile_image,img_edit;
    RelativeLayout rel_edit_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }


        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_email = findViewById(R.id.tv_email);
        tv_post = findViewById(R.id.tv_post);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_country = findViewById(R.id.tv_country);
        profile_image = findViewById(R.id.profile_image);
        rel_edit_profile = findViewById(R.id.rel_edit_profile);
        // img_edit = findViewById(R.id.img_edit);


        initFooterItems();

        rel_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


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


            }
        });

    }
}