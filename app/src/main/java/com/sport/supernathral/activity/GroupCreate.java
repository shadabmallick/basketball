package com.sport.supernathral.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GroupUserAdapter;
import com.sport.supernathral.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupCreate extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_user;
    GroupUserAdapter groupUserAdapter;
    RelativeLayout rel_create_group;
    EditText edt_group_name;
    CircleImageView iv_image;

    Bitmap resized_bitmap;
    File p_image1;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 333;


    String TAG = "group create";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        toolbar = findViewById(R.id.toolbar);
        recycler_user = findViewById(R.id.recycler_user);
        rel_create_group = findViewById(R.id.rel_create_group);
        edt_group_name = findViewById(R.id.edt_group_name);
        iv_image = findViewById(R.id.iv_image);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_black);


        recycler_user.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            ArrayList<String> list = (ArrayList<String>) bundle.getSerializable("data");

            groupUserAdapter = new GroupUserAdapter(this, list);
            recycler_user.setAdapter(groupUserAdapter);
        }



        rel_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_group_name.getText().toString().trim().isEmpty()){

                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.msg_blank_group),
                            FancyToast.LENGTH_LONG,
                            FancyToast.INFO, false).show();

                }else {

                    Intent intent = new Intent(GroupCreate.this,
                            ChatGroup.class);
                    intent.putExtra("from", "create");
                    intent.putExtra("id", "8");
                    intent.putExtra("name", edt_group_name.getText().toString());

                    if (p_image1 != null){
                        intent.putExtra("photo", p_image1.getAbsolutePath());
                    }

                    startActivity(intent);

                }

            }
        });

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermission();
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

        if (ContextCompat.checkSelfPermission(GroupCreate.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(GroupCreate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(GroupCreate.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) GroupCreate.this, permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

            return false;
        } else {

            selectImage();

        }


        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (permissions.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        (permissions.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                    //list is still empty
                    selectImage();
                } else {
                    checkPermission();
                    // Permission Denied

                }
                break;
        }
    }

    public void selectImage() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GroupCreate.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_picture_select, null);
        dialogBuilder.setView(dialogView);

        ImageView iv_gallery = dialogView.findViewById(R.id.iv_gallery);
        ImageView iv_camera = dialogView.findViewById(R.id.iv_camera);

        final AlertDialog alertDialog = dialogBuilder.create();

        iv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        PICK_IMAGE_REQUEST);

                alertDialog.dismiss();

            }
        });


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                alertDialog.dismiss();

            }
        });


        alertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        p_image1 = null;

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG , "PICK_IMAGE_REQUEST - "+uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                createFileFromBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            createFileFromBitmap(photo);
            Log.d(TAG , "CAMERA_REQUEST - "+data.getExtras().get("data"));

        }

    }


    private void createFileFromBitmap(Bitmap bitmap){

        try {
            //InputStream inputStream = getContentResolver().openInputStream(uri);
            // Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            //if (inputStream != null) inputStream.close();
            resized_bitmap = getResizedBitmap(bitmap, 480);
        }catch (Exception e){
            e.printStackTrace();
        }


        long time = 0;
        time =  System.currentTimeMillis();
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Supernathral/Images";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        final File image_file = new File(dir, "sup" + time+ ".jpg");

        p_image1 = image_file;

        OutputStream os;
        try {
            os = new FileOutputStream(image_file);
            resized_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
            os.flush();
            os.close();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(image_file)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_image.setImageBitmap(resized_bitmap);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        if (width < maxSize && height < maxSize){

            return image;

        }else {

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            return Bitmap.createScaledBitmap(image, width, height, true);
        }

    }







}