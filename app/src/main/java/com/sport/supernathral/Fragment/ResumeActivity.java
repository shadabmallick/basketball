package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.NetworkConstant.AppConfig;
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
import pl.droidsonroids.gif.GifImageView;


public class ResumeActivity extends Fragment implements AdvancedWebView.Listener {

    RecyclerView rv_category;
    AdvancedWebView webView;
    GlobalClass globalClass;
    Shared_Preference preference;
    String TAG="product",id;
    AdapterChat adapterChat;
    ArrayList<String> newsList;
    ImageView img_header;
    GifImageView gifImageView;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    MediaPlayer mediaPlayer = new MediaPlayer();

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
        id=getArguments().getString("news_id");
        initialisation(view);
        function();



        return view;
    }

    private void initialisation(View view) {
        webView =  view.findViewById(R.id.webView);
        exoPlayerView = view.findViewById(R.id.exo_player_view);
        gifImageView =  view.findViewById(R.id.gif);
        webView.setListener(getActivity(), this);
        img_header=view.findViewById(R.id.img_header);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        //rv_category = view.findViewById(R.id.recycler_chat);

    }

    private void function() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        //String details = "https://www.supernahtralsports.com/api/news_details";
       // String details = "https://www.supernahtralsports.com/api/news_details";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
            AppConfig.NEWS_DETAIlS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "NEWS_DETAIlS Response: " + response.toString());

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
                            String content = data.optString("content");
                            String heading = data.get("heading").toString().replaceAll("\"", "");
                            String short_content = data.get("short_content").toString().replaceAll("\"", "");
                            String location = data.get("location").toString().replaceAll("\"", "");
                            String main_access_group_id = data.get("main_access_group_id").toString().replaceAll("\"", "");
                            String sub_access_group_id = data.get("sub_access_group_id").toString().replaceAll("\"", "");
                            String file_type = data.optString("file_type");
                            String file_name = data.optString("file_name");
                            String delete_flag = data.get("delete_flag").toString().replaceAll("\"", "");
                            String is_active = data.get("is_active").toString().replaceAll("\"", "");
                            String entry_date = data.get("entry_date").toString().replaceAll("\"", "");
                            String modified_date = data.get("modified_date").toString().replaceAll("\"", "");
                            String news_like_count = data.get("news_like_count").toString().replaceAll("\"", "");
                            String news_comment_count = data.get("news_comment_count").toString().replaceAll("\"", "");
                            JSONArray subComment=data.getJSONArray("news_comment");

                            Picasso.with(getActivity())
                                    .load(file_name)
                                   .into(img_header);
                            webView.loadHtml(content);
                            webView.loadHtml(content);

                            if(file_type.equals("Image")){
                                exoPlayerView.setVisibility(View.GONE);
                                gifImageView.setVisibility(View.GONE);
                                img_header.setVisibility(View.VISIBLE);

                                if(!file_name.isEmpty()) {
                                    Picasso.with(getActivity())
                                            .load(file_name)
                                            .into(img_header);

                                }
                            }
                            else if(file_type.equals("Video")){

                                exoPlayerView.setVisibility(View.VISIBLE);
                                gifImageView.setVisibility(View.GONE);
                                img_header.setVisibility(View.GONE);

                                if(!file_name.isEmpty()){
                                    try {


                                        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                                        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                                        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

                                        Uri videoURI = Uri.parse(file_name);

                                        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                                        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                                        MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                                        exoPlayerView.setPlayer(exoPlayer);
                                        exoPlayer.prepare(mediaSource);
                                        exoPlayer.setPlayWhenReady(true);
                                    }catch (Exception e){
                                        Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
                                    }
                                }
                            }
                            else if(file_type.equals("Gif")){
                                exoPlayerView.setVisibility(View.GONE);
                                gifImageView.setVisibility(View.VISIBLE);
                                img_header.setVisibility(View.GONE);
                                if(!file_name.isEmpty()) {
                                    Picasso.with(getActivity())
                                            .load(file_name)
                                            .into(gifImageView);


                                }

                            }



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

                params.put("news_id", id);

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