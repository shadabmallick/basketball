package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.sport.supernathral.AdapterClass.AdpterStudentList;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    Toolbar toolbar;
    AdpterStudentList adpterStudentList;
    ArrayList<String> newsList;
    RecyclerView recycle_student_list;
    String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(StudentListActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(StudentListActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        from =  getIntent().getStringExtra("from");
        Log.d(TAG, "onCreate: "+from);
        initView();
        initialisation();

    }
    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

    }

    private void initialisation() {

        recycle_student_list = findViewById(R.id.recycle_student_list_new);

        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");


        recycle_student_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adpterStudentList
                = new AdpterStudentList(getApplicationContext(), newsList,from);
        recycle_student_list.setAdapter(adpterStudentList);


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