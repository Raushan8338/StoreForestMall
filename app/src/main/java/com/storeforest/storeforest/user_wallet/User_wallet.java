package com.storeforest.storeforest.user_wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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


public class User_wallet extends AppCompatActivity {
    TextView amount;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    Button pay;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView amountr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_wallet);
        Domain d=new Domain();
        final TextView current_amount=findViewById(R.id.current_amount);
        RequestQueue requestQueue= Volley.newRequestQueue(User_wallet.this);
        progressDialog=new ProgressDialog(User_wallet.this);
        progressDialog.show();
        setTitle("Wallet History");
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        String url="https://trendingstories4u.com/android_app/user_wallet_history.php?user_id="+sharedPreferences.getString(user_id,null);
        Log.e("URL",url);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                //makeToast(response);
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.e("Response",response);
                    if (obj.getString("result").equals("true")) {
                        //makeToast(obj.toString());
                        if (obj.getString("balance").equals("null")){
                            current_amount.setText("0.0");
                        }
                        else{
                            current_amount.setText(obj.getString("balance"));
                        }

                        JSONArray ja = obj.getJSONArray("series");
                        final ArrayList<User_wallet_item> user_wallet_items = new ArrayList<>();
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject job = ja.getJSONObject(i);
                            user_wallet_items.add(new User_wallet_item("0",
                                    job.getString("date_time"),
                                    job.getString("deposited_balance"),
                                    job.getString("deducted_balance"),
                                    job.getString("current_balance"),
                                    job.getString("transaction_detail"),
                                    job.getString("bank_name")+job.getString("acc_no"),
                                    job.getString("id")));
                        }
                        recyclerView = findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                       // Log.e("Count : ", ja.length() + " Hii" + wallet_lists.size());
                        layoutManager = new LinearLayoutManager(User_wallet.this);
                        adapter = new User_wallet_adapter(User_wallet.this, user_wallet_items);
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
                makeToast("Server linking failed !!! Check your Internet Connection.");
            }
        });
        requestQueue.add(stringRequest);

    }
    public void makeToast(String str){
        Toast.makeText(User_wallet.this, str, Toast.LENGTH_SHORT).show();
    }
}
