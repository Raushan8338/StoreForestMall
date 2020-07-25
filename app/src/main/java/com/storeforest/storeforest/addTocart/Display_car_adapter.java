package com.storeforest.storeforest.addTocart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;

public class Display_car_adapter extends RecyclerView.Adapter<Display_car_adapter.DisplayViewHolder> {
    private ArrayList<Display_cart_item> display_cart_items;
   // private Domain d;
    private Context context;
    private Boolean x;
    SharedPreferences sharedPreferences;
    TextView certificate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
    @NonNull
    @Override
    public Display_car_adapter.DisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_cart_item,parent, false);
        Display_car_adapter.DisplayViewHolder lvh = new Display_car_adapter.DisplayViewHolder(view);
        return lvh;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Display_car_adapter.DisplayViewHolder holder, final int position) {
        final Display_cart_item currentItem = display_cart_items.get(position);


          //      holder.booking.setText("Details");
        sharedPreferences1 = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Domain d=new Domain();
         //   holder.gst.setText(currentItem.getC());
         //   holder.sgst.setText(currentItem.getC());
         //   holder.total.setText(currentItem.getB());
            holder.van_name.setText(currentItem.getV());
            progressDialog=new ProgressDialog(context);
            holder.price.setText("Rs."+currentItem.getTotal_a());
            holder.offer_price.setText("Rs."+currentItem.getB());
            holder.i_category.setText(currentItem.getWait_details()+" "+currentItem.getAtrribute());
            holder.total_items.setText("1 * "+currentItem.getQuantity()+" total items");

            holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "https://trendingstories4u.com/android_app/addtocart_cart_details.php?b_id="+currentItem.getBid()+"&user_id="+sharedPreferences1.getString(user_id,null)+"&booking_amount="+currentItem.getB();
                    Log.e("car_ad",url);
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response",response);
                            //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();

                          //  Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, Display_cart_details.class);
                            //intent.putExtra("hello", currentItem.getV());
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest1);
                }
            });
            holder.delete_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "https://trendingstories4u.com/android_app/delete_all_product.php?b_id="+currentItem.getBid()+"&user_id="+sharedPreferences1.getString(user_id,null)+"&booking_amount="+currentItem.getB();
                    Log.e("car_ad",url);
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response",response);
                            progressDialog.dismiss();
                            //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();

                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, Display_cart_details.class);
                            intent.putExtra("hello", currentItem.getV());
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest1);
                }
            });

           holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "https://trendingstories4u.com/android_app/delete_product.php?b_id="+currentItem.getBid()+"&user_id="+sharedPreferences1.getString(user_id,null)+"&booking_amount="+currentItem.getB();
                     Log.e("car_ad",url);
                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("response",response);
                            //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();

                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Display_cart_details.class);
                                intent.putExtra("hello", currentItem.getV());
                                context.startActivity(intent);
                                ((Activity) context).finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(stringRequest1);
                }
            });

        Picasso.get().load(d.getProfile_img()+currentItem.getF())
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return display_cart_items.size();
    }

    public class DisplayViewHolder extends RecyclerView.ViewHolder {
        TextView van_name;
        ImageView imageView;
        ImageView booking;
        TextView totdiscount;
        TextView tot_minus;
        RatingBar rating;
        TextView view_details;
        TextView amount;
        TextView gst;
        TextView sgst;
        TextView i_category;
        Button delete;
        Button add_to_cart;
        TextView price;
        TextView total_items;
        TextView offer_price;
        ImageView delete_all;
        public DisplayViewHolder(@NonNull View itemView) {
            super(itemView);
            van_name=itemView.findViewById(R.id.van_name);
            booking=itemView.findViewById(R.id.booking);
            imageView=itemView.findViewById(R.id.image);
            //amount=itemView.findViewById(R.id.amount);
            rating=itemView.findViewById(R.id.rating);
            delete=itemView.findViewById(R.id.delete);
            add_to_cart=itemView.findViewById(R.id.add_to_cart);
            view_details=itemView.findViewById(R.id.view_details);
            price=itemView.findViewById(R.id.price);
            i_category=itemView.findViewById(R.id.i_category);
            total_items=itemView.findViewById(R.id.total_items);
            offer_price=itemView.findViewById(R.id.offer_price);
            delete_all=itemView.findViewById(R.id.delete_all);

        }
    }
    public Display_car_adapter(Context context, ArrayList<Display_cart_item> display_cart_items){
        this.context=context;
       // d=new Domain();
        this.display_cart_items=display_cart_items;


    }
}
