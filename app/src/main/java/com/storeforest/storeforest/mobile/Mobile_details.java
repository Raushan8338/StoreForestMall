package com.storeforest.storeforest.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.image_slider.Image_slider_adapter;
import com.storeforest.storeforest.image_slider.Image_slider_item;
import com.storeforest.storeforest.mobile.mobiles_item.Mobile_items;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.MainActivity.user_lat;
import static com.storeforest.storeforest.MainActivity.user_lng;

public class Mobile_details extends AppCompatActivity {
SharedPreferences sharedPreferences;
    TextView amountt;
    Button proced;
    CardView plan_detail;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_details);
        TextView title=findViewById(R.id.title);
        TextView price=findViewById(R.id.price);
        TextView description=findViewById(R.id.description);
        proced=findViewById(R.id.proceed);
        plan_detail=findViewById(R.id.plan_detail);
        TextView return_policy=findViewById(R.id.return_policy);
        plan_detail.setVisibility(View.GONE);
        Domain d=new Domain();
        final TextView no_text=findViewById(R.id.no_text);
        amountt=findViewById(R.id.amountt);
        setTitle(getIntent().getStringExtra("title"));
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        final TextView cart_badge=findViewById(R.id.cart_badge);
        title.setText(getIntent().getStringExtra("title"));
        price.setText("Rs."+getIntent().getStringExtra("price"));
        description.setText(getIntent().getStringExtra("description"));
        return_policy.setText(getIntent().getStringExtra("return_policy"));

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlss = "https://trendingstories4u.com/android_app/display_cart_item.php?user_id="+sharedPreferences.getString(user_id,null) ;
       // url = url.replaceAll(" ", "%20");
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
                                Intent intent=new Intent(Mobile_details.this, Display_cart_details.class);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (Throwable throwable) {
                    // dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    Log.e("Parsing problem", "Not parsing", throwable);
                   // makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Mobile_details.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequestss);
        final ProgressDialog progressDialog=new ProgressDialog(Mobile_details.this);
        /*Slider start*/
        RequestQueue queues = Volley.newRequestQueue(Mobile_details.this);
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
                        layoutManager = new LinearLayoutManager(Mobile_details.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Mobile_details.this, LinearLayoutManager.HORIZONTAL, true));
                        adapter = new Image_slider_adapter(Mobile_details .this, image_slider_items,false);
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
        queues.add(stringRequest);
    }
}