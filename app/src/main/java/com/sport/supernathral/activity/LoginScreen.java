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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;

import org.json.JSONObject;

import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import me.pushy.sdk.Pushy;
import me.pushy.sdk.util.exceptions.PushyException;

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
    String deviceToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Pushy.listen(this);
        checkPermission1();
        marshmallowGPSPremissionCheck();

        globalClass = (GlobalClass) getApplicationContext();
        shared_preference = new Shared_Preference(LoginScreen.this);
        shared_preference.loadPrefrence();
        pd = new ProgressDialog(LoginScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");
        initViews();



        try {
            deviceToken = Pushy.register(getApplicationContext());
        }catch (PushyException e){
            e.printStackTrace();
        }

        new RegisterForPushNotificationsAsync().execute();



        try {
            doTrustToCertificates();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void checkPermission1(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
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

                Intent intent = new Intent(LoginScreen.this, OtpActivity.class);
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

                            /*Intent intent = new Intent(LoginScreen.this, HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/


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
                LOGIN, new Response.Listener<String>() {

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


                            String res_user_id = data.optString("id");
                            String unique_id = data.optString("unique_id");
                            String main_access_group_id = data.optString("main_access_group_id");
                            String sub_access_group_id = data.optString("sub_access_group_id");
                            String type = data.optString("type");
                            String name = data.optString("name");
                            String email = data.optString("email");
                            String first_login = data.optString("first_login");
                            String notification = data.optString("notification");
                            String device_type = data.optString("device_type");
                            String device_id = data.optString("device_id");
                            String latitude = data.optString("latitude");
                            String longitude = data.optString("longitude");
                            String location = data.optString("location");
                            String profile_pic = data.optString("profile_pic");

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

                            Log.d(TAG, "onResponse: "+globalClass.getMain_access_group_id());


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
                params.put("device_token", deviceToken);

                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }



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

    public void doTrustToCertificates() throws Exception {
        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] certs,
                                                   String authType) throws CertificateException {
                        return;
                    }

                    public void checkClientTrusted(X509Certificate[] certs,
                                                   String authType) throws CertificateException {
                        return;
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
                    System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");
                }
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }





    public class RegisterForPushNotificationsAsync extends AsyncTask<Void, Void, Exception> {
        protected Exception doInBackground(Void... params) {
            try {
                String token = Pushy.register(getApplicationContext());
                deviceToken = token;
                Log.d("MyApp", "Pushy >> device token: " + deviceToken);
            }
            catch (Exception exc) {
                return exc;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception exc) {
            // Failed?
            if (exc != null) {
                return;
            }
        }
    }
}
