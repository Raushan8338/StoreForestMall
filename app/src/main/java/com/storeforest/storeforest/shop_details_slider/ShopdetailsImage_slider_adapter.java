package com.storeforest.storeforest.shop_details_slider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;

import java.util.ArrayList;

public class ShopdetailsImage_slider_adapter extends RecyclerView.Adapter<ShopdetailsImage_slider_adapter.NotificationViewHolder> {
    private ArrayList<Shop_details_Image_slider_item> shop_details_image_slider_items;
    SharedPreferences sharedPreferences;
    private Context context;
    ProgressDialog progressDialog;

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageshop_details_slider,parent, false);
        NotificationViewHolder lvh = new NotificationViewHolder(view);
        return lvh;
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Shop_details_Image_slider_item currentItem = shop_details_image_slider_items.get(position);
        Domain d=new Domain();
        Picasso.get().load(d.getProfile_img()+currentItem.getImage())
                .fit()
                .centerCrop()
                .into(holder.image_slider);

    }

    @Override
    public int getItemCount() {
        return shop_details_image_slider_items.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView image_slider;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            image_slider=itemView.findViewById(R.id.image_slider);

        }
    }

    public ShopdetailsImage_slider_adapter(Context context, ArrayList<Shop_details_Image_slider_item> shop_details_image_slider_items, boolean b){
        this.context=context;
        this.shop_details_image_slider_items=shop_details_image_slider_items;
    }
}
