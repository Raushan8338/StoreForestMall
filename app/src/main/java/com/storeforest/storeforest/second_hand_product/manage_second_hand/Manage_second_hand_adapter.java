package com.storeforest.storeforest.second_hand_product.manage_second_hand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;

import java.util.ArrayList;
import java.util.List;

import static com.storeforest.storeforest.Login.MyPref;

public class Manage_second_hand_adapter extends RecyclerView.Adapter<Manage_second_hand_adapter.ProductViewHolder> {

    private ArrayList<Manage_second_hand_item> manage_second_hand_items;
    private List<Manage_second_hand_item> exampleListFull;
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_second_product_item, parent, false);
        ProductViewHolder lvh = new ProductViewHolder(view);
        return lvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final Manage_second_hand_item currentItem = manage_second_hand_items.get(position);

        Picasso.get().load(d.getProfile_img() + currentItem.getP_image())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.tot_minus.setText(currentItem.getPrice());
       // holder.tot_minus.setText(currentItem.get);
        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this product")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                RequestQueue q = Volley.newRequestQueue(context);
                Domain d = new Domain();
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please Wait ! ");
                progressDialog.show();
                String url = "https://trendingstories4u.com/android_app/second_hand_delete.php?id=" + currentItem.getId();
                                Log.e("urkl",url);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                                        Intent intent5 = new Intent(context, Manage_second_hand.class);
                                        context.startActivity(intent5);
                        ((Activity) context).finish();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //Log.e("Error",error.toString()+" " +url);
                    }
                });
                q.add(stringRequest);
                            }
                        })
                ;
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        holder.van_name.setText(currentItem.getTitle());
        //Log.e("response",currentItem.getTitle());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent=new Intent(context, Second_hand_details.class);
                intent.putExtra("p_id",currentItem.getId());
                intent.putExtra("item_name",currentItem.getTitle());
                intent.putExtra("item_brand",currentItem.getBrand());
                intent.putExtra("item_price","200");
                intent.putExtra("deescription",currentItem.getDescribe());
                intent.putExtra("name",currentItem.getName());
                intent.putExtra("contact_no",currentItem.getContact_no());
                intent.putExtra("address",currentItem.getAddress());
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return manage_second_hand_items.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView van_name;
        ImageView imageView;
        TextView area_name;
        TextView van_model;
        LinearLayout booking;
        TextView tot_minus;
        TextView perday;
        TextView sold_out;
        TextView km;
        TextView totdiscount;
        TextView percent;
        TextView daydis;
        RatingBar rating;
        TextView view_details;
        LinearLayout p_share;
        TextView monthdis;
        Button cart_book;
        LinearLayout l_outs;
        LinearLayout details;
        TextView delete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            van_name = itemView.findViewById(R.id.van_name);
            area_name = itemView.findViewById(R.id.area_name);
            booking = itemView.findViewById(R.id.booking);
            van_model = itemView.findViewById(R.id.model);
            imageView = itemView.findViewById(R.id.image);
            p_share = itemView.findViewById(R.id.p_share);
            tot_minus = itemView.findViewById(R.id.tot_minus);
            rating = itemView.findViewById(R.id.rating);
            view_details = itemView.findViewById(R.id.view_details);
            details=itemView.findViewById(R.id.details);
            delete=itemView.findViewById(R.id.delete);
        }
    }

    public Manage_second_hand_adapter(Context context, ArrayList<Manage_second_hand_item> manage_second_hand_items, Boolean x) {
        this.context = context;
        d = new Domain();
        this.manage_second_hand_items =manage_second_hand_items;
        this.x = x;
    }

}

