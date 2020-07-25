package com.storeforest.storeforest.second_hand_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.squareup.picasso.Picasso;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.second_hand_product.sec_image_slider.Sec_image_slider_adapter;
import com.storeforest.storeforest.second_hand_product.sec_image_slider.Sec_image_slider_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Second_hand_product_details extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Sec_image_slider_adapter adapters;
    ArrayList<Sec_image_slider_item> sec_image_slider_items;

    TextView item_name;
    TextView item_brand;
    TextView item_price;
    TextView description;
    TextView name;
    TextView contact_no;
    TextView address;
    SharedPreferences sharedPreferences2;
    ImageView image_v;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_hand_product_details);

        item_name=findViewById(R.id.item_name);
        item_brand=findViewById(R.id.item_brand);
        item_price=findViewById(R.id.item_price);
        description=findViewById(R.id.description);
        name=findViewById(R.id.name);
        contact_no=findViewById(R.id.contact_no);
        address=findViewById(R.id.address);
        setTitle(getIntent().getStringExtra("item_name"));
        item_name.setText("Product Name"+" : "+getIntent().getStringExtra("item_name"));
        item_brand.setText("Product Brand"+" : "+getIntent().getStringExtra("item_brand"));
        item_price.setText("Product price"+" : "+getIntent().getStringExtra("item_price"));
        description.setText("Description"+" : "+getIntent().getStringExtra("deescription"));
        name.setText("Seller Name"+" : "+getIntent().getStringExtra("name"));
        contact_no.setText("Contact No"+" : "+getIntent().getStringExtra("contact_no"));
        address.setText("Address"+" : "+getIntent().getStringExtra("address"));
        contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getIntent().getStringExtra("contact_no")));
                startActivity(intent);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(Second_hand_product_details.this);
        //sharedPreferences2 = getSharedPreferences(MyPrefcitylnt, Context.MODE_PRIVATE);
        String url ="https://trendingstories4u.com/android_app/sec_image_slider.php?p_id="+getIntent().getStringExtra("p_id");
        url=url.replaceAll(" ", "%20");
        Log.e("Response",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    //    progressDialog.dismiss();
                    //makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        final ArrayList<Sec_image_slider_item> sec_image_slider_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            Log.e("jsonList", ja.toString());
                            String image = ja.getString("image");
                            String description = ja.getString("id");
                            sec_image_slider_items.add(new Sec_image_slider_item(image,description));
                            recyclerView=findViewById(R.id.recyclerview);
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(Second_hand_product_details.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Second_hand_product_details.this, LinearLayoutManager.HORIZONTAL, true));
                            adapter = new Sec_image_slider_adapter(Second_hand_product_details.this, sec_image_slider_items,false);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                } catch (Throwable throwable) {
                    //     progressDialog.dismiss();
                    Log.e("Parsing problem", "Not parsing", throwable);
                    //makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        queue.add(stringRequest);

    }
}
