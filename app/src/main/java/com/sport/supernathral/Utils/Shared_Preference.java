package com.sport.supernathral.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Shadab Mallick on 06/03/19.
 */

public class Shared_Preference {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;



    GlobalClass globalclass;
    private boolean pref_logInStatus;
    private String pref_name;
    private String pref_fname;
    private String pref_lname;
    private String pref_id;
    private String schoolname;
    private String pref_email;
    private String pref_phone_number;
    private String pref_order_id;
    private String pref_order_type;
    private String pref_business;
    private String pref_user_type;
    private String pref_profile_img;
    private String pref_shipping_address;
    private String pref_cart_no;
    private String pref_ship_address_id;
    private String pref_ship_full_address;
    private String pref_organisation;
    private  String unique_name;
    private  String main_access_group_id;
    private  String sub_access_group_id;
    private String type;
    private String first_login;
    private  String notification;
    private String latitude;
    private  String longitude;
    private  String location;
    private  String switch_notify;



    private String remote_user_id;

    private String fcm;
    private String login_from;


    private static final String PREFS_NAME = "preferences";
    private static final String PREFS_NAME2 = "preferences2";

    private static final String PREF_logInStatus = "logInStatus";
    private static final String PREF_name = "name";
    private static final String PREF_fname = "fname";
    private static final String PREF_lname = "lname";
    private static final String PREF_schoolname = "schoolname";
    private static final String PREF_email = "user_email";
    private static final String PREF_phone_number = "phone_number";
    private static final String PREF_user_type = "user_type";
    private static final String PREF_business = "business";
    private static final String PREF_id = "id";
    private static final String PREF_profile_img = "profile_img";
    private static final String PREF_cart_no = "cart_no";
    private static final String PREF_ship_address_id = "ship_address_id";
    private static final String PREF_ship_full_address = "ship_full_address";
    private static final String PREF_login_from = "login_from";
    private static final String PREF_organisation = "organisation";
    private static final String PREF_order_id = "order_id";
    private static final String PREF_order_type = "ordertype";
    private static final String remote = "remote_id";
    private static final String PREF_unique_name = "unique_name";
    private static final String PREF_main_access_group_id = "main_access_group_id";
    private static final String PREF_sub_access_group_id = "sub_access_group_id";
    private static final String PREF_type = "type";
    private static final String PREF_first_login = "first_login";
    private static final String PREF_notification = "notification";
    private static final String PREF_latitude = "latitude";
    private static final String PREF_longitude = "longitude";
    private static final String PREF_location = "location";
    private static final String PREF_NOTIFY = "notify";




