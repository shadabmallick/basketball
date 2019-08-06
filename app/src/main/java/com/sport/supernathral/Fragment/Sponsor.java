package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sport.supernathral.AdapterClass.AdapterEvent;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Sponsor   extends Fragment {

    String TAG=" About";
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    TextView tv_about,tv_name,tv_designation;
    Toolbar toolbar;
    LinearLayout ll_txt_about;
    ImageView profile_image;
    String name,image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sponsor, container, false);
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

        toolbar =view. findViewById(R.id.toolbar);
        ll_txt_about = view. findViewById(R.id.ll_txt_about);
        tv_name = view. findViewById(R.id.tv_name);
        tv_designation =view.  findViewById(R.id.tv_designation);
        profile_image = view. findViewById(R.id.profile_image);
        tv_name.setText(globalClass.getFname());
        tv_designation.setText("Player");
        if (!globalClass.getProfil_pic().equals("")) {
            Picasso.with(getActivity()).load(globalClass.getProfil_pic()).placeholder(R.mipmap.avatar_gray).into(profile_image);
        }



    }
}
