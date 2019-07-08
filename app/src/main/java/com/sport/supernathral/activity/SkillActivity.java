package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.AdapterSkill;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;

public class SkillActivity  extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    ArrayList<String> newsList;

    RecyclerView recycle_skill;
    AdapterSkill adapterEvent;
    TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skill_set);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(SkillActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(SkillActivity.this);
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
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.skill_set));

        recycle_skill = findViewById(R.id.recycle_skill);

        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");

        recycle_skill.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterEvent
                = new AdapterSkill(getApplicationContext(), newsList);
        recycle_skill.setAdapter(adapterEvent);

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