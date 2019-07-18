package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleProfileInfo extends AppCompatActivity {

    RelativeLayout rel_back, rl_message;
    ImageView iv_profile;
    TextView tv_name, tv_designation, tv_full_name, tv_location, tv_email, tv_designation2;
    CircleImageView iv_user;

    ChatListData chatListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        initViews();



    }


    private void initViews(){

        rel_back = findViewById(R.id.rel_back);
        rl_message = findViewById(R.id.rl_message);
        iv_profile = findViewById(R.id.iv_profile);
        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_location = findViewById(R.id.tv_location);
        tv_email = findViewById(R.id.tv_email);
        tv_designation2 = findViewById(R.id.tv_designation2);
        iv_user = findViewById(R.id.iv_user);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            chatListData = (ChatListData) bundle.getSerializable("info");

            if (chatListData != null){

                if (!chatListData.getUser_image().isEmpty()){
                    Picasso.with(this)
                            .load(chatListData.getUser_image())
                            .placeholder(R.mipmap.profile_placeholder)
                            .into(iv_profile);

                    Picasso.with(this)
                            .load(chatListData.getUser_image())
                            .placeholder(R.mipmap.profile_placeholder)
                            .into(iv_user);
                }


                tv_name.setText(chatListData.getUser_name());
                tv_full_name.setText(chatListData.getUser_name());
                tv_designation.setText(chatListData.getUser_type());
                tv_designation2.setText(chatListData.getUser_type());

                tv_location.setText(chatListData.getUser_type());
                tv_email.setText(chatListData.getUser_type());



            }



        }



        rel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }








}