package com.sport.supernathral.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GameAdapter;
import com.sport.supernathral.AdapterClass.PlayerAdapter;
import com.sport.supernathral.AdapterClass.SponsorAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.SEARCH_USER;
import static com.sport.supernathral.NetworkConstant.AppConfig.SPONSOR;

public class Sponsor extends AppCompatActivity {
    String TAG="Sponsor";
    Toolbar toolbar;
    RecyclerView recyclerSponsor;
    GlobalClass globalClass;
    Shared_Preference preference;
    ArrayList<HashMap<String,String>> sponsor;
    ProgressDialog pd;
    SponsorAdapter notificationListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponser_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }


        globalClass=(GlobalClass)getApplicationContext();
        preference = new Shared_Preference(Sponsor.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(Sponsor.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        recyclerSponsor = findViewById(R.id.recycler_sponsor);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


        getSponsor();
    }

    public void getSponsor(){

         sponsor=new ArrayList<>();
      /*  ArrayList<String> newsList = new ArrayList<>();
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
        newsList.add("A");
*/
        int numberOfColumns = 2;
        recyclerSponsor.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
      /*  SponsorAdapter notificationListAdapter
                = new SponsorAdapter(getApplicationContext(), newsList);*/
       // recyclerSponsor.setAdapter(notificationListAdapter);

        Sponsor();
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

    private void Sponsor() {


        String tag_string_req = "forget_password";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                SPONSOR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null){
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");
                        Log.d(TAG, "status: "+status);

                        if (status == 1){




                            JSONArray news_data=main_object.getJSONArray("data");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String image = item.get("image").toString().replaceAll("\"", "");
                                String name = item.get("name").toString().replaceAll("\"", "");
                                String website = item.get("website").toString().replaceAll("\"", "");
                                String descerption = item.get("descerption").toString().replaceAll("\"", "");
                                String contact_name = item.get("contact_name").toString().replaceAll("\"", "");
                                String contact_designation = item.get("contact_designation").toString().replaceAll("\"", "");
                                String contact_email = item.get("contact_email").toString().replaceAll("\"", "");
                                String contact_phone = item.get("contact_phone").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", id);
                                hashMap.put("image", image);
                                hashMap.put("name", name);
                                hashMap.put("website", website);
                                hashMap.put("descerption", descerption);
                                hashMap.put("contact_name", contact_name);
                                hashMap.put("contact_designation", contact_designation);
                                hashMap.put("contact_email", contact_email);
                                hashMap.put("contact_phone", contact_phone);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);



                                //  globalClass.setFolderanme(folder_name);

                                sponsor.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            notificationListAdapter   = new SponsorAdapter(Sponsor.this,sponsor);
                            recyclerSponsor.setAdapter(notificationListAdapter);
                            notificationListAdapter.notifyDataSetChanged();



                        }else {


                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(getApplicationContext(), "Connection error",
                                FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        e.printStackTrace();

                    }

                }

            }


        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                pd.dismiss();
            }
        }) {


        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


}
