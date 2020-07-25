package com.storeforest.storeforest.second_hand_product.second_hand_item;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.order_details.Shopper_order_details.BOOKPREF;

public class Second_hand_product_item extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    ImageView no_bike;
    TextView no_text;
    TextView text_sorry;
    ArrayList<Second_hand_item> second_hand_items;
    Button join;
    Dialog dialog;
    EditText searchEdit;
    TextView textCartItemCount;
    TextView notification;
    int mCartItemCount = 10;
    SharedPreferences sharedPreferences1;
    TextView info;
    ArrayList<Second_hand_item> searchProductList = new ArrayList<Second_hand_item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_hand_product_item);
        no_text=findViewById(R.id.no_text);
        setTitle(getIntent().getStringExtra("category"));
        Domain d = new Domain();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait !");
        progressDialog.show();
        searchEdit=findViewById(R.id.searchEdit);
        no_text.setVisibility(View.INVISIBLE);
        sharedPreferences = getSharedPreferences(BOOKPREF, Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(Second_hand_product_item.this);
        sharedPreferences1 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        // join.setVisibility(View.INVISIBLE);

        String url ="https://trendingstories4u.com/android_app/display_second_hand_product.php?category="+getIntent().getStringExtra("category");
        url=url.replaceAll(" ", "%20");
        Log.e("URL",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //  dialog.dismiss();
                    progressDialog.dismiss();
                    final JSONObject obj = new JSONObject(response);
                    if (obj.getString("result").equals("true")) {
                        JSONArray jsonList = obj.getJSONArray("series");
                        // makeToast(jsonList.length()+"");
                        int x=0;
                        second_hand_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                            JSONObject j=ja.getJSONObject("data");
                            Log.e("response",j.toString());
                            String  id = j.getString("id");
                            String  user_id = j.getString("user_id");
                            String brand = j.getString("brand");
                            String title = j.getString("title");
                            String address=j.getString("address");
                            String describe=j.getString("describe");
                            String contact_no=j.getString("contact_no");
                            String name=j.getString("name");
                            String p_image=j.getString("p_image");
                            String image=j.getString("image");
                            String status=j.getString("status");
                            String price=j.getString("price");
                            second_hand_items.add(new Second_hand_item(id,user_id,brand,title,address,describe,contact_no,name,p_image,image,status,price));

                            recyclerView=findViewById(R.id.recyclerview);
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(Second_hand_product_item.this);
                            adapter = new Second_hand_adapter(Second_hand_product_item.this, second_hand_items,true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);


                        }
                        if (obj.getString("count").equals("0")) {
                            no_text.setVisibility(View.VISIBLE);
                            no_text.setText("No Item Available");
                        }
                        //  makeToast(x+" ");
                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
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
                Toast.makeText(Second_hand_product_item.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
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
            for (int i = 0; i < second_hand_items.size(); i++)

                if ((second_hand_items.get(i).getBrand() + second_hand_items.get(i).getTitle()).toLowerCase().contains(s.toLowerCase().trim())) {
                    searchProductList.add(second_hand_items.get(i));
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
            searchProductList = new ArrayList<Second_hand_item>();
            for (int i = 0; i < second_hand_items.size(); i++) {
                searchProductList.add(second_hand_items.get(i));
            }
        }

        setProductsData();


    }
    private void setProductsData() {

        adapter = new Second_hand_adapter(Second_hand_product_item.this, searchProductList,false);
        recyclerView.setAdapter(adapter);


    }
    public void makeToast(String str){
        Toast.makeText(Second_hand_product_item.this, str, Toast.LENGTH_SHORT).show();

    }

}