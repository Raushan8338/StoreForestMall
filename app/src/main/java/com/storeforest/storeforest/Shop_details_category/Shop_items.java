package com.storeforest.storeforest.Shop_details_category;

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
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.shop_details_slider.Shop_details_Image_slider_item;
import com.storeforest.storeforest.shop_details_slider.ShopdetailsImage_slider_adapter;
import com.storeforest.storeforest.shop_item_display.Shop_details_adapter;
import com.storeforest.storeforest.shop_item_display.Shop_details_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.Shop_Category.Shop_Category_adapter.MyPrefid;
import static com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter.Mydetailscategory;
import static com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter.category_name;
import static com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter.category_value;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.userId;

public class Shop_items extends AppCompatActivity {
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
    ArrayList<Shop_item_items> shop_item_items;
    SharedPreferences category_items;
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
        setContentView(R.layout.shop_items);
        plan_detail=findViewById(R.id.plan_detail);
        image=findViewById(R.id.image);
        proced=findViewById(R.id.proceed);
        plan_detail.setVisibility(View.GONE);
        Domain d=new Domain();
        final TextView no_text=findViewById(R.id.no_text);
        amountt=findViewById(R.id.amountt);
        category_items=getSharedPreferences(Mydetailscategory, Context.MODE_PRIVATE);
        setTitle(category_items.getString(category_name,null));
        final TextView cart_badge=findViewById(R.id.cart_badge);
        progressDialog=new ProgressDialog(this);
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        sharedPreferences1 =getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(this);

       /* Picasso.get().load(d.getProfile_img() + sharedPreferences1.getString(shop_image,null))
                .fit()
                .centerCrop()
                .into(image);*/
        // progressBar.setVisibility(View.INVISIBLE);
        String url ="https://trendingstories4u.com/android_app/shop_item_details.php?user_id="+sharedPreferences1.getString(userId,null)+"&category_name="+category_items.getString(category_value,null);
        url=url.replaceAll(" ", "%20");
        Log.e("URL",url);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    // dialog.dismiss();, `useer_id`, `item_name`, `item_category`, `price`, `item_image`, `date_time`, `status
                    //progressBar.setVisibility(View.INVISIBLE);
                    JSONObject obj = new JSONObject(response);
                    //  makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        //  makeToast(jsonList.length()+"");
                        int x=0;
                        shop_item_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                            JSONObject j=ja.getJSONObject("data");
                            //    String  km = ja.getString("date_time");
                            String name = j.getString("item_name");
                            String id=j.getString("id");
                            String image=j.getString("item_image");
                            String price=j.getString("price");
                            String status=j.getString("status");
                            String dis=j.getString("item_category");
                            String user_ids=j.getString("useer_id");
                            String offer_price=j.getString("offer_price");
                            String 	attribute=j.getString("attribute");
                            String 	wait_details=j.getString("wait_detail");
                            // String book=j.getString("booking");
                            shop_item_items.add(new Shop_item_items(name,id,image,price,status,dis,user_ids,offer_price,attribute,wait_details));




                        }
                        recyclerView = findViewById(R.id.car_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Shop_items.this);
                        adapter = new Shop_items_adapter(Shop_items.this, shop_item_items,true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        if (obj.getString("count").equals("0")) {
                            no_text.setText("Item is coming soon");
                        }
                        //  makeToast(x+" ");
                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
                    // dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    Log.e("Parsing problem", "Not parsing", throwable);
                    makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Shop_items.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
        String urlss = "https://trendingstories4u.com/android_app/display_cart_item.php?user_id="+sharedPreferences.getString(user_id,null) ;
        url = url.replaceAll(" ", "%20");
      //  Log.e("URL", urls);
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
                        cart_badge.setText(obj.getString("total_quentity"));
                        plan_detail.setVisibility(View.VISIBLE);
                        proced.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(Shop_items.this, Display_cart_details.class);
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
                Toast.makeText(Shop_items.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequestss);
        RequestQueue queuea = Volley.newRequestQueue(Shop_items.this);
        String urlsss ="https://trendingstories4u.com/android_app/imageshop_details_slider.php?user_id="+sharedPreferences1.getString(userId,null);
        url=url.replaceAll(" ", "%20");
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
                        final ArrayList<Shop_details_Image_slider_item> image_slider_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            Log.e("jsonList", ja.toString());
                            String image = ja.getString("image");
                            image_slider_items.add(new Shop_details_Image_slider_item(image));
                        }
                        recyclerView=findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Shop_items.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Shop_items.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new ShopdetailsImage_slider_adapter(Shop_items.this, image_slider_items,false);
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

    }
    public void makeToast(String str) {
        Toast.makeText(Shop_items.this, str, Toast.LENGTH_SHORT).show();
    }
}
