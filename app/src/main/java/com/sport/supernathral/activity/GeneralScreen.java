package com.sport.supernathral.activity;

  import android.app.ProgressDialog;
  import android.content.Intent;
  import android.os.Build;
  import android.os.Bundle;
  import android.support.v4.content.ContextCompat;
  import android.support.v7.app.AppCompatActivity;
  import android.support.v7.widget.Toolbar;
  import android.util.Log;
  import android.view.MenuItem;
  import android.view.View;
  import android.view.Window;
  import android.view.WindowManager;
  import android.widget.ImageView;
  import android.widget.LinearLayout;
  import android.widget.RelativeLayout;
  import android.widget.TextView;


  import com.android.volley.DefaultRetryPolicy;
  import com.android.volley.Request;
  import com.android.volley.Response;
  import com.android.volley.VolleyError;
  import com.android.volley.toolbox.StringRequest;
  import com.shashank.sony.fancytoastlib.FancyToast;
  import com.sport.supernathral.R;
  import com.sport.supernathral.Utils.GlobalClass;
  import com.sport.supernathral.Utils.Shared_Preference;
  import com.squareup.picasso.Picasso;

  import org.json.JSONObject;

  import java.util.HashMap;
  import java.util.Map;



public class GeneralScreen extends AppCompatActivity {
    String TAG="General Setting";
    TextView tv_name, tv_designation, tv_email, tv_post, tv_full_name, tv_country;
    ImageView profile_image,img_edit;
    RelativeLayout rel_edit_profile;
    Toolbar toolbar;
    GlobalClass globalClass;
    Shared_Preference preference;
    ProgressDialog pd;
    String id,unique_id,main_access_group_id,sub_access_group_id,image,type,name
            ,emailid,password,contact_no,skill,fax,dob,description,notes,
            primary_contact_name,primary_contact_email,primary_contact_phone,secondary_contact_name,secondary_contact_email,
    secondary_contact_phone,added_by,child_ids,sponsor_ids,device_type,device_id
    ,activation_token,first_login,otp_code,latitude,longitude,location,admin_approved,qa,
            notification,delete_flag,is_active,report_block,entry_date,modified_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_general);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }
        preference = new Shared_Preference(GeneralScreen.this);
        preference.loadPrefrence();
        pd = new ProgressDialog(GeneralScreen.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");

        /*  Data coming from Profile Screen  */

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




        Log.d(TAG, "onCreate: "+id);
        tv_name = findViewById(R.id.tv_name);
        tv_designation = findViewById(R.id.tv_designation);
        tv_email = findViewById(R.id.tv_email);
        tv_post = findViewById(R.id.tv_post);
        tv_full_name = findViewById(R.id.tv_full_name);
        tv_country = findViewById(R.id.tv_country);
        profile_image = findViewById(R.id.profile_image);
        rel_edit_profile = findViewById(R.id.rel_edit_profile);
       // img_edit = findViewById(R.id.img_edit);

        Picasso.with(getApplicationContext()).load(image).placeholder(R.mipmap.avatar_gray).into(profile_image);
        tv_name.setText(name);
        if(type.equals("Coach/Teachers")){
            tv_designation.setText("Trainer");

        }
        tv_full_name.setText(name);
        tv_country.setText(location);
        tv_email.setText(emailid);


        if(type.equals("Coach/Teachers")){
            tv_post.setText("Trainer");

        }

        initFooterItems();
      //  Profile();

        rel_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent editProfile=new Intent(getApplicationContext(),EditProfile.class);
                editProfile.putExtra("id", id);
                editProfile.putExtra("unique_id", unique_id);
                editProfile.putExtra("main_access_group_id", main_access_group_id);
                editProfile.putExtra("sub_access_group_id", sub_access_group_id);
                editProfile.putExtra("image", image);
                editProfile.putExtra("type", type);
                editProfile.putExtra("name", name);
                editProfile.putExtra("emailid", emailid);
                editProfile.putExtra("password", password);
                editProfile.putExtra("contact_no", contact_no);
                editProfile.putExtra("skill", skill);
                editProfile.putExtra("fax", fax);
                editProfile.putExtra("dob", dob);
                editProfile.putExtra("description", description);
                editProfile.putExtra("notes", notes);
                editProfile.putExtra("primary_contact_name", primary_contact_name);
                editProfile.putExtra("primary_contact_email", primary_contact_email);
                editProfile.putExtra("primary_contact_phone", primary_contact_phone);
                editProfile.putExtra("secondary_contact_name", secondary_contact_name);
                editProfile.putExtra("secondary_contact_email", secondary_contact_email);
                editProfile.putExtra("secondary_contact_phone", secondary_contact_phone);
                editProfile.putExtra("added_by", added_by);
                editProfile.putExtra("child_ids", child_ids);
                editProfile.putExtra("sponsor_ids", sponsor_ids);
                editProfile.putExtra("device_type", device_type);
                editProfile.putExtra("device_id", device_id);
                editProfile.putExtra("activation_token", activation_token);
                editProfile.putExtra("first_login", first_login);
                editProfile.putExtra("otp_code", otp_code);
                editProfile.putExtra("latitude", latitude);
                editProfile.putExtra("longitude", longitude);
                editProfile.putExtra("location", location);
                editProfile.putExtra("admin_approved", admin_approved);
                editProfile.putExtra("qa", qa);
                editProfile.putExtra("notification", notification);
                editProfile.putExtra("delete_flag", delete_flag);
                editProfile.putExtra("is_active", is_active);
                editProfile.putExtra("report_block", report_block);
                editProfile.putExtra("entry_date", entry_date);
                editProfile.putExtra("modified_date", modified_date);
                startActivity(editProfile);

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

}