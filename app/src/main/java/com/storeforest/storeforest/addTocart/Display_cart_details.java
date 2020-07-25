package com.storeforest.storeforest.addTocart;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Add_address;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.payment.Payment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.Verify_otp.MyPref;

public class Display_cart_details extends AppCompatActivity {

    ProgressBar progressBar;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    ImageView no_product;
    TextView no_text;
    final int UPI_PAYMENT = 0;
    Button proceed;
    TextView  amount;
    TextView text_sorry;
    Button join;
    TextView item_count;
    Dialog dialog;
    private static final int TEZ_REQUEST_CODE = 123;
    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    EditText amounts, note, name, upivirtualid;
    private static final int PHONEPE_REQUEST = 123;
    private static final String PHONEPE_PACKAGE_NAME = "com.phonepe.app";
    private static final int BHIM_REQUEST = 123;
    private static final String BHIM_PACKAGE_NAME = "in.org.npci.upiapp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_cart_details);
        //  Domain d = new Domain();
        no_product = findViewById(R.id.no_product);
        no_text = findViewById(R.id.no_text);
        proceed = findViewById(R.id.proceed);
        no_product.setVisibility(View.INVISIBLE);
        amount = findViewById(R.id.amountt);
         item_count=findViewById(R.id.counts);
         final TextView shop_name=findViewById(R.id.shop_name);
        final TextView delivery_charge=findViewById(R.id.delivery_charge);
        final TextView check_am=findViewById(R.id.check_am);
        // sharedPreferences = getSharedPreferences(BOOKPREF, Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(Display_cart_details.this);
        recyclerView = findViewById(R.id.recyclerview);
        setTitle("Cart Details");
        progressDialog=new ProgressDialog(Display_cart_details.this);
        // Log.e("op",getIntent().getStringExtra("hello"));
       sharedPreferences1 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        String url = "https://trendingstories4u.com/android_app/display_cart_item.php?user_id=" +sharedPreferences1.getString(user_id,null) ;
        url = url.replaceAll(" ", "%20");
        Log.e("URL", url);
        progressDialog.show();
        progressDialog.setMessage("Please wait !");
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    //dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    final JSONObject obj = new JSONObject(response);
                      //makeToast(obj.toString());
                    if (obj.getString("tot").equals("null")){
                        amount.setText("Rs.0");
                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Display_cart_details.this, "No any Product Found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        amount.setText("Rs."+obj.getString("tot"));
                        check_am.setText(obj.getString("tot"));
                        shop_name.setText(obj.getString("van_name"));
                       // Toast.makeText(Display_cart_details.this, obj.getString("count"), Toast.LENGTH_SHORT).show();
                        item_count.setText(obj.getString("count"));
                        delivery_charge.setText(obj.getString("delivery_charge"));
                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if (obj.getString("min").trim().length() > obj.getString("tot").length()) {
                                        amount.setText("Rs." + obj.getString("tot"));
                                        Toast.makeText(Display_cart_details.this, "Min orer of "+obj.getString("min"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        String urls = "https://trendingstories4u.com/android_app/check_address.php?user_id=" +sharedPreferences1.getString(user_id,null) ;
                                        RequestQueue queues = Volley.newRequestQueue(Display_cart_details.this);
                                        StringRequest stringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                  //  Log.e("url",response);
                                                    JSONObject obj = new JSONObject(response);
                                                    //res.setText(obj.getString("message"));
                                                    if (obj.getString("result").equals("true")) {
                                                        JSONObject js = obj.getJSONObject("series");
                                                        Log.e("rel",js.getString("status"));
                                                        if (js.getString("status").equals("0")){
                                                            Toast.makeText(Display_cart_details.this, "You have not added address", Toast.LENGTH_SHORT).show();
                                                            Intent intent=new Intent(Display_cart_details.this, Add_address.class);
                                                            startActivity(intent);
                                                        }
                                                        else  if (js.getString("status").equals("1")){
                                                            Toast.makeText(Display_cart_details.this, "You have ", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Display_cart_details.this, Payment.class);
                                                            intent.putExtra("pincode",js.getString("pincode"));
                                                            intent.putExtra("house_no",js.getString("house_no"));
                                                            intent.putExtra("landmark",js.getString("landmark"));
                                                            intent.putExtra("street_name",js.getString("street_name"));
                                                            intent.putExtra("apartment_no",js.getString("apartment_no"));
                                                            intent.putExtra("amount", check_am.getText().toString());
                                                            intent.putExtra("count_item", item_count.getText().toString());
                                                            intent.putExtra("delivery_charge", delivery_charge.getText().toString());
                                                            intent.putExtra("shop_name",shop_name.getText().toString());
                                                            startActivity(intent);
                                                        }

                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                //  dialog.dismiss();
                                                //progressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(Display_cart_details.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        queues.add(stringRequests);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    if (obj.getString("result").equals("true")) {
                        //
                        JSONArray jsonList = obj.getJSONArray("series");
                        //  makeToast(jsonList.length()+"");
                        int x = 0;
                        final ArrayList<Display_cart_item> display_cart_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                          //  makeToast(ja.toString());
                            String p = ja.getString("product_id");
                            String b = ja.getString("booking_amount");
                            String g = ja.getString("gst");
                            String c = ja.getString("cgst");
                            String f = ja.getString("front_image");
                            String v=ja.getString("van_name");
                            String bid=ja.getString("b_id");
                            String pay=ja.getString("payable_amount");
                            String location=ja.getString("location");
                            String atrribute=ja.getString("atrribute");
                            String item_category=ja.getString("item_category");
                            String wait_details=ja.getString("wait_details");
                            String quantity=ja.getString("quantity");
                            String total_a=ja.getString("total_a");
                            display_cart_items.add(new Display_cart_item(p,b,g,c,f,v,bid,pay,location,atrribute,item_category,wait_details,quantity,total_a));

                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(Display_cart_details.this);
                            adapter = new Display_car_adapter(Display_cart_details.this, display_cart_items);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        }
                        if (obj.getString("count").equals("0")){

                            no_product.setVisibility(View.VISIBLE);
                        //    no_text.setText("Cart is Empty");
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
              //  dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Display_cart_details.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void makeToast(String str) {
        Toast.makeText(Display_cart_details.this, str, Toast.LENGTH_SHORT).show();
    }
}