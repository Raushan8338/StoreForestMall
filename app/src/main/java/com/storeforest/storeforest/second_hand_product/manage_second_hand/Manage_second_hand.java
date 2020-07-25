package com.storeforest.storeforest.second_hand_product.manage_second_hand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.storeforest.storeforest.second_hand_product.second_hand_item.Second_hand_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.order_details.Order_details.BOOKPREF;

public class Manage_second_hand extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    ImageView no_bike;
    TextView no_text;
    TextView text_sorry;
    ArrayList<Manage_second_hand_item> manage_second_hand_items;
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
        setContentView(R.layout.manage_second_hand);
        no_text=findViewById(R.id.no_text);

        setTitle("Manage Product");
        Domain d = new Domain();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait !");
        progressDialog.show();
        searchEdit=findViewById(R.id.searchEdit);
        sharedPreferences = getSharedPreferences(BOOKPREF, Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(Manage_second_hand.this);
        sharedPreferences1 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        // join.setVisibility(View.INVISIBLE);
        no_text.setVisibility(View.INVISIBLE);

        String url ="https://trendingstories4u.com/android_app/manage_second_hand_product.php?user_id="+sharedPreferences1.getString(user_id,null);
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
                        manage_second_hand_items = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            //  x=i;
                            JSONObject j=ja.getJSONObject("data");
                           // Log.e("response",j.toString());
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
                            manage_second_hand_items.add(new Manage_second_hand_item(id,user_id,brand,title,address,describe,contact_no,name,p_image,image,status,price));

                            recyclerView=findViewById(R.id.recyclerview);
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(Manage_second_hand.this);
                            adapter = new Manage_second_hand_adapter(Manage_second_hand.this, manage_second_hand_items,true);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);


                        }
                        if (obj.getString("count").equals("0")){
                            no_text.setText("No Item Available");
                            no_text.setVisibility(View.VISIBLE);
                        }
                        //  makeToast(x+" ");
                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                    Log.e("Parsing problem", "Not parsing", throwable);
                    // makeToast("Something went wrong!!! Please try again.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Manage_second_hand.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
