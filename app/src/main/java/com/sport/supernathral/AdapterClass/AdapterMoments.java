package com.sport.supernathral.AdapterClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.MomentData;
import com.sport.supernathral.DataModel.SubCommentData;
import com.sport.supernathral.NetworkConstant.AppConfig;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.ScalableVideoView;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.ChangePassword;
import com.sport.supernathral.activity.GameCommentScreen;
import com.sport.supernathral.activity.MomentCommentList;
import com.sport.supernathral.activity.MomentMessage;
import com.sport.supernathral.activity.MomentsActivity;
import com.sport.supernathral.activity.ShowVideo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;

import static android.support.constraint.Constraints.TAG;

public class AdapterMoments extends RecyclerView.Adapter<AdapterMoments.ItemViewHolder> {

    private Context context;
    private ArrayList<MomentData> listMoments;
    GlobalClass globalClass;
    EditText message_text;
    TextView tv_yes,tv_no;
    ImageView send;
    ProgressDialog pd;
    Shared_Preference preference;

    AdapterMoments.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profile_image;
        TextView tv_name, tv_date, tv_like_no, tv_comment_no,
                tv_report,tv_content, tv_show_more;
        ImageView iv_delete, iv_like, iv_unlike,img_comment, img_comment_fill;
        VideoView videoView;
        ViewPager viewPager;
        TabLayout tab_layout;
        RelativeLayout rel_viewpager, rel_videoview, rl_sublist;
        ProgressBar progressBar;
        RecyclerView recyclerView;
        AdapterMoments.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterMoments.onItemClickListner listner) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_like_no = itemView.findViewById(R.id.tv_like_no);
            tv_comment_no = itemView.findViewById(R.id.tv_comment_no);
            tv_report = itemView.findViewById(R.id.tv_report);
            iv_delete = itemView.findViewById(R.id.iv_delete);
            iv_like = itemView.findViewById(R.id.iv_like);
            img_comment = itemView.findViewById(R.id.img_comment);
            img_comment_fill = itemView.findViewById(R.id.img_comment_fill);
            videoView = itemView.findViewById(R.id.videoView);
            tab_layout = itemView.findViewById(R.id.tab_layout);
            viewPager = itemView.findViewById(R.id.viewPager);
            rel_viewpager = itemView.findViewById(R.id.rel_viewpager);
            rel_videoview = itemView.findViewById(R.id.rel_videoview);
            progressBar = itemView.findViewById(R.id.progressBar);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_unlike = itemView.findViewById(R.id.iv_unlike);
            tv_show_more = itemView.findViewById(R.id.tv_show_more);
            rl_sublist = itemView.findViewById(R.id.rl_sublist);
            recyclerView = itemView.findViewById(R.id.recycler_sub_comments);

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            this.listner = listner;
        }
    }


    public AdapterMoments(Context context, ArrayList<MomentData> itemList){
        this.context = context;
        this.listMoments=itemList;
        globalClass =(GlobalClass)context.getApplicationContext();
        preference = new Shared_Preference(context);
        preference.loadPrefrence();
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading...");


    }

    @Override
    public AdapterMoments.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_moments, parent,false);
        AdapterMoments.ItemViewHolder dvh = new AdapterMoments.ItemViewHolder(v, mListner);
        return dvh;
    }

    @Override
    public int getItemCount() {
        return listMoments.size();
    }

    @Override
    public void onBindViewHolder(final AdapterMoments.ItemViewHolder holder, final int position) {

        final MomentData momentData = listMoments.get(position);
        Log.d(TAG, "onBindViewHolder: "+momentData.toString());

        if (!momentData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(momentData.getUser_image())
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }
     if(globalClass.getId().equals(momentData.getUser_id())){
         holder.iv_delete.setVisibility(View.VISIBLE);
     }
     else {
         holder.iv_delete.setVisibility(View.GONE);
     }

        if (Integer.parseInt(momentData.getMoment_like_count()) == 0){
            holder.iv_like.setVisibility(View.GONE);
            holder.iv_unlike.setVisibility(View.VISIBLE);
        }else {
            holder.iv_like.setVisibility(View.VISIBLE);
            holder.iv_unlike.setVisibility(View.GONE);
        }
        holder.tv_name.setText(momentData.getUser_name());
        holder.tv_content.setText(momentData.getContent());
        holder.tv_like_no.setText(momentData.getMoment_like_count());
        holder.tv_comment_no.setText(momentData.getMoment_comment_count());

        if (momentData.getFile_type().equals("image")){
            setViewPager(holder, momentData.getMoment_files());
            holder.rel_videoview.setVisibility(View.GONE);
            holder.rel_viewpager.setVisibility(View.VISIBLE);
        }else {
            setVideo(holder, momentData.getMoment_files());
            holder.rel_videoview.setVisibility(View.VISIBLE);
            holder.rel_viewpager.setVisibility(View.GONE);

        }
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentLike(momentData.getId());
                holder.iv_like.setVisibility(View.GONE);
                holder.iv_unlike.setVisibility(View.VISIBLE);
            }
        });

        holder.iv_unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentLike(momentData.getId());
                holder.iv_like.setVisibility(View.GONE);
                holder.iv_unlike.setVisibility(View.VISIBLE);
            }
        });

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentDelete(momentData.getId());
            }
        });
        holder.rel_videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent image_video=new Intent(context, ShowVideo.class);
                image_video.putExtra("type",listMoments.get(position).getFile_type());
                image_video.putExtra("file_name",listMoments.get(position).getMoment_files());


                Log.d(TAG, "onClick: "+listMoments.get(position).getMoment_files());
                Log.d(TAG, "onClick: "+listMoments.get(position).getFile_type());
                context.startActivity(image_video);


            }
        });
        holder.tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialog(momentData.getId());
            }
        });
        holder.img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            SelectCommentDialog(momentData.getId());
            }
        });
        holder.img_comment_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectCommentDialog(momentData.getId());
            }
        });





        holder.tv_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MomentCommentList.class);
                intent.putExtra("moment_id", momentData.getId());
              //  intent.putExtra("moment_id",listMoments.get(position). );

                intent.putExtra("from", "moment");
                context.startActivity(intent);


            }
        });


        if(momentData.getList_comment().size() > 0){

            Log.d(TAG, "sub comment size : "
                    +momentData.getList_comment().size());

            if (momentData.getList_comment().size() > 2){
                holder.tv_show_more.setVisibility(View.VISIBLE);

                ArrayList<CommentData> listComment = new ArrayList<>();
                listComment.add(momentData.getList_comment().get(0));
                listComment.add(momentData.getList_comment().get(1));

                setSubComment(holder.recyclerView, listComment);

            } else {
                holder.tv_show_more.setVisibility(View.GONE);
                setSubComment(holder.recyclerView, momentData.getList_comment());
            }

            holder.rl_sublist.setVisibility(View.VISIBLE);

            holder.img_comment_fill.setVisibility(View.VISIBLE);
            holder.img_comment.setVisibility(View.GONE);

        } else {

            holder.rl_sublist.setVisibility(View.GONE);
            holder.img_comment_fill.setVisibility(View.GONE);
            holder.img_comment.setVisibility(View.VISIBLE);
        }












    }
    public void SelectCommentDialog(final String momnetid) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_comment, null);
        dialogBuilder.setView(dialogView);
               final AlertDialog alertDialog = dialogBuilder.create();

        message_text=dialogView.findViewById(R.id.message_text);
        send=dialogView.findViewById(R.id.send_button);

       send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               SendComment(momnetid,message_text.getText().toString().trim());
               alertDialog.dismiss();
           }
       });

        alertDialog.show();
    }
    public void ReportDialog(final String momnetid) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_news_report, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        tv_yes=dialogView.findViewById(R.id.tv_yes);
        tv_no=dialogView.findViewById(R.id.tv_no);

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MomentReport(momnetid);
                alertDialog.dismiss();
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
            }
        });



        alertDialog.show();
    }

    private void SendComment(final String moment_id,final String text) {
        // Tag used to cancel the request

        pd.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.POST_MOMENT_COMMENT;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1) {
                        JSONArray data = main_object.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);

                            MomentData momentData = new MomentData();
                            momentData.setId(object.optString("id"));
                            momentData.setUser_id(object.optString("user_id"));
                            momentData.setContent(object.optString("content"));
                            momentData.setFile_type(object.optString("file_type"));
                            momentData.setDelete_flag(object.optString("delete_flag"));
                            momentData.setIs_active(object.optString("is_active"));
                            momentData.setEntry_date(object.optString("entry_date"));
                            momentData.setModified_date(object.optString("modified_date"));
                            momentData.setUser_name(object.optString("user_name"));
                            momentData.setUser_image(object.optString("user_image"));
                            momentData.setMoment_like_count(object.optString("moment_like_count"));
                            momentData.setMoment_comment_count(object.optString("moment_comment_count"));

                            listMoments.add(momentData);
                            ((MomentsActivity)context).getMOMENTList();

                        }


                    }



                    pd.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(context,
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id",globalClass.getId());
                params.put("moment_id",moment_id);
                params.put("comment",text);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }

    private void MomentLike(final String moment_id) {
        // Tag used to cancel the request

        pd.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.POST_MOMENT_LIKE;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1) {

                        ((MomentsActivity)context).getMOMENTList();



                    }



                    pd.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(context,
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id",globalClass.getId());
                params.put("moment_id",moment_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }
    private void MomentDelete(final String moment_id) {
        // Tag used to cancel the request

        pd.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.POST_MOMENT_DELETE;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1) {

                        ((MomentsActivity)context).getMOMENTList();



                    }



                    pd.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(context,
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id",globalClass.getId());
                params.put("moment_id",moment_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }


    private void MomentReport(final String moment_id) {
        // Tag used to cancel the request

        pd.show();

        listMoments = new ArrayList<>();

        String tag_string_req = "MOMENT";

        String url = AppConfig.REPORT_MOMENT;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "CHAT_LIST Response: " + response);

                try {

                    JSONObject main_object = new JSONObject(response);

                    int status = main_object.optInt("status");
                    String message = main_object.optString("message");

                    if (status == 1) {

                        ((MomentsActivity)context).getMOMENTList();



                    }



                    pd.dismiss();

                } catch (Exception e) {

                    FancyToast.makeText(context,
                            "Data Connection", FancyToast.LENGTH_LONG,
                            FancyToast.WARNING, false).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("user_id",globalClass.getId());
                params.put("moment_id",moment_id);

                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000,
                10, 1.0f));

    }


    private void setViewPager(ItemViewHolder holder, ArrayList<String> list){

        holder.tab_layout.setupWithViewPager(holder.viewPager, true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, list);
        holder.viewPager.setAdapter(viewPagerAdapter);

    }

    private void setVideo(final ItemViewHolder holder, ArrayList<String> list){

        holder.progressBar.setVisibility(View.VISIBLE);

        if (list.size() > 0){

            String VideoURL = list.get(0);
            try {

                MediaController mediacontroller = new MediaController(
                        context);
                mediacontroller.setAnchorView(null);
                Uri video = Uri.parse(VideoURL);
                holder.videoView.setMediaController(null);
                //holder.videoView.setVideoURI(video);
                holder.videoView.setVideoURI(video);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            holder.videoView.requestFocus();

            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    holder.progressBar.setVisibility(View.GONE);
                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int h = displaymetrics.heightPixels;
                    int w = displaymetrics.widthPixels;
                    mp.setVolume(0f, 0f);
                    mp.setLooping(true);
                    //holder.videoView.setLayoutParams(new FrameLayout.LayoutParams(w, h));
                    holder.videoView.start();
                }
            });
        }

    }

    private void setSubComment(RecyclerView recyclerView,
                               ArrayList<CommentData> listComment){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdapterCommentMoment adapterSubComment =
                new AdapterCommentMoment(context, listComment);
        //Log.d(TAG, "setSubComment: "+itemList);
        recyclerView.setAdapter(adapterSubComment);

    }

}