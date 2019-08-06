package com.sport.supernathral.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class EditProfile extends AppCompatActivity implements LocationListener {
    String TAG="Edit Profile";
    TextView tv_name, tv_designation, tv_email, tv_post, tv_full_name, tv_country;
    ImageView profile_image,img_edit,iv_eye;
    RelativeLayout rel_edit_profile,rl_submit;
    GlobalClass globalClass;
    Toolbar toolbar;
    boolean password_visible = true;
    EditText edt_password,tv_location;
    ProgressDialog pd;
    Shared_Preference sharedpreference;
    File p_image;
    double lati;
    double longi;
    String location_full,newAddress;
    String id,unique_id,main_access_group_id,sub_access_group_id,image,type,name
            ,emailid,password,contact_no,skill,fax,dob,description,notes,
            primary_contact_name,primary_contact_email,primary_contact_phone,secondary_contact_name,secondary_contact_email,
            secondary_contact_phone,added_by,child_ids,sponsor_ids,device_type,device_id
            ,activation_token,first_login,otp_code,latitude,longitude,location,admin_approved,qa,
            notification,delete_flag,is_active,report_block,entry_date,modified_date;

    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(EditProfile.this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EditProfile.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            }
            else{
                if(checkForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 124)){

                }

            }
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);
        globalClass = (GlobalClass)getApplicationContext();
        sharedpreference = new Shared_Preference(EditProfile.this);
        sharedpreference.loadPrefrence();
        pd=new ProgressDialog(EditProfile.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_email = findViewById(R.id.tv_mail);
        tv_post = findViewById(R.id.tv_post);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_country = findViewById(R.id.tv_country);
        profile_image = findViewById(R.id.profile_image);
        rel_edit_profile = findViewById(R.id.rel_edit_profile);
        iv_eye = findViewById(R.id.iv_eye);
        edt_password = findViewById(R.id.edt_password);
        tv_location = findViewById(R.id.tv_location);
        rl_submit = findViewById(R.id.rl_submit);
        // img_edit = findViewById(R.id.img_edit);

        AccessValue();
        initFooterItems();



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
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=tv_full_name.getText().toString().trim();
                String location=tv_location.getText().toString().trim();
                updateProfile(name,location);
            }
        });


    }

    private void AccessValue() {
        id =  getIntent().getStringExtra("id");
        unique_id =  getIntent().getStringExtra("unique_id");
        main_access_group_id =  getIntent().getStringExtra("main_access_group_id");
        sub_access_group_id =  getIntent().getStringExtra("sub_access_group_id");
        image =  getIntent().getStringExtra("image");
        type =  getIntent().getStringExtra("type");
        name =  getIntent().getStringExtra("name");
        emailid =  getIntent().getStringExtra("emailid");
        password =  getIntent().getStringExtra("password");
        contact_no =  getIntent().getStringExtra("contact_no");
        skill =  getIntent().getStringExtra("skill");
        fax =  getIntent().getStringExtra("fax");
        dob =  getIntent().getStringExtra("dob");
        description =  getIntent().getStringExtra("description");
        notes =  getIntent().getStringExtra("notes");
        primary_contact_name =  getIntent().getStringExtra("primary_contact_name");
        primary_contact_email =  getIntent().getStringExtra("primary_contact_email");
        primary_contact_phone =  getIntent().getStringExtra("primary_contact_phone");
        secondary_contact_name =  getIntent().getStringExtra("secondary_contact_name");
        secondary_contact_email =  getIntent().getStringExtra("secondary_contact_email");
        secondary_contact_phone =  getIntent().getStringExtra("secondary_contact_phone");
        added_by =  getIntent().getStringExtra("added_by");
        child_ids =  getIntent().getStringExtra("child_ids");
        sponsor_ids =  getIntent().getStringExtra("sponsor_ids");
        device_type =  getIntent().getStringExtra("device_type");
        device_id =  getIntent().getStringExtra("device_id");
        activation_token =  getIntent().getStringExtra("activation_token");
        first_login =  getIntent().getStringExtra("first_login");
        otp_code =  getIntent().getStringExtra("otp_code");
        latitude =  getIntent().getStringExtra("latitude");
        longitude =  getIntent().getStringExtra("longitude");
        location =  getIntent().getStringExtra("location");
        admin_approved =  getIntent().getStringExtra("admin_approved");
        qa =  getIntent().getStringExtra("qa");
        notification =  getIntent().getStringExtra("notification");
        delete_flag =  getIntent().getStringExtra("delete_flag");
        is_active =  getIntent().getStringExtra("is_active");
        report_block =  getIntent().getStringExtra("report_block");
        entry_date =  getIntent().getStringExtra("entry_date");
        modified_date =  getIntent().getStringExtra("modified_date");
        tv_full_name.setText(name);
        tv_email.setText(emailid);
        edt_password.setText(password);
        tv_location.setText(location);
        Picasso.with(getApplicationContext()).load(image).placeholder(R.mipmap.profile_placeholder).into(profile_image);





    }

    public boolean checkForPermission(final String[] permissions, final int permRequestCode) {

        final List<String> permissionsNeeded = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            final String perm = permissions[i];
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(EditProfile.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {

                    Log.d("permisssion","not granted");


                    if (shouldShowRequestPermissionRationale(permissions[i])) {

                        Log.d("if","if");
                        permissionsNeeded.add(perm);

                    } else {
                        // add the request.
                        Log.d("else","else");
                        permissionsNeeded.add(perm);
                    }

                }
            }
        }

        if (permissionsNeeded.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // go ahead and request permissions
                requestPermissions(permissionsNeeded.toArray(new String[permissionsNeeded.size()]), permRequestCode);
            }
            return false;
        } else {
            // no permission need to be asked so all good...we have them all.
            return true;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            p_image = new File(getRealPathFromURI(uri));


            Log.d(TAG, "image = "+p_image);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {


            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }


            try {
                Bitmap bitmap;
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

/*
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                        bitmapOptions);*/

                Log.d(TAG, "bitmap: "+bitmap);

                profile_image.setImageBitmap(bitmap);

                String path = Environment.getExternalStorageDirectory()+File.separator;
                // + File.separator
                //   + "Phoenix" + File.separator + "default";
                f.delete();
                OutputStream outFile = null;
                File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                try {

                    p_image = file;

                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                    outFile.flush();
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // iv_product_image.setImageBitmap(photo);
        }
        if(globalClass.isNetworkAvailable()){
            // user_profile_pic_update_url();
        }else{
            FancyToast.makeText(getApplicationContext(), "Please check your internet connection", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();        }

    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private void selectImage() {
        try {
            PackageManager pm = getPackageManager();
            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(EditProfile.this);
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
                        } else if (options[item].equals("Choose From Gallery")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            } else
                Toast.makeText(EditProfile.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(EditProfile.this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void updateProfile(final String name,final String location){

        pd.show();

        String url = AppConfig.USER_PROFILE_UPDATE;
        AsyncHttpClient cl = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        params.put("user_id", globalClass.getId());
        params.put("name",name);
        params.put("latitude", lati);
        params.put("longitude", longi);
        params.put("location", location);

        try{

            params.put("profile_pic", p_image);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        Log.d(TAG , "URL "+url);
        Log.d(TAG , "params "+params.toString());


        int DEFAULT_TIMEOUT = 30 * 1000;
        cl.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);
        cl.post(url,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (response != null) {
                    Log.d(TAG, "user_profile_pic_update- " + response.toString());
                    try {



                        int status = response.getInt("status");
                        String message = response.getString("message");

                        if (status == 1) {



                            JSONObject data = response.getJSONObject("data");

                            final String id = data.get("id").toString().replaceAll("\"", "");
                            final String unique_id = data.get("unique_id").toString().replaceAll("\"", "");
                            final String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                            final String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                            final String image = data.get("image").toString().replaceAll("\"", "");
                            final String type = data.get("type").toString().replaceAll("\"", "");
                            final String name = data.get("name").toString().replaceAll("\"", "");
                            final String emailid = data.get("emailid").toString().replaceAll("\"", "");
                            final String password = data.get("password").toString().replaceAll("\"", "");
                            final String contact_no = data.get("contact_no").toString().replaceAll("\"", "");
                            final String skill = data.get("skill").toString().replaceAll("\"", "");
                            final String fax = data.get("fax").toString().replaceAll("\"", "");
                            final String dob = data.get("dob").toString().replaceAll("\"", "");
                            final String description = data.get("description").toString().replaceAll("\"", "");
                            final String notes = data.get("notes").toString().replaceAll("\"", "");
                            final String primary_contact_name = data.get("primary_contact_name").toString().replaceAll("\"", "");
                            final String primary_contact_email = data.get("primary_contact_email").toString().replaceAll("\"", "");
                            final String primary_contact_phone = data.get("primary_contact_phone").toString().replaceAll("\"", "");
                            final String secondary_contact_name = data.get("secondary_contact_name").toString().replaceAll("\"", "");
                            final String secondary_contact_email = data.get("secondary_contact_email").toString().replaceAll("\"", "");
                            final String secondary_contact_phone = data.get("secondary_contact_phone").toString().replaceAll("\"", "");
                            final String added_by = data.get("added_by").toString().replaceAll("\"", "");
                            final String child_ids = data.get("child_ids").toString().replaceAll("\"", "");
                            final String sponsor_ids = data.get("sponsor_ids").toString().replaceAll("\"", "");
                            final String device_type = data.get("device_type").toString().replaceAll("\"", "");
                            final String device_id = data.get("device_id").toString().replaceAll("\"", "");
                            final String activation_token = data.get("activation_token").toString().replaceAll("\"", "");
                            final String first_login = data.get("first_login").toString().replaceAll("\"", "");
                            final String otp_code = data.get("otp_code").toString().replaceAll("\"", "");
                            final String latitude = data.get("latitude").toString().replaceAll("\"", "");
                            final String longitude = data.get("longitude").toString().replaceAll("\"", "");
                            final String location = data.get("location").toString().replaceAll("\"", "");
                            final String admin_approved = data.get("admin_approved").toString().replaceAll("\"", "");
                            final String qa = data.get("qa").toString().replaceAll("\"", "");
                            final String notification = data.get("notification").toString().replaceAll("\"", "");
                            final String delete_flag = data.get("delete_flag").toString().replaceAll("\"", "");
                            final String is_active = data.get("is_active").toString().replaceAll("\"", "");
                            final String report_block = data.get("report_block").toString().replaceAll("\"", "");
                            final String entry_date = data.get("entry_date").toString().replaceAll("\"", "");
                            final String modified_date = data.get("modified_date").toString().replaceAll("\"", "");

                            globalClass.setId(id);
                            globalClass.setEmail(emailid);
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
                            globalClass.setProfil_pic(image);
                            sharedpreference.savePrefrence();
                              Intent profile=new Intent(EditProfile.this,ProfileScreen.class);
                            profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(profile);

                            FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();




                        }else{

                            FancyToast.makeText(getApplicationContext(), message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                        }

                        pd.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                 pd.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });


    }



    private void initFooterItems(){

        LinearLayout llnews = findViewById(R.id.llnews);
        LinearLayout llchat = findViewById(R.id.llchat);
        LinearLayout ll_games = findViewById(R.id.ll_games);
        LinearLayout ll_event = findViewById(R.id.ll_event);
        LinearLayout ll_profile = findViewById(R.id.ll_profile);


        llnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomePage.class));
            }
        });

        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChatScreen.class));
            }
        });


        ll_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), GamesMain.class));
            }
        });


        ll_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), EventScreen.class));

            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        Toast.makeText(EditProfile.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}