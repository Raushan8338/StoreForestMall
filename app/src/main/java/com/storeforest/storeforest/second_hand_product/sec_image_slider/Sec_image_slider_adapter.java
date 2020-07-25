package com.storeforest.storeforest.second_hand_product.sec_image_slider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;

import java.util.ArrayList;

public class Sec_image_slider_adapter extends RecyclerView.Adapter<Sec_image_slider_adapter.NotificationViewHolder> {
    private ArrayList<Sec_image_slider_item> image_slider_items;
    SharedPreferences sharedPreferences;
    private Context context;
    ProgressDialog progressDialog;

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageslider_item,parent, false);
        NotificationViewHolder lvh = new NotificationViewHolder(view);
        return lvh;
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Sec_image_slider_item currentItem = image_slider_items.get(position);
        final Domain d=new Domain();
        Picasso.get().load(d.getProfile_img()+currentItem.getImage())
                .fit()
                .centerCrop()
                .into(holder.image_slider);
        Log.e("image",d.getProfile_img()+currentItem.getImage());
        holder.image_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hello ", Toast.LENGTH_SHORT).show();
           /*     viewTC(d.getProfile_img() + currentItem.getImage());
                final Dialog d = new Dialog(context);
                d.setContentView(R.layout.zoom_custom_layout);
                PhotoView photoView =d.findViewById(R.id.imageView);
                Picasso.get().load(currentItem.getImage())
                        .fit()
                        .centerCrop()
                        .into(photoView);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();*/
            }
        });
       // holder.check.setText(currentItem.getDescription());

    }
  /*  private void viewTC(String imageView) {
        final Dialog d = new Dialog(context);
        d.setContentView(R.layout.zoom_custom_layout);
        PhotoView photoView =d.findViewById(R.id.imageView);


        ImageView i =d.findViewById(R.id.image);
        i.setImageResource(R.drawable.bike_img);
        Picasso.get().load(imageView)
                .fit()
                .centerCrop()
                .into(photoView);
        Log.e("url", String.valueOf(i));
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }*/

    @Override
    public int getItemCount() {
        return image_slider_items.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView image_slider;
        TextView check;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            image_slider=itemView.findViewById(R.id.image_slider);
            check=itemView.findViewById(R.id.check);
        }
    }

    public Sec_image_slider_adapter(Context context, ArrayList<Sec_image_slider_item> image_slider_items, boolean b){
        this.context=context;
        this.image_slider_items=image_slider_items;
    }
}
