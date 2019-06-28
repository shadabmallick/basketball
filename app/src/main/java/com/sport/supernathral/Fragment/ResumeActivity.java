package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

public class ResumeActivity extends Fragment implements AdvancedWebView.Listener {

    RecyclerView rv_category;
    AdvancedWebView webView;
    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG="product";
    AdapterChat adapterChat;
    ArrayList<String> newsList;
    ImageView img_header;
    String htmltag="<p><span style=\"font-size:16px\">U14 Wildcats&nbsp; Boys played like champions in the with the Title on the line. The Wildcats started the day trailing T-Sky early before changing gears and looking like a well oiled machine the rest of the tournament before dropping a very competitive championship game to an AIDI team that have not faced a challenger like these Wildcats all season. They made a couple big shots down the stretch to earn the title.</span></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356997_IMG_7531.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356902_IMG_7528.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356927_IMG_7529.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356963_IMG_7530.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357337_IMG_7532.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357181_IMG_7533.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357111_IMG_7534.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357024_IMG_7535.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357469_IMG_7536.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357492_IMG_7537.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357549_IMG_7538.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357568_IMG_7539.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357696_IMG_7540.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357669_IMG_7541.jpg\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560357634_IMG_7542.jpg\" style=\"height:200px; width:300px\" />\n" +
            "    <img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356725_IMB_gNw0UL.PNG\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560356874_IMG_2785.JPG\" style=\"height:200px; width:300px\" /></p>\n" +
            "\n" +
            "<p><img alt=\"\" src=\"https://www.supernahtralsports.com/uploads/file_upload/1560167375_U14.JPG\" style=\"height:200px; width:300px\" /></p>";

    ArrayList<HashMap<String,String>> list_names;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resume_screen, container, false);
        globalClass = (GlobalClass)getActivity().getApplicationContext();
        preference = new Shared_Preference(getActivity());
        preference.loadPrefrence();
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        pd.setMessage("Loading...");
        initialisation(view);
        function();



        return view;
    }

    private void initialisation(View view) {
        webView =  view.findViewById(R.id.webView);
        webView.setListener(getActivity(), this);
        img_header=view.findViewById(R.id.img_header);
        //rv_category = view.findViewById(R.id.recycler_chat);

    }

    private void function() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        String details = "https://www.supernahtralsports.com/api/news_details";


        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                details, new Response.Listener<String>() {

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


                            JSONObject data = main_object.getJSONObject("data");


                            String id = data.get("id").toString().replaceAll("\"", "");
                            String star = data.get("star").toString().replaceAll("\"", "");
                            String content = data.get("content").toString().replaceAll("\"", "");
                            String heading = data.get("heading").toString().replaceAll("\"", "");
                            String short_content = data.get("short_content").toString().replaceAll("\"", "");
                            String location = data.get("location").toString().replaceAll("\"", "");
                            String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                            String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                            String file_type = data.get("file_type").toString().replaceAll("\"", "");
                            String file_name = data.get("file_name").toString().replaceAll("\"", "");
                            String delete_flag = data.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = data.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = data.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = data.get("modified_date").toString().replaceAll("\"", "");
                            String news_like_count = data.get("news_like_count").toString().replaceAll("\"", "");
                            String news_comment_count = data.get("news_comment_count").toString().replaceAll("\"", "");
                            JSONArray subComment=data.getJSONArray("news_comment");
                          //  String description1 = (Html.fromHtml(content)).toString().replaceAll("\n", "");
                            String text = content.replace("\\", "");
                            Picasso.with(getActivity())
                                    .load(file_name)
                                   .into(img_header);
                            Log.d(TAG, "onResponse: "+text);

                            webView.loadData(htmltag, "text/html; mi", "UTF-8");
                          //  webView.loadHtml(content);


                        }else {

                            FancyToast.makeText(getActivity(), message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(getActivity(), "Connection error",
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
        })  {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("news_id", "17");


                Log.d(TAG, "login param: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}