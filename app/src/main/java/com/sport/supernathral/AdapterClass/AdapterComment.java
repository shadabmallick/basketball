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
import com.sport.supernathral.DataModel.CommentData;
import com.sport.supernathral.DataModel.SubCommentData;
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

public class AdapterComment extends
        RecyclerView.Adapter<AdapterComment.ItemViewHolder> {

    private String TAG="Comment";
    private Context context;
    private int row_index=0;
    private ArrayList<CommentData> listComment;
    private ProgressDialog pd;
    private GlobalClass globalClass;
    private Shared_Preference preference;


    private onItemClickListnerLike mListner1;
    public interface onItemClickListnerLike{
        void onItemClickLike(String id);
    }

    public void setmListner1(onItemClickListnerLike mListner) {
        this.mListner1 = mListner;
    }

    private onItemClickListnerComment mListner2;
    public interface onItemClickListnerComment{
        void onItemClickComment(String id, String type);
    }

    public void setmListner2(onItemClickListnerComment mListner) {
        this.mListner2 = mListner;
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView action, desc,dismiss, tv_show_more,tv_like,tv_name,
                tv_date,tv_comment, tv_comment_count;
        ImageView iv_like, img_comment, iv_like_fill, img_comment_fill;
        RelativeLayout rl_sublist, rel_comment, rel_like;
        CircleImageView profile_image;
        RecyclerView recycler_sub_comments;

        public ItemViewHolder(View itemView) {
            super(itemView);
            img_comment=itemView.findViewById(R.id.img_comment);
            rl_sublist=itemView.findViewById(R.id.rl_sublist);
            tv_show_more=itemView.findViewById(R.id.tv_show_more);
            profile_image=itemView.findViewById(R.id.profile_image);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_like=itemView.findViewById(R.id.tv_like);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_comment_count=itemView.findViewById(R.id.tv_comment_count);
            tv_date=itemView.findViewById(R.id.tv_date);
            iv_like=itemView.findViewById(R.id.iv_like);
            iv_like_fill=itemView.findViewById(R.id.iv_like_fill);
            img_comment_fill=itemView.findViewById(R.id.img_comment_fill);
            rel_comment=itemView.findViewById(R.id.rel_comment);
            rel_like=itemView.findViewById(R.id.rel_like);

            recycler_sub_comments=itemView.findViewById(R.id.recycler_sub_comments);

        }
    }


    public AdapterComment(Context context, ArrayList<CommentData> listComment){

        this.context = context;
        this.listComment = listComment;
        globalClass = ((GlobalClass) context.getApplicationContext());
        preference = new Shared_Preference(context);
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(context.getResources().getString(R.string.loading));

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_comment, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v);
        return dvh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        final CommentData commentData = listComment.get(position);


        if (!commentData.getUser_image().isEmpty()){
            Picasso.with(context)
                    .load(commentData.getUser_image())
                    .placeholder(R.mipmap.profile_placeholder)
                    .into(holder.profile_image);
        }


        holder.tv_comment.setText(commentData.getComment());
        holder.tv_like.setText(commentData.getMoment_comment_like_count());
        holder.tv_name.setText(commentData.getUser_name());
        holder.tv_date.setText(commentData.getEntry_date());
        holder.tv_comment_count.setText(commentData.getMoment_comment_sub_count());


        holder.img_comment_fill.setVisibility(View.GONE);
        holder.img_comment.setVisibility(View.VISIBLE);



        if (row_index == position){
            holder.img_comment_fill.setVisibility(View.VISIBLE);
            holder.img_comment.setVisibility(View.GONE);
        }else {
            holder.img_comment_fill.setVisibility(View.GONE);
            holder.img_comment.setVisibility(View.VISIBLE);
        }

        holder.rel_comment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              row_index = position;
              notifyDataSetChanged();

              mListner2.onItemClickComment(commentData.getId(), "sub_comment");

          }

        });


        if (Integer.parseInt(commentData.getMoment_comment_like_count()) > 0){
            holder.iv_like_fill.setVisibility(View.VISIBLE);
            holder.iv_like.setVisibility(View.GONE);
        }else {
            holder.iv_like_fill.setVisibility(View.GONE);
            holder.iv_like.setVisibility(View.VISIBLE);
        }


        holder.rel_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListner1.onItemClickLike(commentData.getId());

            }
        });


        holder.tv_show_more.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


              }
        });

         if(row_index == position){

              Log.d(TAG, "sub comment size : "+commentData.getList_sub_comment().size());

              if (commentData.getList_sub_comment().size() > 2){
                  holder.tv_show_more.setVisibility(View.VISIBLE);

                  ArrayList<SubCommentData> listSubComment = new ArrayList<>();
                  listSubComment.add(commentData.getList_sub_comment().get(0));
                  listSubComment.add(commentData.getList_sub_comment().get(1));

                  setSubComment(holder.recycler_sub_comments, listSubComment);

              } else {
                  holder.tv_show_more.setVisibility(View.GONE);
                  setSubComment(holder.recycler_sub_comments, commentData.getList_sub_comment());
              }

              holder.rl_sublist.setVisibility(View.VISIBLE);

         } else {

            holder.rl_sublist.setVisibility(View.GONE);

         }

    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }


    private void setSubComment(RecyclerView recyclerView, ArrayList<SubCommentData> itemList){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdapterSubComment adapterSubComment = new AdapterSubComment(context, itemList);
        //Log.d(TAG, "setSubComment: "+itemList);
        recyclerView.setAdapter(adapterSubComment);

    }



}