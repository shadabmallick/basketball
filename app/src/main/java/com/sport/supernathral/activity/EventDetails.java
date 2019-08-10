package com.sport.supernathral.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sport.supernathral.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetails extends AppCompatActivity {
    Toolbar toolbar;
    Date date;
    String newString;
    TextView tv_title,tv_date_start,tv_date_end,tv_time,tv_venue,tv_address,tv_description,
            tv_contact_person,tv_contact_email,tv_phone_end;
    ImageView top_img,ongoing,upcoming,complete, iv_location;
    String name,event_start_date,event_end_date,event_venue,image,main_access_group_id,
            event_address,event_city,event_state,event_pincode,event_desc,event_contact_name,
            event_contact_designation,event_contact_email,event_contact_phone,
            delete_flag,is_active,entry_date,modified_date,eventType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        name=getIntent().getStringExtra("name");
        event_start_date=getIntent().getStringExtra("event_start_date");
        event_end_date=getIntent().getStringExtra("event_end_date");
        event_venue=getIntent().getStringExtra("event_venue");
        image=getIntent().getStringExtra("image");
        main_access_group_id=getIntent().getStringExtra("main_access_group_id");
        event_address=getIntent().getStringExtra("event_address");
        event_city=getIntent().getStringExtra("event_city");
        event_state=getIntent().getStringExtra("event_state");
        event_pincode=getIntent().getStringExtra("event_pincode");
        event_desc=getIntent().getStringExtra("event_desc");
        event_contact_name=getIntent().getStringExtra("event_contact_name");
        event_contact_designation=getIntent().getStringExtra("event_contact_designation");
        event_contact_designation=getIntent().getStringExtra("event_contact_designation");
        event_contact_email=getIntent().getStringExtra("event_contact_email");
        event_contact_phone=getIntent().getStringExtra("event_contact_phone");
        delete_flag=getIntent().getStringExtra("delete_flag");
        is_active=getIntent().getStringExtra("is_active");
        entry_date=getIntent().getStringExtra("entry_date");
        modified_date= getIntent().getStringExtra("modified_date");
        eventType= getIntent().getStringExtra("eventType");

        initView();
    }

    public void initView(){
        toolbar = findViewById(R.id.toolbar);
        top_img = findViewById(R.id.top_img);
        ongoing = findViewById(R.id.ongoing);
        upcoming = findViewById(R.id.upcoming);
        complete = findViewById(R.id.complete);
        tv_title = findViewById(R.id.tv_title);
        tv_date_start = findViewById(R.id.tv_date_start);
        tv_date_end = findViewById(R.id.tv_date_end);
        tv_time = findViewById(R.id.tv_time);
        tv_venue = findViewById(R.id.tv_venue);
        tv_address = findViewById(R.id.tv_address);
        tv_description = findViewById(R.id.tv_description);
        tv_contact_person = findViewById(R.id.tv_contact_person);
        tv_contact_email = findViewById(R.id.tv_contact_email);
        tv_phone_end = findViewById(R.id.tv_phone_end);
        iv_location = findViewById(R.id.iv_location);


        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(event_start_date);

            newString  = new SimpleDateFormat("HH:mm").format(date);
        }
        catch (ParseException e) {
            //Handle exception here
            e.printStackTrace();
        }        if (!image.isEmpty()) {
            Picasso.with(getApplicationContext())
                    .load(image)
                    .placeholder(R.mipmap.icon4)
                    .into(top_img);
        }
        if(eventType.equals("Past")){
            complete.setVisibility(View.VISIBLE);
            ongoing.setVisibility(View.GONE);
            upcoming.setVisibility(View.GONE);
        }
        else if(eventType.equals("Ongoing")){
            complete.setVisibility(View.GONE);
            ongoing.setVisibility(View.VISIBLE);
            upcoming.setVisibility(View.GONE);
        }
        else if(eventType.equals("Upcoming")){
            complete.setVisibility(View.GONE);
            ongoing.setVisibility(View.GONE);
            upcoming.setVisibility(View.VISIBLE);
        }

      tv_title.setText(name);
        tv_date_start.setText(event_start_date);
        tv_date_end.setText(event_end_date);
        tv_venue.setText(event_venue);
        tv_address.setText(event_address);
        tv_description.setText(event_desc);
        tv_contact_person.setText(event_contact_name);
        tv_contact_email.setText(event_contact_email);
        tv_phone_end.setText(event_contact_phone);
        tv_time.setText(newString);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);



        iv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EventDetails.this, BingMapActivity.class);
                intent.putExtra("event_venue", event_venue);
                intent.putExtra("event_address", event_address);
                intent.putExtra("event_venue_chinese", getIntent().getStringExtra("event_venue_chinese"));
                intent.putExtra("event_address_chinese", getIntent().getStringExtra("event_address_chinese"));
                startActivity(intent);

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
