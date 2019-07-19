package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sport.supernathral.AdapterClass.UserSelectionAdapter;
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class GroupUserSelection extends AppCompatActivity {

    Toolbar toolbar;
    ImageView iv_goto_group;
    RecyclerView recycler_user;

    UserSelectionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_user_selection_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        toolbar = findViewById(R.id.toolbar);
        iv_goto_group = findViewById(R.id.iv_goto_group);
        recycler_user = findViewById(R.id.recycler_user);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);



        iv_goto_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter != null && adapter.getSelectedUserIds().size() > 0){

                    Intent intent =
                            new Intent(getApplicationContext(), GroupCreate.class);
                    intent.putExtra("data", adapter.getSelectedUserIds());
                    startActivity(intent);

                }else {


                }
            }
        });




        recycler_user.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MembersData> arrayList = new ArrayList<>();

        arrayList.add(new MembersData());
        arrayList.add(new MembersData());
        arrayList.add(new MembersData());
        arrayList.add(new MembersData());
        arrayList.add(new MembersData());

        adapter = new UserSelectionAdapter(GroupUserSelection.this,
                arrayList);
        recycler_user.setAdapter(adapter);


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