package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Chats extends Fragment {

    RecyclerView rv_category;

    String TAG="product";
    AdapterChat adapterChat;
    ArrayList<String> newsList;

    ArrayList<HashMap<String,String>> list_names;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_chats, container, false);

        initialisation(view);
        function();



        return view;
    }

    private void initialisation(View view) {

        rv_category = view.findViewById(R.id.recycler_chat);

    }

    private void function() {
        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");

        rv_category.setLayoutManager(new LinearLayoutManager(getActivity()));
         adapterChat
                = new AdapterChat(getActivity(), newsList);
        rv_category.setAdapter(adapterChat);







    }



}