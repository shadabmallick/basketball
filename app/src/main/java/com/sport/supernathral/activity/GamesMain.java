package com.sport.supernathral.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.DrawerListAdapter;
import com.sport.supernathral.DataModel.DrawerItem;
import com.sport.supernathral.Fragment.Attendence;
import com.sport.supernathral.Fragment.Event;
import com.sport.supernathral.Fragment.GameFragment;
import com.sport.supernathral.Fragment.Games;
import com.sport.supernathral.Fragment.Info;
import com.sport.supernathral.Fragment.Notes;
import com.sport.supernathral.Fragment.Schedule;
import com.sport.supernathral.Fragment.ScheduleUserwise;
import com.sport.supernathral.Fragment.SkillSet;
import com.sport.supernathral.Fragment.Sponsor;
import com.sport.supernathral.Fragment.Statistics;
import com.sport.supernathral.Fragment.StudentList;
import com.sport.supernathral.Fragment.Team;
import com.sport.supernathral.Fragment.Team_All;
import com.sport.supernathral.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class GamesMain extends AppCompatActivity
        implements DrawerListAdapter.onItemClickListner {

    DrawerLayout drawer;
    RecyclerView nav_drawer_recycler_view;
    TextView toolbar_title;
    CircleImageView iv_user;
    RelativeLayout rel_profile;


    private FragmentManager mFragmentManager;
    Toolbar toolbar;

    ArrayList<DrawerItem> drawerItemArrayList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }




        initViews();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Games");



    }


    private void initViews(){

        toolbar  = findViewById(R.id.toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),
                R.mipmap.drawar_menu, getApplicationContext().getTheme());
        toggle.setHomeAsUpIndicator(drawable);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });



        NavigationView navigationView = findViewById(R.id.nav_view);

        nav_drawer_recycler_view = findViewById(R.id.nav_drawer_recycler_view);
        nav_drawer_recycler_view.setLayoutManager(new LinearLayoutManager(this));


        mFragmentManager = getSupportFragmentManager();



        initNavigationItems();

       // initFooterItems();



        toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Games");

        transactFragment(new GameFragment());






        /// recycler view data

       /* ArrayList<GameData> gameDataArrayList = new ArrayList<>();
        GameData gameData = new GameData();
        gameDataArrayList.add(gameData);
        gameDataArrayList.add(gameData);


        ArrayList<HashMap<String,ArrayList<GameData>>> listMain = new ArrayList<>();

        HashMap<String,ArrayList<GameData>> map = new HashMap<>();
        map.put("Foregoing", gameDataArrayList);
        listMain.add(map);

        map = new HashMap<>();
        map.put("Ongoing", gameDataArrayList);
        listMain.add(map);

        map = new HashMap<>();
        map.put("Upcoming", gameDataArrayList);
        listMain.add(map);


        ArrayList<ListItem> mItems = new ArrayList<>();

        for (HashMap<String,ArrayList<GameData>> map1 : listMain){

            for (String string : map1.keySet()) {

                HeaderItem header = new HeaderItem();
                header.setHeader(string);
                mItems.add(header);

                for (GameData gameData1 : map1.get(string)) {
                    GameItem item = new GameItem();
                    item.setGameData(gameData1);
                    mItems.add(item);
                }

            }

        }


        GameListAdapter gameListAdapter = new GameListAdapter(GamesMain.this,
                mItems);
        recycle_game.setAdapter(gameListAdapter);
*/


    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
            return;

        }
    }


    @Override
    protected void onResume() {

        super.onResume();
    }


    public void initNavigationItems(){

        String[] some_array = getResources().getStringArray(R.array.game_drawer_items);

        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.mipmap.game_gray);
        myImageList.add(R.mipmap.event_gray);
        myImageList.add(R.mipmap.schedule_gray);
        myImageList.add(R.mipmap.attendance_gray);
        myImageList.add(R.mipmap.skill_gray);
        myImageList.add(R.mipmap.profile_info_gray);
        myImageList.add(R.mipmap.statistics_gray);
        myImageList.add(R.mipmap.sponsor_gray);
        myImageList.add(R.mipmap.notes_gray);

        drawerItemArrayList = new ArrayList<>();
        DrawerItem drawerItem;

        for (int i = 0; i < some_array.length; i++){
            drawerItem = new DrawerItem();

            drawerItem.setImgResID(myImageList.get(i));
            drawerItem.setTitle(some_array[i]);
            drawerItemArrayList.add(drawerItem);

        }


        DrawerListAdapter drawerListAdapter =
                new DrawerListAdapter(GamesMain.this, drawerItemArrayList, this);
        nav_drawer_recycler_view.setAdapter(drawerListAdapter);

    }


    @Override
    public void onItemClick(int position) {

        Intent intent = null;

        switch (position){

            case 0:
                toolbar_title.setText(getResources().getString(R.string.games));

                //transaction=getSupportFragmentManager().beginTransaction();
               // transaction.replace(R.id.flContent,new Games());
                //changeActivity(intent);

                transactFragment(new GameFragment());

                break;


            case 1:

                toolbar_title.setText(getResources().getString(R.string.events));

                //transaction=getSupportFragmentManager().beginTransaction();
                //transaction.replace(R.id.flContent,new Event());

                transactFragment(new Event());

                break;

            case 2:
                toolbar_title.setText(getResources().getString(R.string.schedule));
                Bundle bundle_schedule = new Bundle();
                bundle_schedule.putString("from", getResources().getString(R.string.schedule));
                ScheduleUserwise scchedule_list = new ScheduleUserwise();
                scchedule_list.setArguments( bundle_schedule);

                transactFragment(scchedule_list);


                break;

            case 3:
                toolbar_title.setText(getResources().getString(R.string.attendance));

                transactFragment(new Team());

                break;

            case 4:

                toolbar_title.setText(getResources().getString(R.string.student_list));

                Bundle bundle_student_list = new Bundle();
                bundle_student_list.putString("from", getResources().getString(R.string.student_list));
                Team_All student_list = new Team_All();
                student_list.setArguments( bundle_student_list);

                transactFragment(student_list);

                break;

            case 5:

                toolbar_title.setText(getResources().getString(R.string.info));

                Bundle bundle_info = new Bundle();
                bundle_info.putString("from", getResources().getString(R.string.info));
                Team_All info = new Team_All();
                info.setArguments(bundle_info);

                transactFragment(info);

                break;

            case 6:

                toolbar_title.setText(getResources().getString(R.string.statistics));

                Bundle bundle_statistics = new Bundle();
                bundle_statistics.putString("from", getResources().getString(R.string.statistics));
                Team_All statistics = new Team_All();
                statistics.setArguments(bundle_statistics);

                transactFragment(statistics);

                break;

            case 7:

                toolbar_title.setText(getResources().getString(R.string.sponsor));

                Bundle bundle = new Bundle();
                bundle.putString("from", getResources().getString(R.string.sponsor));
                Team_All team_all = new Team_All();
                team_all.setArguments(bundle);

                transactFragment(team_all);

                break;

            case 8:

                toolbar_title.setText(getResources().getString(R.string.notes));
                Bundle bundle_notes = new Bundle();
                bundle_notes.putString("from", getResources().getString(R.string.notes));
                Team_All team_all_notes = new Team_All();
                team_all_notes.setArguments(bundle_notes);

                transactFragment(team_all_notes);

                break;

        }


    }


    private void changeActivity(final Intent intent){

        if (intent != null){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                  startActivity(intent);

                }
            }, 300);


        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                drawer.closeDrawer(GravityCompat.START);

            }
        }, 150);

    }


    private void transactFragment(final Fragment fragment){

        if (fragment != null) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    FragmentTransaction ft = mFragmentManager.beginTransaction();
                    ft.replace(R.id.flContent, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

                }
            }, 300);

        }

        drawer.closeDrawer(GravityCompat.START);

    }










}

