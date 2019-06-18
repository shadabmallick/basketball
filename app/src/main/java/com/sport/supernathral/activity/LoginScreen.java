package com.sport.supernathral.activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import static com.sport.supernathral.NetworkConstant.AppConfig.LOGIN;


public class LoginScreen extends AppCompatActivity implements LocationListener {
    String TAG = "Login Screen";
    TextView tv_forgetPass, tv_signup;
    ImageView iv_login, iv_eye;
    EditText edt_email, edt_password;
    RelativeLayout rl_login;
    GlobalClass globalClass;
    Shared_Preference shared_preference;
    ProgressDialog pd;
    String device_id;
    LocationManager locationManager;
    String location_full,newAddress;
    double lati;
    double longi;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_ENABLE_GPS = 516;
    boolean isGpsEnabled;

    Toolbar toolbar;
    boolean password_visible = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        marshmallowGPSPremissionCheck();

        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(LoginScreen.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(LoginScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initViews();


    }


    public void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        tv_forgetPass = findViewById(R.id.tv_forgetPass);
        iv_eye = findViewById(R.id.iv_eye);
        edt_password = findViewById(R.id.edt_password);
        tv_signup = findViewById(R.id.tv_signup);
        rl_login = findViewById(R.id.rl_login);
        edt_email = findViewById(R.id.edt_email);


        buttonClick();
    }
    private void openGpsEnableSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }
    private void buttonClick() {

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUp.class));
            }
        });

        tv_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginScreen.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_new = edt_email.getText().toString().trim();
                String password_new = edt_password.getText().toString().trim();
                if (globalClass.isNetworkAvailable()) {
                    if (!edt_email.getText().toString().isEmpty()) {

                        if (!edt_password.getText().toString().isEmpty()) {

                            login(email_new, password_new);

                        } else {

                            FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.password_empty), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                        }

                    } else {
                        FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.empty_email_name), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }
                } else {
                    FancyToast.makeText(getApplicationContext(), getResources().getString(R.string.check_network), FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                }
            }
        });




        edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        iv_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!password_visible) {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.invisible);

                    password_visible = true;

                } else {

                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_eye.setImageResource(R.mipmap.eye);

                    password_visible = false;

                }

                edt_password.setSelection(edt_password.length());
            }
        });

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
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (android.location.LocationListener) this);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!isGpsEnabled) {
                openGpsEnableSetting();
            }
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && LoginScreen.this.checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && LoginScreen.this.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            getLocation();
        }



    }
    private void login(final String user_email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        final String device_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+LOGIN, new Response.Listener<String>() {

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

                            FancyToast.makeText(getApplicationContext(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false)
                                    .show();

                            JSONObject data = main_object.getJSONObject("data");


                            String res_user_id = data.get("id").toString().replaceAll("\"", "");
                            String unique_id = data.get("unique_id").toString().replaceAll("\"", "");
                            String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                            String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                            String type = data.get("type").toString().replaceAll("\"", "");
                            String name = data.get("name").toString().replaceAll("\"", "");
                            String email = data.get("email").toString().replaceAll("\"", "");
                            String first_login = data.get("first_login").toString().replaceAll("\"", "");
                            String notification = data.get("notification").toString().replaceAll("\"", "");
                            String device_type = data.get("device_type").toString().replaceAll("\"", "");
                            String device_id = data.get("device_id").toString().replaceAll("\"", "");
                            String latitude = data.get("latitude").toString().replaceAll("\"", "");
                            String longitude = data.get("longitude").toString().replaceAll("\"", "");
                            String location = data.get("location").toString().replaceAll("\"", "");
                            String profile_pic = data.get("profile_pic").toString().replaceAll("\"", "");

                            globalClass.setId(res_user_id);
                            globalClass.setEmail(email);
                            globalClass.setFname(name);
                            globalClass.setUnique_name(unique_id);
                            globalClass.setLocation(location);
                            globalClass.setMain_access_group_id(main_access_group_id);
                            globalClass.setSub_access_group_id(sub_access_group_id);
                            globalClass.setType(type);
                            globalClass.setFirst_login(first_login);
                            globalClass.setNotification(notification);
                            globalClass.setLatitude(latitude);
                            globalClass.setLongitude(longitude);
                            globalClass.setDeviceid(device_type);
                            globalClass.setDeviceid(device_id);

                            globalClass.setProfil_pic(profile_pic);




                            globalClass.setLogin_status(true);

                            shared_preference.savePrefrence();
                            Intent intent = new Intent(LoginScreen.this, HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);



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

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("email", user_email);
                params.put("password", password);
                params.put("device_type", "android");
                params.put("device_id", device_id);
                params.put("latitude", String.valueOf(lati));
                params.put("longitude", String.valueOf(longi));
                params.put("location", newAddress);

                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }




/*
    private void checkLogin(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+LOGIN, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");
                    int result = Integer.parseInt(status);
                   // System.out.println(result);

                    Log.d(TAG, "Message: " + message);
                    Log.d(TAG, "Message: " + result);

                    if(result==1) {
                        JsonObject data = jobj.getAsJsonObject("data");
                        String res_user_id = data.get("id").toString().replaceAll("\"", "");
                        String unique_id = data.get("unique_id").toString().replaceAll("\"", "");
                        String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                        String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                        String type = data.get("type").toString().replaceAll("\"", "");
                        String name = data.get("name").toString().replaceAll("\"", "");
                        String email = data.get("email").toString().replaceAll("\"", "");
                        String first_login = data.get("first_login").toString().replaceAll("\"", "");
                        String notification = data.get("notification").toString().replaceAll("\"", "");
                        String device_type = data.get("device_type").toString().replaceAll("\"", "");
                        String device_id = data.get("device_id").toString().replaceAll("\"", "");
                        String latitude = data.get("latitude").toString().replaceAll("\"", "");
                        String longitude = data.get("longitude").toString().replaceAll("\"", "");
                        String location = data.get("location").toString().replaceAll("\"", "");
                        String profile_pic = data.get("profile_pic").toString().replaceAll("\"", "");

                        globalClass.setId(res_user_id);
                        globalClass.setEmail(email);
                        globalClass.setFname(name);
                        globalClass.setUnique_name(unique_id);
                         globalClass.setLocation(location);
                         globalClass.setMain_access_group_id(main_access_group_id);
                         globalClass.setSub_access_group_id(sub_access_group_id);
                         globalClass.setType(type);
                         globalClass.setFirst_login(first_login);
                         globalClass.setNotification(notification);
                         globalClass.setLatitude(latitude);
                         globalClass.setLongitude(longitude);
                        globalClass.setDeviceid(device_type);
                        globalClass.setDeviceid(device_id);

                        globalClass.setProfil_pic(profile_pic);




                        globalClass.setLogin_status(true);

                        shared_preference.savePrefrence();
                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        Intent intent = new Intent(LoginScreen.this, HomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        pd.dismiss();


                    }
                    else {

                        FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }


                    Log.d(TAG, "Token \n" + message);


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("email", username);
                params.put("password", password);
                params.put("device_type", "android");
                params.put("device_id", device_id);
                params.put("latitude", String.valueOf(lati));
                params.put("longitude", String.valueOf(longi));
                params.put("location", newAddress);
                Log.d(TAG, "getParams: "+params);


                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));





    }
*/


    @Override
    public void onLocationChanged(Location location) {
        lati=location.getLatitude();
        longi=location.getLongitude();

        Log.d(TAG, "location_full: "+lati+""+longi);

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
           /* locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));*/
            location_full=addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2);
            Log.d(TAG, "onLocationChanged: "+location_full);
            newAddress=  addresses.get(0).getLocality();
            System.out.println(addresses.get(0).getLocality());

            Log.d(TAG, "newAddress: "+newAddress);
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(LoginScreen.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
