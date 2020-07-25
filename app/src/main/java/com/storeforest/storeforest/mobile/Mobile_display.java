package com.storeforest.storeforest.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Verify_otp;
import com.storeforest.storeforest.hotel.Hotel_list_adapter;
import com.storeforest.storeforest.hotel.Hotel_list_display;
import com.storeforest.storeforest.hotel.Hotel_list_item;
import com.storeforest.storeforest.image_slider.Image_slider_adapter;
import com.storeforest.storeforest.image_slider.Image_slider_item;
import com.storeforest.storeforest.mobile.mobiles_item.Mobile_items;
import com.storeforest.storeforest.shop_info.Display_shop;
import com.storeforest.storeforest.shop_info.Display_shop_Adapter;
import com.storeforest.storeforest.shop_info.Kirana_stores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.MainActivity.user_lat;
import static com.storeforest.storeforest.MainActivity.user_lng;

public class Mobile_display extends AppCompatActivity {
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
        setContentView(R.layout.mobile_display);
        setTitle("Electronic Shop");
        CardView mobile = findViewById(R.id.mobile);
        CardView m_cover = findViewById(R.id.m_cover);
        CardView m_accesories = findViewById(R.id.m_accesories);
        CardView speaker = findViewById(R.id.speaker);
        CardView powerbank = findViewById(R.id.powerbank);
        CardView data_storage = findViewById(R.id.data_storage);
        CardView monitor = findViewById(R.id.monitor);
        CardView entairment = findViewById(R.id.entairment);
        CardView health_care=findViewById(R.id.health_care);
        sharedPreferences = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        sharedPreferences1 =getSharedPreferences(Verify_otp.MyPref, Context.MODE_PRIVATE);

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Mobile");
                startActivity(intent);
            }
        });

        m_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Mobile cover & Screen Guard");
                startActivity(intent);
            }
        });

        m_accesories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Mobile Accessories");
                startActivity(intent);
            }
        });

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Headphone & speaker");
                startActivity(intent);
            }
        });

        powerbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Powerbank");
                startActivity(intent);
            }
        });

        data_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Data Storage");
                startActivity(intent);
            }
        });

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Printer,Monitor & more");
                startActivity(intent);
            }
        });
        entairment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Home Entertainment");
                startActivity(intent);
            }
        });
        health_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mobile_display.this, Mobile_items.class);
                intent.putExtra("category", "Health care Devices");
                startActivity(intent);
            }
        });
        final ProgressDialog   progressDialog=new ProgressDialog(Mobile_display.this);
        /*Slider start*/
        RequestQueue queue = Volley.newRequestQueue(Mobile_display.this);
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
                        layoutManager = new LinearLayoutManager(Mobile_display.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Mobile_display.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new Image_slider_adapter(Mobile_display .this, image_slider_items,false);
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
