package com.sport.supernathral.AdapterClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sport.supernathral.DataModel.ChatData;
import com.sport.supernathral.DataModel.ChatListData;
import com.sport.supernathral.R;
import com.sport.supernathral.activity.ChatImageFull;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatSingleAdapter extends RecyclerView.Adapter<ChatSingleAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ChatData> arrayList;


    private onItemClickListner mListner;
    public interface onItemClickListner{
        void onItemClick(ChatData chatData);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2, textView1Date, textView2Date,
                textView1ImgDate, textView2ImgDate;
        RelativeLayout relMy1, relMy2, relimgView1, relimgView2;
        LinearLayout linMsg1, linMsg2;
        RoundedImageView imageView11, imageView22;
        ProgressBar progressBar;
        BubbleLayout bubble_msg1, bubble_image1, bubble_msg2, bubble_image2;

        onItemClickListner listner;

        public ItemViewHolder(View itemView, onItemClickListner listner) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView1Date = itemView.findViewById(R.id.textView1Date);
            textView2Date = itemView.findViewById(R.id.textView2Date);
            textView1ImgDate = itemView.findViewById(R.id.textView1ImgDate);
            textView2ImgDate = itemView.findViewById(R.id.textView2ImgDate);

            relMy1 = itemView.findViewById(R.id.relMy1);
            relMy2 = itemView.findViewById(R.id.relMy2);
            relimgView1 = itemView.findViewById(R.id.relimgView1);
            relimgView2 = itemView.findViewById(R.id.relimgView2);
            linMsg1 = itemView.findViewById(R.id.linMsg1);
            linMsg2 = itemView.findViewById(R.id.linMsg2);
            imageView11 = itemView.findViewById(R.id.imageView11);
            imageView22 = itemView.findViewById(R.id.imageView22);
            progressBar = itemView.findViewById(R.id.progressBar);

            bubble_msg1 = itemView.findViewById(R.id.bubble_msg1);
            bubble_image1 = itemView.findViewById(R.id.bubble_image1);
            bubble_msg2 = itemView.findViewById(R.id.bubble_msg2);
            bubble_image2 = itemView.findViewById(R.id.bubble_image2);


            this.listner = listner;
        }
    }


    public ChatSingleAdapter(Context context, ArrayList<ChatData> itemList,
                             onItemClickListner mListner){
        this.context = context;
        this.arrayList = itemList;
        this.mListner = mListner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_single, parent,false);
        ItemViewHolder dvh = new ItemViewHolder(v, mListner);
        return dvh;
    }


    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, final int position) {

        final ChatData chatData = arrayList.get(position);


        //Log.d("TAG" ,"msg- " + chatData.getMessage());
        //Log.d("TAG" ,"msg type- " + chatData.getMessage_type());

        viewHolder.progressBar.setVisibility(View.GONE);

        if (chatData.getMessage_type().matches("1")) {

            if (chatData.isIs_me()) {

                viewHolder.relMy1.setVisibility(View.VISIBLE);
                viewHolder.relMy2.setVisibility(View.GONE);

                viewHolder.textView1.setText(chatData.getMessage());
                viewHolder.textView1Date.setText(chatData.getDatetime());
                viewHolder.linMsg1.setVisibility(View.VISIBLE);
                viewHolder.bubble_msg1.setVisibility(View.VISIBLE);

                viewHolder.relimgView1.setVisibility(View.GONE);
                viewHolder.bubble_image1.setVisibility(View.GONE);


            }else{

                viewHolder.relMy2.setVisibility(View.VISIBLE);
                viewHolder.relMy1.setVisibility(View.GONE);

                viewHolder.textView2.setText(chatData.getMessage());
                viewHolder.textView2Date.setText(chatData.getDatetime());

                viewHolder.linMsg2.setVisibility(View.VISIBLE);
                viewHolder.bubble_msg2.setVisibility(View.VISIBLE);

                viewHolder.relimgView2.setVisibility(View.GONE);
                viewHolder.bubble_image2.setVisibility(View.GONE);


            }

        }else if (chatData.getMessage_type().matches("2")){


            if (chatData.isIs_me()) {

                viewHolder.relMy1.setVisibility(View.VISIBLE);
                viewHolder.relMy2.setVisibility(View.GONE);

                viewHolder.textView1.setText(chatData.getMessage());
                viewHolder.textView1Date.setText(chatData.getDatetime());
                viewHolder.linMsg1.setVisibility(View.VISIBLE);
                viewHolder.bubble_msg1.setVisibility(View.VISIBLE);

                viewHolder.relimgView1.setVisibility(View.GONE);
                viewHolder.bubble_image1.setVisibility(View.GONE);

            }else{

                viewHolder.relMy2.setVisibility(View.VISIBLE);
                viewHolder.relMy1.setVisibility(View.GONE);

                viewHolder.textView2.setText(chatData.getMessage());
                viewHolder.textView2Date.setText(chatData.getDatetime());

                viewHolder.linMsg2.setVisibility(View.VISIBLE);
                viewHolder.bubble_msg2.setVisibility(View.VISIBLE);

                viewHolder.relimgView2.setVisibility(View.GONE);
                viewHolder.bubble_image2.setVisibility(View.GONE);

            }

        }else if (chatData.getMessage_type().matches("3")){

            Log.d("TAG" ,"msg from - " + chatData.getImage_from());

            if (chatData.isIs_me()) {

                viewHolder.relMy1.setVisibility(View.VISIBLE);
                viewHolder.relMy2.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.VISIBLE);

                //checking if image is just sent or loaded via api
                if (chatData.getImage_from().matches("web")){

                    Picasso.with(context).load(chatData.getMessage())
                            .error(R.mipmap.camera_grey)
                            .into( viewHolder.imageView11, new Callback() {
                        @Override
                        public void onSuccess() {

                            viewHolder.progressBar.setVisibility(View.GONE);

                        }
                        @Override
                        public void onError() {
                        }
                    });
                    viewHolder.imageView11.setScaleType(ImageView.ScaleType.CENTER_CROP);

                }else if (chatData.getImage_from().matches("local")){

                    try {

                        viewHolder.progressBar.setVisibility(View.GONE);

                        InputStream inputStream = context.getContentResolver()
                                .openInputStream(Uri.fromFile(new File(chatData.getMessage())));
                        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                        if (inputStream != null) inputStream.close();



                        //viewHolder.imageView11.setImageURI(Uri.parse(chatData.getMessage()));
                        viewHolder.imageView11.setImageBitmap(bmp);


                        viewHolder.imageView11.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                viewHolder.relimgView1.setVisibility(View.VISIBLE);
                viewHolder.bubble_image1.setVisibility(View.VISIBLE);
                viewHolder.linMsg1.setVisibility(View.GONE);
                viewHolder.bubble_msg1.setVisibility(View.GONE);
                viewHolder.textView1ImgDate.setText(chatData.getDatetime());


            }else {

                viewHolder.relMy2.setVisibility(View.VISIBLE);
                viewHolder.relMy1.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.VISIBLE);

                Picasso.with(context)
                        .load(chatData.getMessage())
                        .error(R.mipmap.camera_grey)
                        .into( viewHolder.imageView22, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError() {
                    }
                });

                viewHolder.imageView22.setScaleType(ImageView.ScaleType.CENTER_CROP);

                viewHolder.relimgView2.setVisibility(View.VISIBLE);
                viewHolder.bubble_image2.setVisibility(View.VISIBLE);
                viewHolder.linMsg2.setVisibility(View.GONE);
                viewHolder.bubble_msg2.setVisibility(View.GONE);

                viewHolder.textView2ImgDate.setText(chatData.getDatetime());

            }

        }


        viewHolder.imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chatData.getMessage_type().matches("3")) {

                    Intent intent = new Intent(context, ChatImageFull.class);
                    intent.putExtra("img", chatData.getMessage());
                    intent.putExtra("key", chatData.getImage_from());
                    context.startActivity(intent);
                }

            }
        });

        viewHolder.imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chatData.getMessage_type().matches("3")) {

                    Intent intent = new Intent(context, ChatImageFull.class);
                    intent.putExtra("img", chatData.getMessage());
                    intent.putExtra("key", chatData.getImage_from());
                    context.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}