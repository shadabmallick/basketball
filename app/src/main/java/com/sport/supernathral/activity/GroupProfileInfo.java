package com.sport.supernathral.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.GroupMembersAdapter;
import com.sport.supernathral.AdapterClass.UserSelectionAdapter;
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.R;

import java.util.ArrayList;

public class GroupProfileInfo extends AppCompatActivity
        implements GroupMembersAdapter.onItemClickListner {

    RelativeLayout rel_back, rel_select_pic, rel_update, rel_add_member, rel_delete_group;
    EditText edt_group_name;
    ImageView iv_edit;
    RecyclerView recycler_members, recycler_users;
    RelativeLayout rel_members_view, rel_add_more, rel_close_dialog;
    TextView tv_group_name;


    ArrayList<MembersData> membersDataArrayList;
    GroupMembersAdapter groupMembersAdapter;
    UserSelectionAdapter userSelectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_profile_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        initViews();




    }


    private void initViews(){

        rel_back = findViewById(R.id.rel_back);
        rel_select_pic = findViewById(R.id.rel_select_pic);
        rel_update = findViewById(R.id.rel_update);
        rel_add_member = findViewById(R.id.rel_add_member);
        rel_delete_group = findViewById(R.id.rel_delete_group);
        edt_group_name = findViewById(R.id.edt_group_name);
        iv_edit = findViewById(R.id.iv_edit);
        recycler_members = findViewById(R.id.recycler_members);

        rel_members_view = findViewById(R.id.rel_members_view);
        rel_add_more = findViewById(R.id.rel_add_more);
        rel_close_dialog = findViewById(R.id.rel_close_dialog);
        recycler_users = findViewById(R.id.recycler_users);
        tv_group_name = findViewById(R.id.tv_group_name);
        rel_members_view.setVisibility(View.GONE);
        rel_members_view.getBackground().setAlpha(120);

        recycler_members.setLayoutManager(new LinearLayoutManager(this));
        recycler_users.setLayoutManager(new LinearLayoutManager(this));


        membersDataArrayList = new ArrayList<>();
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());
        membersDataArrayList.add(new MembersData());

        groupMembersAdapter = new GroupMembersAdapter(GroupProfileInfo.this,
                membersDataArrayList, this);
        recycler_members.setAdapter(groupMembersAdapter);



        rel_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rel_members_view.setVisibility(View.VISIBLE);

                setNewUsers();
            }
        });
        rel_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_members_view.setVisibility(View.GONE);
            }
        });



    }

    @Override
    public void onItemClick(MembersData membersData) {

    }


    private void setNewUsers(){

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");
        arrayList.add("A");

        userSelectionAdapter = new UserSelectionAdapter(GroupProfileInfo.this,
                arrayList);
        recycler_users.setAdapter(userSelectionAdapter);
    }

}