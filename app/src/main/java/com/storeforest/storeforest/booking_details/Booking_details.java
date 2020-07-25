package com.storeforest.storeforest.booking_details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class Booking_details extends AppCompatActivity {
    TextView amount;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    Button pay;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView amountr;
    TextView no_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        Domain d = new Domain();
        no_record=findViewById(R.id.no_record);
        no_record.setVisibility(View.INVISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(Booking_details.this);
        progressDialog = new ProgressDialog(Booking_details.this);
        progressDialog.show();
        setTitle("Booking History");
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        String url = "https://trendingstories4u.com/android_app/user_booking_history.php?user_id="+sharedPreferences.getString(user_id,null);
        Log.e("URL", url);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //makeToast(response);
                try {
                    progressDialog.dismiss();
                    JSONObject obj = new JSONObject(response);

                    if (obj.getString("result").equals("true")) {
                        //makeToast(obj.toString());
                        JSONArray ja = obj.getJSONArray("series");
                        final ArrayList<Booking_details_item> booking_details_items = new ArrayList<>();
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject job = ja.getJSONObject(i);
                            //Log.e("reaponse",job.getString("user_id"));
                            String user_id=job.getString("user_id");
                            String total_item=job.getString("total_item");
                            String booking_status=job.getString("booking_status");
                            String shop_id=job.getString("shop_id");
                            String booking_amount=job.getString("booking_amount");
                            String delivery_charge=job.getString("delivery_charge");
                            String booking_date=job.getString("booking_date");
                            String shop_image=job.getString("shop_image");
                            String address=job.getString("address");
                            String shop_name=job.getString("shop_name");
                            String item_id=job.getString("b_item_id");
                            booking_details_items.add(new Booking_details_item(user_id,total_item,booking_status,shop_id,booking_amount,delivery_charge,booking_date,shop_image,address,shop_name,item_id));
                        }
                        if (obj.getString("count").equals("0")){
                            no_record.setVisibility(View.VISIBLE);
                        }

                        recyclerView = findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                        // Log.e("Count : ", ja.length() + " Hii" + wallet_lists.size());
                        layoutManager = new LinearLayoutManager(Booking_details.this);
                        adapter = new Booking_details_adapter(Booking_details.this, booking_details_items);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

                    }
                } catch (Throwable throwable) {

                    Log.e("Parsing problem", "Not parsing", throwable);
                    makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        requestQueue.add(stringRequest);

    }

    public void makeToast(String str) {
        Toast.makeText(Booking_details.this, str, Toast.LENGTH_SHORT).show();
    }
}