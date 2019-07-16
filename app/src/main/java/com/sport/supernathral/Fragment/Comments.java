package com.sport.supernathral.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.AdapterClass.AdapterChat;
import com.sport.supernathral.AdapterClass.AdapterComment;
import com.sport.supernathral.AdapterClass.PlayerAdapter;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.sport.supernathral.activity.SearchPlayer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS_COMMENT;
import static com.sport.supernathral.NetworkConstant.AppConfig.SEARCH_USER;

public class Comments extends Fragment {

    RecyclerView rv_category;

    String TAG="product";
    AdapterComment adapterComment;
    ArrayList<String> newsList;
    Shared_Preference preference;
    GlobalClass globalClass;

    ArrayList<HashMap<String,String>> comment_list;
    ArrayList<HashMap<String,String>> subcomment_list;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_comment, container, false);

        initialisation(view);



        return view;
    }

    private void initialisation(View view) {

        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getActivity().getResources().getString(R.string.loading));
        rv_category = view.findViewById(R.id.recycler_comment);

        preference = new Shared_Preference(getActivity());
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        function();
    }

    private void function() {
        comment_list=new ArrayList<>();
        subcomment_list=new ArrayList<>();



        rv_category.setLayoutManager(new LinearLayoutManager(getActivity()));
       // adapterChat = new AdapterComment(getActivity(), newsList);
       // rv_category.setAdapter(adapterChat);
        CommentList();

    }

    private void CommentList() {


        String tag_string_req = "forget_password";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                NEWS_COMMENT, new Response.Listener<String>() {

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




                            JSONArray news_data=main_object.getJSONArray("data");


                            for (int i = 0; i < news_data.length(); i++) {
                                JSONObject item = news_data.getJSONObject(i);
                                String id = item.get("id").toString().replaceAll("\"", "");
                                String news_id = item.get("news_id").toString().replaceAll("\"", "");
                                String user_id = item.get("user_id").toString().replaceAll("\"", "");
                                String comment = item.get("comment").toString().replaceAll("\"", "");
                                String comment_id = item.get("comment_id").toString().replaceAll("\"", "");
                                String delete_flag = item.get("delete_flag").toString().replaceAll("\"", "");
                                String is_active = item.get("is_active").toString().replaceAll("\"", "");
                                String entry_date = item.get("entry_date").toString().replaceAll("\"", "");
                                String modified_date = item.get("modified_date").toString().replaceAll("\"", "");
                                String user_name = item.get("user_name").toString().replaceAll("\"", "");
                                String user_image = item.get("user_image").toString().replaceAll("\"", "");
                                String news_comment_like = item.get("news_comment_like").toString().replaceAll("\"", "");
                                String news_comment_sub_count = item.get("news_comment_sub_count").toString().replaceAll("\"", "");

                                JSONArray news_comment_sub=item.getJSONArray("news_comment_sub");
                                for (int j = 0; j < news_comment_sub.length(); j++) {
                                    JSONObject item_comment = news_data.getJSONObject(j);
                                    String sub_id = item_comment.get("id").toString().replaceAll("\"", "");
                                    String sub_news_id = item_comment.get("news_id").toString().replaceAll("\"", "");
                                    String sub_user_id = item_comment.get("user_id").toString().replaceAll("\"", "");
                                    String sub_comment = item_comment.get("comment").toString().replaceAll("\"", "");
                                    String sub_comment_id = item_comment.get("comment_id").toString().replaceAll("\"", "");
                                    String sub_delete_flag = item_comment.get("delete_flag").toString().replaceAll("\"", "");
                                    String sub_is_active = item_comment.get("is_active").toString().replaceAll("\"", "");
                                    String sub_entry_date = item_comment.get("entry_date").toString().replaceAll("\"", "");
                                    String sub_modified_date = item_comment.get("modified_date").toString().replaceAll("\"", "");
                                    String sub_user_name = item_comment.get("user_name").toString().replaceAll("\"", "");
                                    String sub_user_image = item_comment.get("user_image").toString().replaceAll("\"", "");
                                    String sub_news_comment_like = item_comment.get("news_comment_like").toString().replaceAll("\"", "");
                                    String sub_news_comment_sub_count = item_comment.get("news_comment_sub_count").toString().replaceAll("\"", "");

                                    HashMap<String, String> SubComment = new HashMap<>();
                                    SubComment.put("sub_id", sub_id);
                                    SubComment.put("sub_news_id", sub_news_id);
                                    SubComment.put("sub_user_id", sub_user_id);
                                    SubComment.put("sub_comment", sub_comment);
                                    SubComment.put("sub_comment_id", sub_comment_id);
                                    SubComment.put("sub_delete_flag", sub_delete_flag);
                                    SubComment.put("sub_is_active", sub_is_active);
                                    SubComment.put("sub_entry_date", sub_entry_date);
                                    SubComment.put("sub_modified_date", sub_modified_date);
                                    SubComment.put("sub_user_name", sub_user_name);
                                    SubComment.put("sub_user_image", sub_user_image);
                                    SubComment.put("sub_news_comment_like", sub_news_comment_like);
                                    SubComment.put("sub_news_comment_sub_count", sub_news_comment_sub_count);
                                    subcomment_list.add(SubComment);

                                }


                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("user_id", user_id);
                                hashMap.put("user_name", user_name);
                                hashMap.put("user_image", user_image);
                                hashMap.put("id", id);
                                hashMap.put("news_id", news_id);
                                hashMap.put("comment", comment);
                                hashMap.put("comment_id", comment_id);
                                hashMap.put("delete_flag", delete_flag);
                                hashMap.put("is_active", is_active);
                                hashMap.put("entry_date", entry_date);
                                hashMap.put("modified_date", modified_date);
                                hashMap.put("news_comment_like", news_comment_like);
                                hashMap.put("news_comment_sub_count", news_comment_sub_count);




                                //  globalClass.setFolderanme(folder_name);

                                comment_list.add(hashMap);
                                Log.d(TAG, "Hashmap " + hashMap);

                            }
                            adapterComment   = new AdapterComment(getActivity(),comment_list,subcomment_list, pd);
                            rv_category.setAdapter(adapterComment);
                            adapterComment.notifyDataSetChanged();



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
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("news_id","7");



                Log.d(TAG, " param3: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }





}