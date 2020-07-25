package com.storeforest.storeforest.shop_item_display;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Shop_item_category.Shop_details_item_category;
import com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.shop_details_slider.Shop_details_Image_slider_item;
import com.storeforest.storeforest.shop_details_slider.ShopdetailsImage_slider_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.MyPrefid;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.shop_name;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.userId;

public class Shop_details extends AppCompatActivity {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    ImageView no_bike;
    private Shop_details_adapter adapters;
    TextView no_text;
    TextView text_sorry;
    ArrayList<Shop_details_item> shop_details_items;
    ArrayList<Shop_details_item_category> shop_details_item_categories;
    Button join;
    ProgressDialog progressDialog;
    Dialog dialog;
    EditText searchEdit;
    TextView amountt;
    Button proced;
    CardView plan_detail;
    ImageView image;
    ArrayList<Shop_details_item>searchProductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        plan_detail=findViewById(R.id.plan_detail);
        image=findViewById(R.id.image);
        proced=findViewById(R.id.proceed);
         plan_detail.setVisibility(View.GONE);
        Domain d=new Domain();
        amountt=findViewById(R.id.amountt);
        final TextView cart_badge=findViewById(R.id.cart_badge);
        progressDialog=new ProgressDialog(this);
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        sharedPreferences1 =getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        RequestQueue qq =  Volley.newRequestQueue(Shop_details.this);

      /*  String urls = "https://trendingstories4u.com/android_app/cart_length.php?user_id="+sharedPreferences.getString(user_id, null);
        Log.e("url : ", urls);
        StringRequest stringRequests = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js = new JSONObject(response);
                    cart_badge.setText(js.getString("count"));
                } catch (Throwable throwable){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("Error",error.toString()+" " +url);
                makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        qq.add(stringRequests);*/
        RequestQueue queue = Volley.newRequestQueue(this);
        getSupportActionBar().setTitle(sharedPreferences1.getString(shop_name,null));

       /* Picasso.get().load(d.getProfile_img() + sharedPreferences1.getString(shop_image,null))
                .fit()
                .centerCrop()
                .into(image);*/
        // progressBar.setVisibility(View.INVISIBLE);
        String urlss = "https://trendingstories4u.com/android_app/display_cart_item.php?user_id="+sharedPreferences.getString(user_id,null) ;

        StringRequest stringRequestss = new StringRequest(Request.Method.GET, urlss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject obj = new JSONObject(response);
                    Log.e("response",response);
                    if (obj.getString("count").equals("0")){
                        plan_detail.setVisibility(View.INVISIBLE);
                    }
                    else {
                        amountt.setText("Rs."+obj.getString("tot"));
                        plan_detail.setVisibility(View.VISIBLE);
                        cart_badge.setText(obj.getString("total_quentity"));
                        proced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(Shop_details.this, Display_cart_details.class);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (Throwable throwable) {
                    // dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    Log.e("Parsing problem", "Not parsing", throwable);
                    makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Shop_details.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequestss);
        RequestQueue queuea = Volley.newRequestQueue(Shop_details.this);
        String urlsss ="https://trendingstories4u.com/android_app/imageshop_details_slider.php?user_id="+sharedPreferences1.getString(userId,null);

        Log.e("Response",urlsss);
        StringRequest stringRequesta = new StringRequest(Request.Method.GET,urlsss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    progressDialog.dismiss();
                    //makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        final ArrayList<Shop_details_Image_slider_item> shop_details_image_slider_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            Log.e("jsonList", ja.toString());
                            String image = ja.getString("image");
                            shop_details_image_slider_items.add(new Shop_details_Image_slider_item(image));
                        }
                        recyclerView=findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Shop_details.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Shop_details.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new ShopdetailsImage_slider_adapter(Shop_details.this, shop_details_image_slider_items,false);
                        // recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
                    Log.e("Parsing problem", "Not parsing", throwable);
                    //   makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        queuea.add(stringRequesta);

        RequestQueue queueas = Volley.newRequestQueue(Shop_details.this);
        String urlssss ="https://trendingstories4u.com/android_app/shop_category.php?user_id="+sharedPreferences1.getString(userId,null);

        Log.e("Response",urlssss);
        StringRequest stringRequestas = new StringRequest(Request.Method.GET,urlssss, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    progressDialog.dismiss();
                    //makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        final ArrayList<Shop_details_item_category> shop_details_item_categories = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            String user_id =  ja.getString("user_id");
                            String c_image = ja.getString("image");
                            String c_name = ja.getString("category_name");
                            String c_value = ja.getString("category_value");

                            shop_details_item_categories.add(new Shop_details_item_category(user_id,c_image,c_name,c_value));
                        }
                        recyclerView=findViewById(R.id.recyclerviewcard);
                        recyclerView.setHasFixedSize(true);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(Shop_details.this,3,GridLayoutManager.VERTICAL,false);
                        //recyclerView.setLayoutManager(new LinearLayoutManager(Shop_details.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new Shopdetail_category_adapter(Shop_details.this, shop_details_item_categories,false);
                         recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(adapter);

                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
                    Log.e("Parsing problem", "Not parsing", throwable);
                    //   makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        queueas.add(stringRequestas);

    }
    public void makeToast(String str) {
        Toast.makeText(Shop_details.this, str, Toast.LENGTH_SHORT).show();
    }
}
