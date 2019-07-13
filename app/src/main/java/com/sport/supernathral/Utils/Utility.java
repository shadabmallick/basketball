package com.sport.supernathral.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Developer on 6/22/17.
 */

public class Utility {

    Context mContext;

    public Utility(Context context) {
        mContext = context;
    }


    private String getPathOfAppInternalStorage() {

        String FILE = Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        return FILE;
    }

    private File makeDirectory(){
        File dbDirectory = new File(getPathOfAppInternalStorage()
                + "/" + "SuperNatharal/Video");
        if(!dbDirectory.exists()) {
            dbDirectory.mkdir();
        }
        return dbDirectory;
    }


    public String getVideoDirectory() {
        String FILE = makeDirectory().toString();
        FILE = FILE + "/";
        Log.d("TAG", "dir = "+FILE);
        return FILE;
    }


}
