package com.storeforest.storeforest.hotel;

import androidx.appcompat.app.AppCompatActivity;
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
import android.view.Menu;
import android.view.MenuItem;
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
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.shop_info.Display_shop_Adapter;
import com.storeforest.storeforest.shop_info.Display_shop_item;
import com.storeforest.storeforest.shop_info.Kirana_stores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;

public class Hotel_list_display extends AppCompatActivity {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    ImageView no_bike;
    private Display_shop_Adapter adapters;
    TextView no_text;
    TextView text_sorry;
    ArrayList<Hotel_list_item> hotel_list_items;
    Button join;
    ProgressDialog progressDialog;
    Dialog dialog;
    EditText searchEdit;
    TextView textCartItemCount;
    TextView notification;
    int mCartItemCount = 10;
    ImageView shp_empty;
    ArrayList<Hotel_list_item>searchProductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel__list_display);
        searchEdit=findViewById(R.id.searchEdit);
        progressDialog=new ProgressDialog(Hotel_list_display.this);
        setTitle(getIntent().getStringExtra("title"));
        sharedPreferences = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        String add = sharedPreferences.getString(address,null);
        RequestQueue queue = Volley.newRequestQueue(Hotel_list_display.this);
        no_text=findViewById(R.id.no_text);
        shp_empty=findViewById(R.id.shp_empty);
        shp_empty.setVisibility(View.INVISIBLE);
        // progressBar.setVisibility(View.INVISIBLE);
        String url ="https://trendingstories4u.com/android_app/shop_details.php?add="+add +"&category="+getIntent().getStringExtra("value");
        url=url.replaceAll(" ", "%20");
        Log.e("URL",url);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    // dialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    JSONObject obj = new JSONObject(response);
                    //  makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        //  makeToast(jsonList.length()+"");
                        int x=0;
                        hotel_list_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                            JSONObject j=ja.getJSONObject("data");
                            Log.e("log",j+"");
                            String  id = j.getString("id");
                            String  userid = j.getString("user_id");
                            String n = j.getString("shop_name");
                            String dn = j.getString("distance");
                            String k=j.getString("shop_image");
                            String sta=j.getString("status");
                            String dis=j.getString("rating");
                            String min=j.getString("min_order");
                            String address=j.getString("address");
                            String delivery_charge=j.getString("delivery_charge");
                            // String book=j.getString("booking");
                            hotel_list_items.add(new Hotel_list_item(id,userid,n,dn,k,sta,dis,min,address,delivery_charge));


                        }
                        recyclerView = findViewById(R.id.car_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Hotel_list_display.this);
                        adapter = new Hotel_list_adapter(Hotel_list_display.this, hotel_list_items,true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        if (obj.getString("count").equals("0")) {
                            shp_empty.setVisibility(View.VISIBLE);
                            no_text.setText("No Any Shop Available in Your Area");
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
                dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Hotel_list_display.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);


        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("text", editable.toString());
                searchVehicles(editable.toString());
            }
        });
    }
    private void searchVehicles(String s) {


        searchProductList.clear();

        // Toast.makeText(getActivity(), ""+productItems.size(), Toast.LENGTH_SHORT).show();

        if (s.length() > 0) {
            for (int i = 0; i < hotel_list_items.size(); i++)

                if ((hotel_list_items.get(i).getShop_name()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchProductList.add(hotel_list_items.get(i));
                    //Toast.makeText(getActivity(), ""+searchProductList.size(), Toast.LENGTH_SHORT).show();
                }


            if (searchProductList.size() < 1) {
                //no_bike.setImageResource(R.drawable.bike_no);
                //no_text.setText("No Bike Available");
                //text_sorry.setText("Sorry We are currently not available in your city. We are trying to be available in your city as soon as possible.If you also have a bike or car, please join us.");
                //join.setVisibility(View.VISIBLE);
            } else {

            }

        } else {
            searchProductList = new ArrayList<>();
            for (int i = 0; i < hotel_list_items.size(); i++) {
                searchProductList.add(hotel_list_items.get(i));
            }
        }

        setProductsData();
    }
    private void setProductsData() {
        try {
            progressDialog.dismiss();

            adapter = new Hotel_list_adapter(Hotel_list_display.this, searchProductList, false);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

    }


    public void makeToast(String str) {
        Toast.makeText(Hotel_list_display.this, str, Toast.LENGTH_SHORT).show();
    }

}