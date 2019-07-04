package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.AdapterSchduleUserwise;
import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.AdapterClass.AdapterTeam;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    AdapterSchduleUserwise adapterNotes;
    ArrayList<String> newsList;
    RecyclerView recycle_student_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendence_team_datewise);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(AttendanceActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(AttendanceActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initView();
        initialisation();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        ll_txt_about = findViewById(R.id.ll_txt_about);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        tv_about=findViewById(R.id.about_us);

    }

    private void initialisation() {

        recycle_student_list = findViewById(R.id.recycle_student_list);

        //rv_category = view.findViewById(R.id.recycler_chat);
        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");


        recycle_student_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterNotes
                = new AdapterSchduleUserwise(getApplicationContext(), newsList);
        recycle_student_list.setAdapter(adapterNotes);


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