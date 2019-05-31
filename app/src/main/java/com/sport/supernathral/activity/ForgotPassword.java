package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.R;

public class ForgotPassword extends AppCompatActivity {
    TextView tv_forgetPass;
    Toolbar toolbar;
    LinearLayout linear_entry_email, linear_otp;
    RelativeLayout rl_submit, rl_save;
    ImageView iv_eye, iv_eye_confirm;
    EditText edt_password, edt_confirm_password;

    boolean password_visible = true;
    boolean password_visible2 = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initViews();

    }

    public void initViews(){

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        linear_entry_email = findViewById(R.id.linear_entry_email);
        linear_otp = findViewById(R.id.linear_otp);
        rl_submit = findViewById(R.id.rl_submit);
        rl_save = findViewById(R.id.rl_save);

        linear_entry_email.setVisibility(View.VISIBLE);
        linear_otp.setVisibility(View.GONE);

        tv_forgetPass=findViewById(R.id.tv_forgetPass);
        iv_eye=findViewById(R.id.iv_eye);
        iv_eye_confirm=findViewById(R.id.iv_eye_confirm);
        edt_password=findViewById(R.id.edt_password);
        edt_confirm_password=findViewById(R.id.edt_confirm_password);


        edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        buttonClick();

    }


    private void buttonClick(){

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_entry_email.setVisibility(View.GONE);
                linear_otp.setVisibility(View.VISIBLE);

            }
        });

        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible){

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.eye);

                    password_visible = true;

                }else {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.invisible);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());

            }
        });

        iv_eye_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible2){

                    edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye_confirm.setImageResource(R.mipmap.eye);

                    password_visible2 = true;

                }else {

                    edt_confirm_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye_confirm.setImageResource(R.mipmap.invisible);

                    password_visible2 = false;

                }

                edt_confirm_password.setSelection(edt_confirm_password.length());
            }
        });


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
