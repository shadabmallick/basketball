package com.sport.supernathral.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.GroupMembersAdapter;
import com.sport.supernathral.AdapterClass.UserSelectionAdapter;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.DataModel.MembersData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.Common;
import com.sport.supernathral.Utils.GlobalClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class GroupProfileInfo extends AppCompatActivity
        implements GroupMembersAdapter.onItemClickListner {

    String TAG = "groupInfo";
    RelativeLayout rel_back, rel_select_pic, rel_update, rel_add_member, rel_delete_group;
    EditText edt_group_name;
    ImageView iv_edit, group_image;
    RecyclerView recycler_members, recycler_users;
    RelativeLayout rel_members_view, rel_add_more, rel_close_dialog;
    TextView tv_group_name;


    String group_admin_id;
    ProgressDialog progressDialog;
    GlobalClass globalClass;

    File p_image1;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    public static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 333;


    ArrayList<MembersData> membersDataArrayList;
    GroupMembersAdapter groupMembersAdapter;
    UserSelectionAdapter userSelectionAdapter;

    ChatListData chatListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_profile_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.deep_yellow));
        }

        initViews();


    }


    private void initViews(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));

        globalClass = (GlobalClass) getApplicationContext();


        rel_back = findViewById(R.id.rel_back);
        rel_select_pic = findViewById(R.id.rel_select_pic);
        rel_update = findViewById(R.id.rel_update);
        rel_add_member = findViewById(R.id.rel_add_member);
        rel_delete_group = findViewById(R.id.rel_delete_group);
        edt_group_name = findViewById(R.id.edt_group_name);
        iv_edit = findViewById(R.id.iv_edit);
        recycler_members = findViewById(R.id.recycler_members);
        group_image = findViewById(R.id.group_image);

        rel_members_view = findViewById(R.id.rel_members_view);
        rel_add_more = findViewById(R.id.rel_add_more);
        rel_close_dialog = findViewById(R.id.rel_close_dialog);
        recycler_users = findViewById(R.id.recycler_users);
        tv_group_name = findViewById(R.id.tv_group_name);
        rel_members_view.setVisibility(View.GONE);
        rel_members_view.getBackground().setAlpha(120);

        recycler_members.setLayoutManager(new LinearLayoutManager(this));
        recycler_users.setLayoutManager(new LinearLayoutManager(this));


        edt_group_name.setEnabled(false);
        if (globalClass.isTrainer()){
            rel_delete_group.setVisibility(View.VISIBLE);
            rel_update.setVisibility(View.VISIBLE);
            rel_add_member.setVisibility(View.VISIBLE);
            rel_select_pic.setVisibility(View.VISIBLE);
            iv_edit.setVisibility(View.VISIBLE);
        }else {
            rel_delete_group.setVisibility(View.GONE);
            rel_update.setVisibility(View.GONE);
            rel_add_member.setVisibility(View.GONE);
            rel_select_pic.setVisibility(View.GONE);
            iv_edit.setVisibility(View.INVISIBLE);
        }


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            chatListData = (ChatListData) bundle.getSerializable("info");

            if (chatListData != null){

                getGroupProfileInfo(chatListData.getReceiver_id());

            }

        }


        rel_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUsers(chatListData.getReceiver_id());

            }
        });
        rel_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_members_view.setVisibility(View.GONE);
            }
        });
        rel_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rel_select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        rel_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_group_name.getText().toString().trim().isEmpty()){

                    FancyToast.makeText(getApplicationContext(),
                            getResources().getString(R.string.msg_blank_group),
                            FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();

                }else {

                    updateGroupProfile();
                }

            }
        });
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_group_name.setEnabled(true);
                edt_group_name.setText(edt_group_name.length());
            }
        });
        rel_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addParticipant(chatListData.getReceiver_id());

            }
        });
        rel_delete_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGroup(chatListData.getReceiver_id());
            }
        });

    }

    @Override
    public void onItemClick(MembersData membersData) {
        removeMemberFromGroup(membersData);
    }


    private void getGroupProfileInfo(final String group_id) {

        progressDialog.show();

        membersDataArrayList = new ArrayList<>();

        String tag_string_req = "group_details";

        String url = AppConfig.group_details;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "group_details Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        JSONObject data = main_object.getJSONObject("data");


                        if (!data.optString("group_image").isEmpty()){
                            Picasso.with(GroupProfileInfo.this)
                                    .load(data.optString("group_image"))
                                    .placeholder(R.mipmap.profile_placeholder)
                                    .into(group_image);
                        }

                        group_admin_id = data.optString("group_admin");

                        edt_group_name.setText(data.optString("group_name"));
                        tv_group_name.setText(data.optString("group_name"));


                      /*  if (group_admin_id.equals(globalClass.getId())){
                            rel_delete_group.setVisibility(View.VISIBLE);
                        }else {
                            rel_delete_group.setVisibility(View.GONE);
                        }*/



                        JSONArray group_member = data.getJSONArray("group_member");
                        for (int j = 0; j < group_member.length(); j++){
                            JSONObject object = group_member.getJSONObject(j);

                            MembersData membersData = new MembersData();
                            membersData.setId(object.optString("group_member"));
                            membersData.setUser_name(object.optString("user_name"));
                            membersData.setUser_image(object.optString("user_image"));
                            membersData.setUser_type(object.optString("user_type"));

                            membersDataArrayList.add(membersData);
                        }

                    }

                    setGroupMembersAdapter();



                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection Error", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("group_id", group_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }


    private void setGroupMembersAdapter(){

        groupMembersAdapter = new GroupMembersAdapter(GroupProfileInfo.this,
                membersDataArrayList, group_admin_id, this);
        recycler_members.setAdapter(groupMembersAdapter);

    }



    private boolean checkPermission() {

        List<String> permissionsList = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(GroupProfileInfo.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(GroupProfileInfo.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(GroupProfileInfo.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(Manifest.permission.CAMERA);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions((Activity) GroupProfileInfo.this,
                    permissionsList.toArray(new String[permissionsList.size()]),
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(GroupProfileInfo.this);
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
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
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
        //  IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        p_image1 = null;

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d(TAG , "PICK_IMAGE_REQUEST - "+uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                sendImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            sendImage(photo);
            Log.d(TAG , "CAMERA_REQUEST - "+data.getExtras().get("data"));

        }


    }


    public void sendImage(Bitmap bitmap) {

        try {
            //InputStream inputStream = getContentResolver().openInputStream(uri);
            // Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            //if (inputStream != null) inputStream.close();
            //resized_bitmap = getResizedBitmap(bitmap, 480);
        } catch (Exception e) {
            e.printStackTrace();
        }


        long time = 0;
        time = System.currentTimeMillis();
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/Supernathral/Images";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        final File image_file = new File(dir, "group" + time + ".jpg");

        p_image1 = image_file;

        OutputStream os;
        try {
            os = new FileOutputStream(image_file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            os.flush();
            os.close();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(image_file)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(p_image1.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(p_image1.getAbsolutePath());
            group_image.setImageBitmap(myBitmap);
        }


    }


    /// get group user for add participant ...

    private void getUsers(final String group_id) {

        progressDialog.show();

        String tag_string_req = "group_chat_list";

        String url = AppConfig.group_chat_list;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "group_chat_list Response: " + response);

                try {

                    ArrayList<MembersData> listNewMembers = new ArrayList<>();

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        JSONArray data = main_object.getJSONArray("data");
                        for (int j = 0; j < data.length(); j++){
                            JSONObject object = data.getJSONObject(j);

                            if (object.optString("group").equals("No")){

                                MembersData membersData = new MembersData();
                                membersData.setId(object.optString("receiver_id"));
                                membersData.setUser_name(object.optString("user_name"));
                                membersData.setUser_image(object.optString("user_image"));
                                membersData.setUser_type(object.optString("user_type"));

                                listNewMembers.add(membersData);

                            }

                        }

                    }

                    setNewUsers(listNewMembers);

                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection Error", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("group_id", group_id);
                params.put("user_id", globalClass.getId());

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    private void setNewUsers(ArrayList<MembersData> listNewMembers){

        userSelectionAdapter = new UserSelectionAdapter(GroupProfileInfo.this,
                listNewMembers);
        recycler_users.setAdapter(userSelectionAdapter);

        rel_members_view.setVisibility(View.VISIBLE);

    }

    private void addParticipant(final String group_id) {

        progressDialog.show();

        String tag_string_req = "add_group_member";

        String url = AppConfig.add_group_member;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "add_group_member Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        FancyToast.makeText(getApplicationContext(),
                                message, FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false).show();

                        rel_members_view.setVisibility(View.GONE);
                        getGroupProfileInfo(group_id);

                    }


                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection Error", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("group_id", group_id);
                params.put("member_id", userSelectionAdapter.getIds());

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    public void updateGroupProfile(){

        progressDialog.show();

        String url = AppConfig.update_group;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("group_name", edt_group_name.getText().toString());
        params.put("group_id", chatListData.getReceiver_id());

        if (p_image1 != null){

            try{

                params.put("group_image", p_image1);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }


        }

        Log.d(TAG ,"update_group- " + url);
        Log.d(TAG ,"update_group- " + params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        client.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);

        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(TAG, "update_group- " + response.toString());

                if (response != null) {
                    try {

                        int status = response.optInt("status");
                        String message = response.optString("message");

                        if (status == 1){

                            tv_group_name.setText(edt_group_name.getText().toString());

                        }else {


                        }

                        progressDialog.dismiss();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.d(TAG, "update_group- " + res);

                progressDialog.dismiss();

                android.app.AlertDialog alert =
                        new android.app.AlertDialog.Builder(GroupProfileInfo.this).create();
                alert.setMessage("Server Error");
                alert.show();
            }


        });

    }


    public void deleteGroup(String group_id){

        progressDialog.show();

        String url = AppConfig.group_delete;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("user_id", globalClass.getId());
        params.put("group_id", group_id);

        Log.d(TAG ,"group_delete- " + url);
        Log.d(TAG ,"group_delete- " + params.toString());

        int DEFAULT_TIMEOUT = 30 * 1000;
        client.setMaxRetriesAndTimeout(5 , DEFAULT_TIMEOUT);

        client.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(TAG, "group_delete- " + response.toString());

                if (response != null) {
                    try {

                        int status = response.optInt("status");
                        String message = response.optString("message");

                        if (status == 1){

                            finish();

                        }

                        progressDialog.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.d(TAG, "update_group- " + res);

                progressDialog.dismiss();

                android.app.AlertDialog alert =
                        new android.app.AlertDialog.Builder(GroupProfileInfo.this).create();
                alert.setMessage("Server Error");
                alert.show();
            }

        });

    }


    private void removeMemberFromGroup(final MembersData membersData) {

        progressDialog.show();

        String tag_string_req = "add_group_member";

        String url = AppConfig.delete_group_member;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "delete_group_member Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1){

                        FancyToast.makeText(getApplicationContext(),
                                message, FancyToast.LENGTH_SHORT,
                                FancyToast.SUCCESS, false).show();

                        getGroupProfileInfo(chatListData.getReceiver_id());

                    }


                } catch (Exception e) {

                    FancyToast.makeText(getApplicationContext(),
                            "Data Connection Error", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }

                progressDialog.dismiss();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("group_id", chatListData.getReceiver_id());
                params.put("member_id", membersData.getId());
                Log.d(TAG, "delete_group_member params: " + params);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }
}