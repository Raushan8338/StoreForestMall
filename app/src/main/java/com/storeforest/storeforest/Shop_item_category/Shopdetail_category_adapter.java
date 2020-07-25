package com.storeforest.storeforest.Shop_item_category;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Shop_details_category.Shop_items;

import java.util.ArrayList;

public class Shopdetail_category_adapter extends RecyclerView.Adapter<Shopdetail_category_adapter.NotificationViewHolder> {
    private ArrayList<Shop_details_item_category> shop_details_item_categories;
    SharedPreferences sharedPreferences;
    private Context context;
    ProgressDialog progressDialog;
    public static final String Mydetailscategory = "Mydetailscategory";
    public static final String category_value = "usecategory_valuer_id";
    public static final String category_name = "category_name";

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_details_category_item,parent, false);
        NotificationViewHolder lvh = new NotificationViewHolder(view);
        return lvh;
    }
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Shop_details_item_category currentItem = shop_details_item_categories.get(position);
        Domain d=new Domain();
        Picasso.get().load("https://trendingstories4u.com/android_app/"+currentItem.getC_image())
                .fit()
                .centerCrop()
                .into(holder.image_slider);
        Log.e("imageurl",currentItem.getC_name());
        sharedPreferences = context.getSharedPreferences(Mydetailscategory, Context.MODE_PRIVATE);
         holder.select_v.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context, Shop_items.class);
                 SharedPreferences.Editor editor = sharedPreferences.edit();
                 editor.putString(category_value,currentItem.getC_value());
                 editor.putString(category_name,currentItem.getC_name());
                 editor.apply();
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return shop_details_item_categories.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView image_slider;
        TextView check;
        CardView select_v;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            image_slider=itemView.findViewById(R.id.image_slider);
            check=itemView.findViewById(R.id.check);
            select_v=itemView.findViewById(R.id.select_v);
        }
    }

    public Shopdetail_category_adapter(Context context, ArrayList<Shop_details_item_category> shop_details_item_categories, boolean b){
        this.context=context;
        this.shop_details_item_categories=shop_details_item_categories;
    }
}
