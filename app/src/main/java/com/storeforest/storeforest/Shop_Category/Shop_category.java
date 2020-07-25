package com.storeforest.storeforest.Shop_Category;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.storeforest.storeforest.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Shop_category extends AppCompatActivity {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    ImageView no_bike;
    private Shop_Category_adapter adapters;
    TextView no_text;
    TextView text_sorry;
    ArrayList<Shop_category_item> shop_category_items;
    Button join;
    ProgressDialog progressDialog;
    Dialog dialog;
    EditText searchEdit;
    ImageView shp_empty;
    ArrayList<Shop_category_item>searchProductList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_category);
        RequestQueue queue = Volley.newRequestQueue(Shop_category.this);
        searchEdit=findViewById(R.id.searchEdit);
        String url ="https://trendingstories4u.com/android_app/shop_category.php";
        url=url.replaceAll(" ", "%20");
        progressDialog=new ProgressDialog(Shop_category.this);
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
                        shop_category_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                            JSONObject j=ja.getJSONObject("data");
                            String  id = j.getString("id");
                            String  category_name = j.getString("category_name");
                            String category = j.getString("category");
                            String image = j.getString("image");
                            String value=j.getString("value");
                            shop_category_items.add(new Shop_category_item(id,category_name,category,image,value));

                            recyclerView = findViewById(R.id.car_recyclerview);
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(Shop_category.this);
                            adapter = new Shop_Category_adapter(Shop_category.this, shop_category_items,true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

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
             //   dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Shop_category.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
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
            for (int i = 0; i < shop_category_items.size(); i++)

                if ((shop_category_items.get(i).getCategory()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchProductList.add(shop_category_items.get(i));
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
            for (int i = 0; i < shop_category_items.size(); i++) {
                searchProductList.add(shop_category_items.get(i));
            }
        }

        setProductsData();


    }
    private void setProductsData() {
        try {
            progressDialog.dismiss();

            adapter = new Shop_Category_adapter(Shop_category.this, searchProductList, false);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

    }
    public void makeToast(String str) {
        Toast.makeText(Shop_category.this, str, Toast.LENGTH_SHORT).show();
    }

}