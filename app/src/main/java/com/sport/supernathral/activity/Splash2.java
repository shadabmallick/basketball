package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sport.supernathral.R;

public class Splash2 extends AppCompatActivity {

    Button btn_sign_up;
    RelativeLayout rl_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash2);
        initViews();


    }


    private void initViews(){

        btn_sign_up = findViewById(R.id.btn_sign_up);
        rl_login=findViewById(R.id.rl_login);


        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash2.this, SignUp.class);
                startActivity(intent);

            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash2.this, LoginScreen.class);
                startActivity(intent);

            }
        });


    }



}
