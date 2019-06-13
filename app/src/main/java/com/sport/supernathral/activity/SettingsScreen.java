package com.sport.supernathral.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

public class SettingsScreen extends AppCompatActivity {

    TextView tv_change_password, tv_about, tv_sign_out;
    Switch switch_noti;
    ImageView iv_back;
    GlobalClass globalClass;
    Shared_Preference prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }


        prefManager = new Shared_Preference(this);
        globalClass = (GlobalClass) getApplicationContext();
        prefManager.loadPrefrence();

        tv_change_password = findViewById(R.id.tv_change_password);
        tv_about = findViewById(R.id.tv_about);
        tv_sign_out = findViewById(R.id.tv_sign_out);
        switch_noti = findViewById(R.id.switch_noti);
        iv_back = findViewById(R.id.iv_back);



        tv_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogLogout();

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



    }


    private void dialogLogout(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        TextView tv_yes = dialogView.findViewById(R.id.tv_yes);
        TextView tv_no = dialogView.findViewById(R.id.tv_no);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsScreen.this, LoginScreen.class);
                prefManager.clearPrefrence();
                globalClass.setLogin_status(false);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });


    }




}