    public Shared_Preference(Context context) {
        this.context = context;

        this.globalclass = (GlobalClass) context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

        this.sharedPreferences2 = context.getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE);
        this.editor2 = sharedPreferences2.edit();



    }

    public void savePrefrence() {

        if (globalclass.getLogin_status()) {

            editor.putString(remote,remote_user_id);
            pref_logInStatus = globalclass.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);

            pref_name = globalclass.getName();
            editor.putString(PREF_name, pref_name);

            pref_fname = globalclass.getFname();
            editor.putString(PREF_fname, pref_fname);

            schoolname=globalclass.getSchool_name();
            editor.putString(PREF_schoolname,schoolname);

            pref_lname = globalclass.getLname();
            editor.putString(PREF_lname, pref_lname);

            pref_id= globalclass.getId();
            editor.putString(PREF_id,pref_id);

            unique_name= globalclass.getUnique_name();
            editor.putString(PREF_unique_name,unique_name);


            main_access_group_id= globalclass.getMain_access_group_id();
            editor.putString(PREF_main_access_group_id,main_access_group_id);


            sub_access_group_id= globalclass.getSub_access_group_id();
            editor.putString(PREF_sub_access_group_id,sub_access_group_id);



            type = globalclass.getType();
            editor.putString(PREF_type,type);

            switch_notify= globalclass.getNotify();
            editor.putString(PREF_NOTIFY,switch_notify);


            first_login= globalclass.getFirst_login();
            editor.putString(PREF_first_login,first_login);



            notification= globalclass.getNotification();
            editor.putString(PREF_notification,notification);



            latitude= globalclass.getLatitude();
            editor.putString(PREF_latitude,latitude);



            longitude= globalclass.getLongitude();
            editor.putString(PREF_longitude,longitude);



            location= globalclass.getLocation();
            editor.putString(PREF_location,location);

           // list_id= globalclass.getList_Id();
            editor.putString(PREF_id,pref_id);

            pref_email= globalclass.getEmail();
            editor.putString(PREF_email,pref_email);

            pref_phone_number= globalclass.getPhone_number();
            editor.putString(PREF_phone_number,pref_phone_number);

          //  pref_order_id= globalclass.getOrder_id();
            editor.putString(PREF_order_id,pref_order_id);

           // pref_order_type= globalclass.getType();
            editor.putString(PREF_order_type,pref_order_type);

           // pref_business=globalclass.getBusiness();
            editor.putString(PREF_business,pref_business);


            pref_profile_img = globalclass.getProfil_pic();
            editor.putString(PREF_profile_img, pref_profile_img);

           // pref_organisation = globalclass.getOrganization();
            editor.putString(PREF_organisation, pref_organisation);

            pref_cart_no = globalclass.getCart_no();
            editor.putString(PREF_cart_no, pref_cart_no);


            login_from = globalclass.getLogin_from();
            editor.putString(PREF_login_from, login_from);

          /*  pref_ship_address_id = globalclass.getShipping_id();
            editor.putString(PREF_ship_address_id, pref_ship_address_id);

            pref_ship_full_address = globalclass.getShipping_full_address();
            editor.putString(PREF_ship_full_address, pref_ship_full_address);*/

            editor.commit();

        }else{
            // dont save anything, if user is logged out
            pref_logInStatus = globalclass.getLogin_status();
            editor.putBoolean(PREF_logInStatus, pref_logInStatus);
            editor.commit();
        }

    }

    public void loadPrefrence() {
        pref_logInStatus = sharedPreferences.getBoolean(PREF_logInStatus, false);
        globalclass.setLogin_status(pref_logInStatus);

        Log.d("TV", globalclass.getLogin_status() + "");
        if (globalclass.getLogin_status()) {
            remote_user_id=sharedPreferences.getString(remote,"");

            pref_name = sharedPreferences.getString(PREF_name, "");
            globalclass.setName(pref_name);

            pref_fname = sharedPreferences.getString(PREF_fname, "");
            globalclass.setFname(pref_fname);

            pref_lname = sharedPreferences.getString(PREF_lname, "");
            globalclass.setLname(pref_lname);


            switch_notify = sharedPreferences.getString(PREF_NOTIFY, "");
            globalclass.setNotify(switch_notify);

            pref_id= sharedPreferences.getString(PREF_id,"");
            globalclass.setId(pref_id);

            schoolname= sharedPreferences.getString(PREF_schoolname,"");
            globalclass.setSchool_name(schoolname);

            pref_phone_number=sharedPreferences.getString(PREF_phone_number,"");
            globalclass.setPhone_number(pref_phone_number);

            pref_email=sharedPreferences.getString(PREF_email,"");
            globalclass.setEmail(pref_email);

            pref_organisation=sharedPreferences.getString(PREF_organisation,"");

            pref_cart_no=sharedPreferences.getString(PREF_cart_no,"");
            globalclass.setCart_no(pref_cart_no);

            pref_order_id=sharedPreferences.getString(PREF_order_id,"");

            pref_order_type=sharedPreferences.getString(PREF_order_type,"");


            pref_profile_img=sharedPreferences.getString(PREF_profile_img,"");
            globalclass.setProfil_pic(pref_profile_img);


            login_from=sharedPreferences.getString(PREF_login_from,"");
            globalclass.setLogin_from(login_from);


            unique_name=sharedPreferences.getString(PREF_unique_name,"");
            globalclass.setUnique_name(unique_name);


            main_access_group_id=sharedPreferences.getString(PREF_main_access_group_id,"");
            globalclass.setUnique_name(main_access_group_id);


            sub_access_group_id=sharedPreferences.getString(PREF_sub_access_group_id,"");
            globalclass.setSub_access_group_id(sub_access_group_id);


            type=sharedPreferences.getString(PREF_type,"");
            globalclass.setType(type);


            first_login=sharedPreferences.getString(PREF_first_login,"");
            globalclass.setFirst_login(first_login);


            notification=sharedPreferences.getString(PREF_notification,"");
            globalclass.setNotification(notification);


            latitude=sharedPreferences.getString(PREF_latitude,"");
            globalclass.setLatitude(latitude);



            longitude=sharedPreferences.getString(PREF_longitude,"");
            globalclass.setLatitude(longitude);


            location=sharedPreferences.getString(PREF_location,"");
            globalclass.setLocation(location);


            if (Common.player.equals(globalclass.getType())){
                globalclass.setPlayer(true);
                globalclass.setParent(false);
                globalclass.setTrainer(false);
            }else if (Common.parent.equals(globalclass.getType())){
                globalclass.setPlayer(false);
                globalclass.setParent(true);
                globalclass.setTrainer(false);
            }else if (Common.trainer.equals(globalclass.getType())){
                globalclass.setPlayer(false);
                globalclass.setParent(false);
                globalclass.setTrainer(true);
            }

        }
    }

    public void clearPrefrence(){

        editor.clear();
        editor.commit();


    }













}
