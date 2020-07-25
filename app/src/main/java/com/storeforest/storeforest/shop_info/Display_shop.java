package com.storeforest.storeforest.shop_info;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Verify_otp;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.image_slider.Image_slider_adapter;
import com.storeforest.storeforest.image_slider.Image_slider_item;
import com.storeforest.storeforest.order_details.Order_details;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.MainActivity.user_lat;
import static com.storeforest.storeforest.MainActivity.user_lng;

public class Display_shop extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    private Image_slider_adapter adapters;
    ArrayList<Image_slider_item> image_slider_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_shop);
        CardView fruits = findViewById(R.id.fruits);
        CardView dairy = findViewById(R.id.dairy);
        CardView snacks = findViewById(R.id.snacks);
        CardView noodles = findViewById(R.id.noodles);
        CardView clining = findViewById(R.id.clining);
        CardView beauty = findViewById(R.id.beauty);
        CardView eggs = findViewById(R.id.eggs);
        CardView babycare = findViewById(R.id.babycare);
        CardView kirana=findViewById(R.id.kirana);
        setTitle("Grocery Details");
        sharedPreferences = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        sharedPreferences1 =getSharedPreferences(Verify_otp.MyPref, Context.MODE_PRIVATE);

        kirana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Foodgrains and Oil");
                intent.putExtra("value", "kirana");
                startActivity(intent);
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Fruits & Vegitables");
                intent.putExtra("value", "fruits");
                startActivity(intent);
            }
        });

        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Dairy & Colddrinks");
                intent.putExtra("value", "dairy");
                startActivity(intent);
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Snacks & chocolate");
                intent.putExtra("value", "snacks");
                startActivity(intent);
            }
        });

        noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Noodles & Sauces");
                intent.putExtra("value", "noodles");
                startActivity(intent);
            }
        });

        clining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Clining & Household");
                intent.putExtra("value", "clining");
                startActivity(intent);
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Beauty & Persional care");
                intent.putExtra("value", "beauty");
                startActivity(intent);
            }
        });
        eggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Egg,Meet and fish");
                intent.putExtra("value", "eggs");
                startActivity(intent);
            }
        });
        babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Display_shop.this, Kirana_stores.class);
                intent.putExtra("title", "Baby Care");
                intent.putExtra("value", "babycare");
                startActivity(intent);
            }
        });
     final ProgressDialog   progressDialog=new ProgressDialog(Display_shop.this);
        /*Slider start*/
        RequestQueue queue = Volley.newRequestQueue(Display_shop.this);
        progressDialog.show();
        progressDialog.setMessage("Please Wait !");
        String url ="https://trendingstories4u.com/android_app/image_slider.php?lat="+sharedPreferences.getString(user_lat,null)+"&lng="+sharedPreferences.getString(user_lng ,null);
        url=url.replaceAll(" ", "%20");
        Log.e("Response",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    progressDialog.dismiss();
                    //makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        final ArrayList<Image_slider_item> image_slider_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            Log.e("jsonList", ja.toString());
                            String image = ja.getString("image");
                            image_slider_items.add(new Image_slider_item(image));
                        }
                        recyclerView=findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Display_shop.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Display_shop.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new Image_slider_adapter(Display_shop.this, image_slider_items,false);
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
        queue.add(stringRequest);

    }



    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
