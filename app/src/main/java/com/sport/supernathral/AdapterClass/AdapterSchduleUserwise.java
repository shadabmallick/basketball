package com.sport.supernathral.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.activity.AttendanceActivity;
import com.sport.supernathral.activity.AttendenceStudentList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;
import static com.sport.supernathral.NetworkConstant.AppConfig.ADD_PLAYER_ATTENDENCE;


public class AdapterSchduleUserwise extends
        RecyclerView.Adapter<AdapterSchduleUserwise.ItemViewHolder> {

    private Context context;
    ArrayList<HashMap<String,String>> arrayList;
     String type,formattedDate ;
     GlobalClass globalClass;
     ProgressDialog pd;
     String schedule_id,datechange;
    Calendar c = Calendar.getInstance();

    AdapterSchduleUserwise.onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(int position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView action, desc,dismiss,tv_present,tv_late,tv_excuse,tv_absent,tv_name;
        CircleImageView profile_image;
        LinearLayout ll_text;
        ImageView img_present,img_late,img_absent,img_excuse;
        AdapterSchduleUserwise.onItemClickListner listner;

        public ItemViewHolder(View itemView, AdapterSchduleUserwise.onItemClickListner listner) {
            super(itemView);

            tv_absent=itemView.findViewById(R.id.tv_absent);
            tv_late=itemView.findViewById(R.id.tv_late);
            tv_excuse=itemView.findViewById(R.id.tv_excuse);
            tv_present=itemView.findViewById(R.id.tv_present);
            tv_name=itemView.findViewById(R.id.tv_name);
            profile_image=itemView.findViewById(R.id.profile_image);
            ll_text=itemView.findViewById(R.id.ll_text);
            img_present=itemView.findViewById(R.id.img_present);
            img_late=itemView.findViewById(R.id.img_late);
            img_absent=itemView.findViewById(R.id.img_absent);
            img_excuse=itemView.findViewById(R.id.img_excuse);

            this.listner = listner;
        }
    }


    public AdapterSchduleUserwise(Context context, ArrayList<HashMap<String,String>> itemList, ProgressDialog pd,String schedule_id,String datechange){

        this.context = context;
        this.arrayList=itemList;
        this.pd=pd;
        this.schedule_id=schedule_id;
        this.datechange=datechange;
        globalClass=(GlobalClass)context.getApplicationContext();

    }

    @Override
    public AdapterSchduleUserwise.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendence_activity, parent,false);
        AdapterSchduleUserwise.ItemViewHolder dvh = new AdapterSchduleUserwise.ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(AdapterSchduleUserwise.ItemViewHolder holder, final int position) {
        holder.tv_late.setText(arrayList.get(position).get("late"));
        holder.tv_absent.setText(arrayList.get(position).get("absent"));
        holder.tv_excuse.setText(arrayList.get(position).get("excuses"));
        holder.tv_present.setText(arrayList.get(position).get("present"));
        holder.tv_name.setText(arrayList.get(position).get("user_name"));
        Log.d(TAG, "onBindViewHolder: "+datechange);
        if(datechange.equals("previous")){

           holder.img_late.setClickable(false);
           holder.img_excuse.setClickable(false);
           holder.img_absent.setClickable(false);
           holder.img_present.setClickable(false);
        }
        else {
            holder.img_present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="present";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="absent";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_excuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="excuses";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_late.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="late";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });

        }

        if(datechange.equals("today")){
            holder.img_present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="present";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="absent";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_excuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="excuses";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
            holder.img_late.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type="late";
                    String team_id=arrayList.get(position).get("team_id");
                    String player_id=arrayList.get(position).get("player_id");

                    AddPlayerAttendence(type,team_id,player_id,formattedDate);
                }
            });
        }

        if(datechange.equals("after")){

        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        if (!arrayList.get(position).get("user_image").isEmpty()){
            Picasso.with(context)
                    .load(arrayList.get(position).get("user_image"))
                    .placeholder(R.mipmap.avatar_gray)
                    .into(holder.profile_image);
        }
       holder.ll_text.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent attendence_student=new Intent(context, AttendenceStudentList.class);
               attendence_student.putExtra("player_id",arrayList.get(position).get("player_id"));
               attendence_student.putExtra("datechange",datechange);
               context.startActivity(attendence_student);
           }
       });


    }

    private void AddPlayerAttendence(final String type,final String team_id,final String player_id,final String date) {


        String tag_string_req = "EventList";

        pd.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                ADD_PLAYER_ATTENDENCE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                if (response != null)
                {
                    pd.dismiss();

                    try {

                        JSONObject main_object = new JSONObject(response);


                        int status = main_object.optInt("status");
                        String message = main_object.optString("message");


                        if (status == 1){

                            FancyToast.makeText(context, message,
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false)
                                    .show();
                            ((AttendanceActivity)context).AttendnceList();

                            }
                        else {


                            FancyToast.makeText(context, message,
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false)
                                    .show();


                        }


                    } catch (Exception e) {

                        FancyToast.makeText(context, "Connection error",
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

                params.put("team_id",team_id);
                params.put("schedule_id",schedule_id);
                params.put("coach_id",globalClass.getId());
                params.put("date",date);
                params.put("player_id",player_id);
                params.put("type",type);
                Log.d(TAG, " player_id: "+params);
                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}