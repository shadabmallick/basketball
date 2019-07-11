package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sport.supernathral.AdapterClass.GameListAdapter;
import com.sport.supernathral.DataModel.GameData;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GameItem;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.HeaderItem;
import com.sport.supernathral.Utils.ListItem;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ChatScreen;
import com.sport.supernathral.activity.EventScreen;
import com.sport.supernathral.activity.GamesMain;
import com.sport.supernathral.activity.HomePage;
import com.sport.supernathral.activity.ProfileScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class Games extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    RecyclerView recycle_game;
    ImageView img_header;
    LinearLayout llnews;
    LinearLayout llchat ;
    LinearLayout ll_games;
            LinearLayout ll_event;
    LinearLayout ll_profile;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        recycle_game = view.findViewById(R.id.recycle_game);
        recycle_game.setLayoutManager(new LinearLayoutManager(getActivity()));


        pd.setMessage("Loading...");
        initialisation(view);
        initFooterItems();



        return view;
    }

    private void initialisation(View view) {

        img_header = view.findViewById(R.id.img_header);
        recycle_game = view.findViewById(R.id.recycle_game);
        llnews = view.findViewById(R.id.llnews);
        llchat = view.findViewById(R.id.llchat);
        ll_games = view.findViewById(R.id.ll_games);
        ll_event =view. findViewById(R.id.ll_event);
        ll_profile =view. findViewById(R.id.ll_profile);
        ArrayList<GameData> gameDataArrayList = new ArrayList<>();
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


        GameListAdapter gameListAdapter = new GameListAdapter(getActivity(),
                mItems);
        recycle_game.setAdapter(gameListAdapter);

    }
    private void initFooterItems(){




        llnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomePage.class));
            }
        });


        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ChatScreen.class));
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), EventScreen.class));

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ProfileScreen.class));

            }
        });


    }

}
