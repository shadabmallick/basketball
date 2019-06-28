package com.sport.supernathral.Fragment;


/* Develop by Shadab on 28/06/2019*/

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;

public class Schedule extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    RecyclerView recycle_event;
    AdapterEvent adapterEvent;

    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setMessage("Loading...");
        initialisation(view);
        // function();


        return view;
    }

    private void initialisation(View view) {

        img_header = view.findViewById(R.id.img_header);


    }
}
