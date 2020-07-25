package com.storeforest.storeforest.second_hand_product.second_hand_item;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.BuildConfig;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.second_hand_product.Second_hand_product_details;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.storeforest.storeforest.Login.MyPref;

public class Second_hand_adapter extends RecyclerView.Adapter<Second_hand_adapter.ProductViewHolder> {

    private ArrayList<Second_hand_item> second_hand_items;
    private List<Second_hand_item> exampleListFull;
    private Domain d;
    private Context context;
    private Boolean x;
    TextView certificate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
    SharedPreferences sharedPreferences;
    @NonNull
    @Override
    public Second_hand_adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_hand_items, parent, false);
        Second_hand_adapter.ProductViewHolder lvh = new Second_hand_adapter.ProductViewHolder(view);
        return lvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Second_hand_adapter.ProductViewHolder holder, int position) {
        final Second_hand_item currentItem = second_hand_items.get(position);
            Domain d=new Domain();
        Picasso.get().load(d.getProfile_img() + currentItem.getP_image())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        holder.address.setText(currentItem.getAddress());
        holder.van_name.setText(currentItem.getTitle());
        holder.tot_minus.setText(currentItem.getPrice());
        //Log.e("response",currentItem.getTitle());
        if (currentItem.getStatus().equals("0")) {
            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Second_hand_product_details.class);
                    intent.putExtra("p_id", currentItem.getId());
                    intent.putExtra("item_name", currentItem.getTitle());
                    intent.putExtra("item_brand", currentItem.getBrand());
                    intent.putExtra("item_price", currentItem.getPrice());
                    intent.putExtra("deescription", currentItem.getDescribe());
                    intent.putExtra("name", currentItem.getName());
                    intent.putExtra("contact_no", currentItem.getContact_no());
                    intent.putExtra("address", currentItem.getAddress());
                    intent.putExtra("image", currentItem.getP_image());
                    context.startActivity(intent);
                }
            });
        }
        else {
            holder.view_details.setText("Sold Out");
            holder.view_details.setTextColor(Color.GRAY);
        }

        holder.p_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable=holder.imageView.getDrawable();
                Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

                try {
                    File file = new File(context.getApplicationContext().getExternalCacheDir(), File.separator +"dhome.jpeg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");
                    intent.putExtra(intent.EXTRA_TEXT,"Product Name : ");
                    intent.putExtra(intent.EXTRA_PACKAGE_NAME,"Product Price : ");
                    intent.putExtra(intent.EXTRA_TEXT,"Product Name : "+currentItem.getTitle()+" , "+"Product Price : Rs"+currentItem.getPrice()+" , "+" Download the app and sell/Buy your Product "+"https://play.google.com/store/apps/details?id=com.storeforest.storeforest");
                    context.startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return second_hand_items.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView van_name;
        ImageView imageView;
        TextView area_name;
        TextView van_model;
        LinearLayout booking;
        TextView tot_minus;
        RatingBar rating;
        TextView view_details;
        LinearLayout p_share;
        LinearLayout details;
        TextView address;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            van_name = itemView.findViewById(R.id.van_name);
            area_name = itemView.findViewById(R.id.area_name);
            booking = itemView.findViewById(R.id.booking);
            van_model = itemView.findViewById(R.id.model);
            imageView = itemView.findViewById(R.id.image);
            p_share = itemView.findViewById(R.id.p_share);
            tot_minus=itemView.findViewById(R.id.tot_minus);
            rating = itemView.findViewById(R.id.rating);
            view_details = itemView.findViewById(R.id.view_details);
            details=itemView.findViewById(R.id.details);
            address=itemView.findViewById(R.id.address);
        }
    }

    public Second_hand_adapter(Context context, ArrayList<Second_hand_item> second_hand_items, Boolean x) {
        this.context = context;
        d = new Domain();
        this.second_hand_items =second_hand_items;
        this.x = x;
    }

}

