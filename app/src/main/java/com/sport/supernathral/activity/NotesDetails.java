package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;




public class NotesDetails extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about,tv_date,tv_notes;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    String note,date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_details);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(NotesDetails.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(NotesDetails.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        note=getIntent().getStringExtra("note");
        date=getIntent().getStringExtra("date");
        initView();
        tv_date.setText(date);
        tv_notes.setText(note);

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        tv_date = findViewById(R.id.tv_date);
        tv_notes = findViewById(R.id.tv_notes);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tv_about=findViewById(R.id.about_us);
       // About();

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
