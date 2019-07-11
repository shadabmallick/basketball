package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sport.supernathral.AdapterClass.AdapterNotesDetails;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;

public class NotesActivity  extends AppCompatActivity {
    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    Toolbar toolbar;

    AdapterNotesDetails adapterNotesDetails;
    ArrayList<String> newsList;
    RecyclerView recyle_notes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);
        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(NotesActivity.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(NotesActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
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

        recyle_notes = findViewById(R.id.recyle_notes);

        //rv_category = view.findViewById(R.id.recycler_chat);
        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");


        recyle_notes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterNotesDetails
                = new AdapterNotesDetails(NotesActivity.this, newsList);
        recyle_notes.setAdapter(adapterNotesDetails);


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