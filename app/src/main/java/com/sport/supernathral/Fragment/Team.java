package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.AdapterTeam;

import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Team  extends Fragment {


    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG = "product";
    ArrayList<String> newsList;
    ImageView img_header;
    AdapterTeam adapterNotes;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    RecyclerView recycle_notes;
    TextView toolbar_title,tv_date_select;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attendence_team, container, false);
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
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = simpleDateFormat.format(calendar.getTime());

        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        tv_date_select=view.findViewById(R.id.tv_date_select);
        toolbar_title.setText(getResources().getString(R.string.team));

        img_header = view.findViewById(R.id.img_header);
        recycle_notes = view.findViewById(R.id.recycle_date);
        tv_date_select.setText(date);
        //rv_category = view.findViewById(R.id.recycler_chat);
        newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");

        int numberOfColumns = 2;
        recycle_notes.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapterNotes
                = new AdapterTeam(getActivity(), newsList);
        recycle_notes.setAdapter(adapterNotes);


    }
}
