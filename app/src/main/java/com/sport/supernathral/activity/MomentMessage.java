package com.sport.supernathral.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.sport.supernathral.AdapterClass.OnlyImageAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.ScalableVideoView;
import com.sport.supernathral.trimmer.TrimmerActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import life.knowledge4.videotrimmer.utils.FileUtils;

public class MomentMessage extends AppCompatActivity {

    String TAG = "MomentMessage";
    Toolbar toolbar;
    ImageView img_back, img_add_image;
    RecyclerView recycler_images;
    ScalableVideoView video_view;
    RelativeLayout rel_videoview;
    TextView tv_cancel;

    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 222;
    private static final int VIDEO_REQUEST = 1888;
    public static final int TRIMMER_ACTIVITY_RESULT_CODE = 2006;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        toolbar = findViewById(R.id.toolbar);
        img_back = findViewById(R.id.img_back);
        img_add_image = findViewById(R.id.img_add_image);
        recycler_images = findViewById(R.id.recycler_images);
        video_view = findViewById(R.id.video_view);
        tv_cancel = findViewById(R.id.tv_cancel);
        rel_videoview = findViewById(R.id.rel_videoview);
        rel_videoview.setVisibility(View.GONE);
        recycler_images.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


        img_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rel_videoview.setVisibility(View.GONE);
                video_view.stopPlayback();
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


    private boolean checkPermission() {

        List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(MomentMessage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MomentMessage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MomentMessage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) MomentMessage.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

            return false;
        } else {

            selectImageOrVideo();

        }


        return true;
    }


    public void selectImageOrVideo() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MomentMessage.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_video_select, null);
        dialogBuilder.setView(dialogView);


        LinearLayout linear_images = dialogView.findViewById(R.id.linear_images);
        LinearLayout linear_video = dialogView.findViewById(R.id.linear_video);

        final AlertDialog alertDialog = dialogBuilder.create();

        linear_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MomentMessage.this,
                        AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 9); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

                alertDialog.dismiss();

            }
        });


        linear_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setTypeAndNormalize("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Video"), VIDEO_REQUEST);



                alertDialog.dismiss();

            }
        });


        alertDialog.show();
    }



    ArrayList<Uri> uriArrayList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "requestCode - " +requestCode);



        if (requestCode == ConstantsCustomGallery.REQUEST_CODE &&
                resultCode == Activity.RESULT_OK && data != null) {

            uriArrayList = new ArrayList<>();

            ArrayList<Image> imageArrayList = data.getParcelableArrayListExtra(
                    ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            for (int i = 0; i < imageArrayList.size(); i++) {
                Uri uri = Uri.fromFile(new File(imageArrayList.get(i).path));
                uriArrayList.add(uri);
            }

            Log.d(TAG, "image len - " +imageArrayList.size());


            setImagesAdapter();

            rel_videoview.setVisibility(View.GONE);
            recycler_images.setVisibility(View.VISIBLE);

        }else if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK){

            Uri video_uri = data.getData();

            MediaPlayer mp = MediaPlayer.create(this, video_uri);
            int duration = mp.getDuration();
            mp.release();

            Log.d("TAG", "/////*******************/////");
            Log.d("TAG", "duration = "+duration);


            if (duration > 10){
                String filemanagerstring = video_uri.getPath();
                Log.d("TAG", "path = "+filemanagerstring);
                Intent intent = new Intent(MomentMessage.this, TrimmerActivity.class);
                intent.putExtra("path", FileUtils.getPath(this, video_uri));
                startActivityForResult(intent, TRIMMER_ACTIVITY_RESULT_CODE);

            }else {

                setVideo(video_uri);
            }

        } else if (requestCode == TRIMMER_ACTIVITY_RESULT_CODE && resultCode == RESULT_OK){

            String path_uri = data.getExtras().getString("path");
            Uri myUri = Uri.parse(path_uri);

            File auxFile = new File(myUri.getPath());

            MediaPlayer mp = MediaPlayer.create(this, myUri);
            int duration = mp.getDuration();
            mp.release();

            long length = auxFile.length();
            Log.d("TAG", "/////*******************/////");
            Log.d("TAG", "length = "+length);
            Log.d("TAG", "duration = "+duration);

            setVideo(myUri);
        }
    }


    private void setImagesAdapter(){

        recycler_images.setLayoutManager(new LinearLayoutManager(this,
                        LinearLayoutManager.HORIZONTAL, false));

        OnlyImageAdapter onlyImageAdapter = new OnlyImageAdapter(MomentMessage.this, uriArrayList);
        recycler_images.setAdapter(onlyImageAdapter);

    }

    private void setVideo(final Uri uri){

        rel_videoview.setVisibility(View.VISIBLE);
        recycler_images.setVisibility(View.GONE);

        video_view.setVideoURI(uri);

        video_view.setMediaController(null);
        video_view.requestFocus();
        video_view.start();

        video_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               // mp.setVolume(0, 0);
            }
        });

        video_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                video_view.setVideoURI(uri);
                video_view.requestFocus();
                video_view.start();
            }
        });

        video_view.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "setOnErrorListener ");
                return true;
            }
        });


    }

}
