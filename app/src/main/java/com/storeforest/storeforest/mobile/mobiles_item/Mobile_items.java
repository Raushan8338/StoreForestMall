package com.storeforest.storeforest.mobile.mobiles_item;

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
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.addTocart.Display_cart_details;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_adapter;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_details;
import com.storeforest.storeforest.hotel.hotel_item_list.Hotel_item_details_items;
import com.storeforest.storeforest.shop_item_display.Shop_details_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.Shop_Category.Shop_Category_adapter.MyPrefid;
import static com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter.Mydetailscategory;
import static com.storeforest.storeforest.Shop_item_category.Shopdetail_category_adapter.category_value;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.userId;

public class Mobile_items extends AppCompatActivity {
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
    ArrayList<Mobiles_item_item> mobiles_item_items;
    SharedPreferences category_items;
    SharedPreferences sharedPreferences3;
    Button join;
    ProgressDialog progressDialog;
    Dialog dialog;
    EditText searchEdit;
    TextView amountt;
    Button proced;
    CardView plan_detail;
    ImageView image;
    ArrayList<Mobiles_item_item>searchProductList = new ArrayList<>();
    public static final String Category_MYPRIF = "Category_MYPRIF";
    public static final String category_item = "category_item";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_items);

        plan_detail=findViewById(R.id.plan_detail);
        image=findViewById(R.id.image);
        searchEdit=findViewById(R.id.searchEdit);
        proced=findViewById(R.id.proceed);
        plan_detail.setVisibility(View.GONE);
        Domain d=new Domain();
        final TextView no_text=findViewById(R.id.no_text);
        amountt=findViewById(R.id.amountt);
        category_items=getSharedPreferences(Mydetailscategory, Context.MODE_PRIVATE);
        setTitle(getIntent().getStringExtra("category"));
        final TextView cart_badge=findViewById(R.id.cart_badge);
        progressDialog=new ProgressDialog(this);
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        sharedPreferences1 =getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(this);
        sharedPreferences3 = getSharedPreferences(Category_MYPRIF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences3.edit();
        editor.putString(category_item, getIntent().getStringExtra("category"));
        editor.apply();


       /* Picasso.get().load(d.getProfile_img() + sharedPreferences1.getString(shop_image,null))
                .fit()
                .centerCrop()
                .into(image);*/
        // progressBar.setVisibility(View.INVISIBLE);
        String url ="https://trendingstories4u.com/android_app/ecm_display_product.php";
         url=url.replaceAll(" ", "%20");
        Log.e("URL",url);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method .POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response",response);

                    progressDialog.dismiss();
                    // dialog.dismiss();, `useer_id`, `item_name`, `item_category`, `price`, `item_image`, `date_time`, `status
                    //progressBar.setVisibility(View.INVISIBLE);
                    JSONObject obj = new JSONObject(response);
                    //  makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        //  makeToast(jsonList.length()+"");
                        int x=0;
                        mobiles_item_items = new ArrayList<>();
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
                            String 	description=j.getString("description");
                            String 	return_policy=j.getString("return_policy");
                            // String book=j.getString("booking");
                            mobiles_item_items.add(new Mobiles_item_item(name,id,image,price,status,dis,user_ids,offer_price,attribute,wait_details,description,return_policy));
     }
                        recyclerView = findViewById(R.id.car_recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Mobile_items.this);
                        adapter = new Mobile_item_adapter(Mobile_items.this, mobiles_item_items,true);
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
                Toast.makeText(Mobile_items.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("category_name",getIntent().getStringExtra("category"));

                return params;
            }
        };
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
                                Intent intent=new Intent(Mobile_items.this, Display_cart_details.class);
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
                Toast.makeText(Mobile_items.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequestss);

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
            for (int i = 0; i < mobiles_item_items.size(); i++)

                if ((mobiles_item_items.get(i).getName()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchProductList.add(mobiles_item_items.get(i));
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
            for (int i = 0; i < mobiles_item_items.size(); i++) {
                searchProductList.add(mobiles_item_items.get(i));
            }
        }

        setProductsData();
    }
    private void setProductsData() {
        try {
            progressDialog.dismiss();

            adapter = new Mobile_item_adapter(Mobile_items.this, searchProductList, false);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

    }
    public void makeToast(String str) {
        Toast.makeText(Mobile_items.this, str, Toast.LENGTH_SHORT).show();
    }
}
