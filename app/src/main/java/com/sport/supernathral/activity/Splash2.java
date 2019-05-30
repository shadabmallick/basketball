package com.sport.supernathral.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sport.supernathral.R;

public class Splash2 extends AppCompatActivity {

    Button btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash2);
        initViews();


    }


    private void initViews(){

        btn_sign_up = findViewById(R.id.btn_sign_up);



        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }



}
