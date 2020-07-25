package com.storeforest.storeforest.order_details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;


public class Order_details extends AppCompatActivity {
    Button b_amount;
    TextView follow_direction;
    ImageView van_image;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    public static final String BOOKPREF = "BOOKPREF";
    Button booking;
    TextView no_record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        sharedPreferences1 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Domain d = new Domain();
        setTitle("History");
        RequestQueue queue =  Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(Order_details.this);
        no_record=findViewById(R.id.no_record);
        no_record.setVisibility(View.INVISIBLE);
        progressDialog.show();
        // progressBar.setVisibility(View.VISIBLE);*//*
        final SharedPreferences sharedPreferences;
        String url = "https://trendingstories4u.com/android_app/user_booking_details.php?user_id="+sharedPreferences1.getString(user_id,null)+"&item_id="+getIntent().getStringExtra("item_id");
        Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    //makeToast(obj.toString());
                    Log.e("url",obj+"");
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");

                        final ArrayList<Order_item> order_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject j = jsonList.getJSONObject(i);
                            String b_id = j.getString("b_id");
                            String booking_status=j.getString("booking_status");
                            String booking_amount=j.getString("booking_amount");
                            String payment_mode=j.getString("payment_mode");
                            String transaction_id=j.getString("transaction_id");
                            String item_name=j.getString("item_name");
                            String item_image=j.getString("item_image");
                            String wait_details=j.getString("wait_details");
                            String attribute=j.getString("attribute");
                            String b_item_id=j.getString("b_item_id");
                            String quantity=j.getString("quantity");
                            order_items.add(new Order_item(b_id,booking_status,booking_amount,payment_mode,transaction_id,item_name,item_image,wait_details,attribute,b_item_id,quantity));
                        }
                        if (obj.getString("count").equals("0")){
                            no_record.setVisibility(View.VISIBLE);
                        }

                        progressDialog.dismiss();
                        //  makeToast(booking_history_items.size()+"");
                        recyclerView = findViewById(R.id.bike_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Order_details.this);
                        adapter = new Order_adapter(Order_details.this, order_items);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        //  makeToast(x+" ");
                    }
                } catch (Throwable throwable) {
               //     progressBar.setVisibility(View.INVISIBLE);*//*
                    progressDialog.dismiss();
                    Log.e("Parsing problem", "Not parsing", throwable);
                   // makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                 //   progressBar.setVisibility(View.INVISIBLE);*//*
                //makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        queue.add(stringRequest);
    }
}
