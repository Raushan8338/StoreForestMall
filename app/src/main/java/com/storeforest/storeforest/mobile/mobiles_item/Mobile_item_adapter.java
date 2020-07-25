package com.storeforest.storeforest.mobile.mobiles_item;

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
import android.widget.LinearLayout;
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
import com.storeforest.storeforest.Shop_details_category.Shop_items;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_adapter;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_details;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_details_items;
import com.storeforest.storeforest.mobile.Mobile_details;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.mobile.mobiles_item.Mobile_items.Category_MYPRIF;
import static com.storeforest.storeforest.mobile.mobiles_item.Mobile_items.category_item;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.MyPrefid;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.min_order;

public class Mobile_item_adapter extends RecyclerView.Adapter<Mobile_item_adapter.ProductViewHolder> {
    private ArrayList<Mobiles_item_item> mobiles_item_items;
    private List<Mobiles_item_item> exampleListFull;
    //private Domain d;
    private Context context;
    private Boolean x;
    TextView certificate;
    @RequiresApi(api = Build.VERSION_CODES.N)
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences1;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;

    @NonNull
    @Override
    public Mobile_item_adapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_item_details, parent, false);
        Mobile_item_adapter.ProductViewHolder lvh = new Mobile_item_adapter.ProductViewHolder(view);
        return lvh;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Mobile_item_adapter.ProductViewHolder holder, int position) {
        final Mobiles_item_item currentItem = mobiles_item_items.get(position);
        holder.van_model.setText(currentItem.getName());

        //holder.area_name.setText(currentItem.getName());
        Domain d=new Domain();
        if (currentItem.getFront_img().isEmpty()){
            holder.imageView.setImageResource(R.drawable.logo);
        }
        else {
            Picasso.get().load(d.getProfile_img() + currentItem.getFront_img())
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        }
        sharedPreferences1 = context.getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        sharedPreferences2 = context.getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        holder.wait_details.setText(currentItem.getDis());
        holder.price.setText("Rs."+currentItem.getPrice());
        final String add = sharedPreferences1.getString(address,null);
        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        sharedPreferences3 = context.getSharedPreferences(Category_MYPRIF, Context.MODE_PRIVATE);
        holder.full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Mobile_details.class);
                intent.putExtra("title",currentItem.getName());
                intent.putExtra("price",currentItem.getPrice());
                intent.putExtra("description",currentItem.getDescription());
                intent.putExtra("return_policy",currentItem.getReturn_policy());

                context.startActivity(intent);
            }
        });
        String urlss = "https://trendingstories4u.com/android_app/display_cart_item.php?user_id="+sharedPreferences.getString(user_id,null) ;
        //url = url.replaceAll(" ", "%20");
        Log.e("urlsd",urlss);
        StringRequest stringRequestss = new StringRequest(Request.Method.GET, urlss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject obj = new JSONObject(response);
                    Log.e("response",response);
                    if (obj.getString("count").equals("0")) {
                        holder.remove.setVisibility(View.INVISIBLE);
                        //   progressDialog.dismiss();
                    }
                    else {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Please wait !");
                        if (obj.getString("result").equals("true")) {
                            //
                            JSONArray jsonList = obj.getJSONArray("series");
                            // makeToast(jsonList.length()+"");
                            int x = 0;
                            //  final ArrayList<Display_cart_item> display_cart_items = new ArrayList<>();
                            for (int i = 0; i < jsonList.length(); i++) {
                                JSONObject ja = jsonList.getJSONObject(i);
                                //  x=i;
                                //  makeToast(ja.toString());
                                String p = ja.getString("product_id");
                                Log.e("pro",p+""+currentItem.getId());
                                if (currentItem.getId().equals(p+"")){
                                    holder.remove.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    holder.remove.setVisibility(View.INVISIBLE);
                                }
                            }
                        }

                        holder.remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressDialog.show();
                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                String url = "https://trendingstories4u.com/android_app/product_minus_shop_details.php?product_id="+currentItem.getId()+"&user_id="+sharedPreferences.getString(user_id,null)+"&booking_amount="+currentItem.getOffer_price();
                                Log.e("car_ad",url);
                                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        Log.e("response",response);
                                        //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();

                                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, Hotel_item_details.class);
                                        context.startActivity(intent);
                                        ((Activity) context).finish();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progressDialog.dismiss();
                                    }
                                });
                                requestQueue.add(stringRequest1);
                            }
                        });
                    }

                } catch (Throwable throwable) {
                    // dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    Log.e("Parsing problem", "Not parsing", throwable);
                    //makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                //  Toast.makeText(Shop_details.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequestss);
        sharedPreferences3 = context.getSharedPreferences(Category_MYPRIF, Context.MODE_PRIVATE);
        holder.booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                // Log.e("ids",sharedPreferences.getBoolean(status,false));
                String url = "https://trendingstories4u.com/android_app/add_to_cart_ecm.php";
                Log.e("URL", url);
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait ! Your booking is underprocess");
                //progressDialog.setCancelable(true);
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Ut", response);
                        progressDialog.dismiss();
                        //  progressDialog.dismiss();
                        Log.e("response", response);
                        Intent intent = new Intent(context, Mobile_items.class);
                        intent.putExtra("category", sharedPreferences3.getString(category_item,null));
                        Toast.makeText(context, "Successfully added into your cart", Toast.LENGTH_SHORT).show();
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        //Toast.makeText(Payment.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Response failure", Toast.LENGTH_SHORT).show();
                        //makeToast("Server linking failed !!! Check your Internet Connection.");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        // Adding All values to Params.
                        //  params.put("alternate", alter);
                        params.put("user_id", currentItem.getUser_ids());
                        params.put("product_id", currentItem.getId());
                        params.put("f_image", currentItem.getFront_img());
                        params.put("location", add + "");
                        params.put("cus_id", sharedPreferences.getString(user_id, null));
                        params.put("booking_amount", currentItem.getOffer_price());
                        params.put("van_name", currentItem.getName());
                        params.put("gst", "0");
                        params.put("sgst", "0");
                        params.put("all_p_amounts", "324");
                        params.put("atrribute",currentItem.getAttribute());
                        params.put("item_category",currentItem.getDis());
                        params.put("min_order",sharedPreferences2.getString(min_order,null));
                        params.put("wait_detail",currentItem.getWait_details());
                        params.put("delivery_chatge","20");

                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mobiles_item_items.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView van_name;
        ImageView imageView;
        TextView area_name;
        TextView van_model;
        Button booking;
        TextView km;
        TextView percent;
        RatingBar rating;
        TextView view_details;
        LinearLayout p_share;
        LinearLayout l_out;
        LinearLayout full;
        TextView price;
        TextView offer_price;
        TextView i_category;
        TextView wait_details;
        Button remove;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            van_name = itemView.findViewById(R.id.van_name);
            area_name = itemView.findViewById(R.id.area_name);
            booking = itemView.findViewById(R.id.booking);
            van_model = itemView.findViewById(R.id.model);
            imageView = itemView.findViewById(R.id.image);
            p_share = itemView.findViewById(R.id.p_share);
            full=itemView.findViewById(R.id.full);
            remove=itemView.findViewById(R.id.remove);
            price=itemView.findViewById(R.id.price);
            km = itemView.findViewById(R.id.km);
            rating = itemView.findViewById(R.id.rating);
            offer_price=itemView.findViewById(R.id.offer_price);
            i_category=itemView.findViewById(R.id.i_category);

            l_out = itemView.findViewById(R.id.l_out);
            //l_outs = itemView.findViewById(R.id.l_outs);
            view_details = itemView.findViewById(R.id.view_details);


            percent = itemView.findViewById(R.id.percent);
            wait_details=itemView.findViewById(R.id.wait_details);

        }
    }

    public Mobile_item_adapter(Context context, ArrayList<Mobiles_item_item> mobiles_item_items, Boolean x) {
        this.context = context;
        // d = new Domain();
        this.mobiles_item_items = mobiles_item_items;
        this.x = x;
    }


}

