package com.sport.supernathral.AdapterClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sport.supernathral.R;
import com.sport.supernathral.Utils.GlobalClass;
import com.sport.supernathral.Utils.Shared_Preference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sport.supernathral.NetworkConstant.AppConfig.NEWS_COMMENT;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ItemViewHolder> {
    String TAG="Comment";
private Context context;
    ArrayList<HashMap<String,String>> arrayList;
    ArrayList<HashMap<String,String>> comment_list;
    ArrayList<HashMap<String,String>> subcomment_list;
    ArrayList<HashMap<String,String>> itemList1;
    RecyclerView recycler_sub_comments;

    int row_index=0;
    AdapterSubComment adapterChat;
    ProgressDialog pd;
    GlobalClass globalClass;
    Shared_Preference preference;
   onItemClickListner mListner;
public interface onItemClickListner{
    void onItemClick(int position);
}

public class ItemViewHolder extends RecyclerView.ViewHolder{

    TextView action, desc,dismiss, tv_show_more,tv_comment,tv_like,tv_name,tv_date,tv_type;
    onItemClickListner listner;
    ImageView like, comment;
    RelativeLayout rl_sublist;
    Shared_Preference preference;
    CircleImageView profile_image;


    public ItemViewHolder(View itemView, onItemClickListner listner) {
        super(itemView);
        comment=itemView.findViewById(R.id.img_comment);
        rl_sublist=itemView.findViewById(R.id.rl_sublist);
        tv_show_more=itemView.findViewById(R.id.tv_show_more);
        profile_image=itemView.findViewById(R.id.profile_image);
        tv_comment=itemView.findViewById(R.id.tv_comment);
        tv_like=itemView.findViewById(R.id.tv_like);
        tv_name=itemView.findViewById(R.id.tv_name);
        tv_type=itemView.findViewById(R.id.tv_type);
        tv_date=itemView.findViewById(R.id.tv_date);

        recycler_sub_comments=itemView.findViewById(R.id.recycler_sub_comments);

        this.listner = listner;
    }
}


    public AdapterComment(Context context, ArrayList<HashMap<String,String>>itemList,ArrayList<HashMap<String,String>>itemList1, ProgressDialog pd){

        this.context = context;
        this.arrayList = itemList;
        this.pd=pd;
        this.itemList1=itemList1;
        globalClass = ((GlobalClass) context.getApplicationContext());
        preference = new Shared_Preference(context);
        comment_list=new ArrayList<>();
     //   subcomment_list=new ArrayList<>();

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        Picasso.with(context).load(arrayList.get(position).get("user_image")).placeholder(R.mipmap.profile_placeholder).into(holder.profile_image);
        holder.tv_comment.setText(arrayList.get(position).get("news_comment_sub_count"));
        holder.tv_like.setText(arrayList.get(position).get("news_comment_like"));
        holder.tv_name.setText(arrayList.get(position).get("user_name"));
        holder.tv_date.setText(arrayList.get(position).get("entry_date"));
        holder.comment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              row_index = position;
              notifyDataSetChanged();
          }
      });

      holder.tv_show_more.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


          }
      });

      if(row_index==position){


            setSubComment(recycler_sub_comments, itemList1);
          Log.d(TAG, "onBindViewHolder: "+itemList1);
          if (itemList1.size()>2){
              holder.tv_show_more.setVisibility(View.VISIBLE);
          }
          else {
              holder.tv_show_more.setVisibility(View.GONE);

          }

            holder.rl_sublist.setVisibility(View.VISIBLE);
          //  holder.plus.setVisibility(View.INVISIBLE);

        } else {

            holder.rl_sublist.setVisibility(View.GONE);
           // holder.plus.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void setSubComment(RecyclerView recyclerView,ArrayList<HashMap<String,String>>itemList){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      //  CommentList();
        AdapterSubComment adapterChat = new AdapterSubComment(context, itemList);
        Log.d(TAG, "setSubComment: "+itemList);
       recyclerView.setAdapter(adapterChat);

    }



}