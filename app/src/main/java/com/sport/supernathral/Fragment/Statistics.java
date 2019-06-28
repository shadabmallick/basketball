package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import java.util.ArrayList;
import java.util.Collections;

public class Statistics  extends Fragment {


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
        View view = inflater.inflate(R.layout.statistics, container, false);
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



        BarChart barChart = (BarChart)view. findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(10f, 1));


        BarDataSet bardataset = new BarDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");
        labels.add("");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("");  // set the description

        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.deep_yellow)));

        barChart.animateY(5000);
    }
}
