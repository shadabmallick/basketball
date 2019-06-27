package com.sport.supernathral.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.sport.supernathral.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class ChatImageFull extends AppCompatActivity {

    ImageView back,imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_img_full);

        imageView = findViewById(R.id.img_full_screen);
        back = findViewById(R.id.back_full);


        if (getIntent().getStringExtra("key").matches("web")){

            Picasso.with(this)
                    .load(getIntent().getStringExtra("img"))
                    .error(R.mipmap.camera_grey)
                    .into( imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError() {
                        }
                    });

        }else if (getIntent().getStringExtra("key").matches("local")) {

            imageView.setImageURI(Uri.parse(getIntent().getStringExtra("img")));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
