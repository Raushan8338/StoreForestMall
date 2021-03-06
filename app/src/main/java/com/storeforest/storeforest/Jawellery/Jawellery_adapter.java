package com.storeforest.storeforest.Jawellery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.Jawellery.jawellery_item.Jawelerry_item_details;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.mobile.Mobile_adapter;
import com.storeforest.storeforest.mobile.Mobiles_item;
import com.storeforest.storeforest.mobile.mobiles_item.Mobile_items;

import java.util.ArrayList;

public class Jawellery_adapter  extends RecyclerView.Adapter<Jawellery_adapter.ProductViewHolder> {

    private ArrayList<Jawllery_item> jawllery_items;
    //private Domain d;
    private Context context;
    private Boolean x;
    TextView certificate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
    public static final String MyPrefid = "MyPrefid";
    public static final String userId = "userId";
    public static final String shop_name = "shop_name";
    public static final String shop_image = "shop_image";
    public static final String min_order = "min_order";
    public static final String delivery_charge = "delivery_charge";

    @NonNull
    @Override
    public Jawellery_adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_item_display, parent, false);
        Jawellery_adapter.ProductViewHolder lvh = new Jawellery_adapter.ProductViewHolder(view);
        return lvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Jawellery_adapter.ProductViewHolder holder, int position) {
        final Jawllery_item currentItem = jawllery_items.get(position);

        holder.van_model.setText(currentItem.getShop_name());
        holder.min_order.setText("For Home Develery Minimum Order Should be Rs."+currentItem.getMin());
        holder.area_name.setText(currentItem.getAddress());
        sharedPreferences1 =context.getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        Domain d=new Domain();
        holder.km.setText(currentItem.getDistance());

        if (currentItem.getShop_image().isEmpty()){
            holder.imageView.setImageResource(R.drawable.logo);
        }
        else {
            Picasso.get().load(d.getProfile_img() + currentItem.getShop_image())
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        }
        holder.full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Jawelerry_item_details.class);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString(userId,currentItem.getUserid());
                editor.putString(shop_name,currentItem.getShop_name());
                editor.putString(shop_image,currentItem.getShop_image());
                editor.putString(min_order,currentItem.getMin());
                editor.putString(delivery_charge,currentItem.getDelivery_charge());
                editor.apply();
                intent.putExtra("h_name",currentItem.getShop_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jawllery_items.size();
    }

    /* @Override
     public Filter getFilter() {
         return exampleFilter;
     }
 */
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView van_name;
        ImageView imageView;
        TextView area_name;
        TextView van_model;
        Button booking;
        TextView perhour;
        TextView perday;
        TextView permonth;
        TextView km;
        TextView totdiscount;
        TextView percent;
        TextView tot_minus;
        TextView daydis;
        RatingBar rating;
        TextView view_details;
        LinearLayout p_share;
        TextView monthdis;
        LinearLayout l_out;
        LinearLayout l_outs;
        LinearLayout full;
        TextView min_order;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            van_name = itemView.findViewById(R.id.van_name);
            area_name = itemView.findViewById(R.id.area_name);
            booking = itemView.findViewById(R.id.booking);
            van_model = itemView.findViewById(R.id.model);
            imageView = itemView.findViewById(R.id.image);
            p_share = itemView.findViewById(R.id.p_share);
            full=itemView.findViewById(R.id.full);
            min_order=itemView.findViewById(R.id.min_order);
            km = itemView.findViewById(R.id.km);
            rating = itemView.findViewById(R.id.rating);
            l_out = itemView.findViewById(R.id.l_out);
            //l_outs = itemView.findViewById(R.id.l_outs);

            view_details = itemView.findViewById(R.id.view_details);

            percent = itemView.findViewById(R.id.percent);

        }
    }

    public Jawellery_adapter(Context context, ArrayList<Jawllery_item> jawllery_items, Boolean x) {
        this.context = context;
        // d = new Domain();
        this.jawllery_items = jawllery_items;
        this.x = x;
    }

}

