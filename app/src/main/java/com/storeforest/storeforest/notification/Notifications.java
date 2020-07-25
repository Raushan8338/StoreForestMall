package com.storeforest.storeforest.notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class Notifications extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView profile_completed;
    ImageView image;
    TextView dears;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView notification;
    TextView dateTime;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        setTitle("Notification");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Domain d=new Domain();
        final ImageView n_img=findViewById(R.id.notification_img);
        final TextView n_text=findViewById(R.id.notification_txt);
        n_img.setVisibility(View.INVISIBLE);
        n_text.setVisibility(View.INVISIBLE);
        RequestQueue queue =  Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.show();

        final SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        String url = "https://trendingstories4u.com/android_app/fetch_notification.php?user_id="+sharedPreferences.getString(user_id,null);
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
                        final ArrayList<NotificationItem> notificationItems = new ArrayList<>();
                        for (int i = 0; i < jsonList.length(); i++) {
                            JSONObject ja = jsonList.getJSONObject(i);
                            Log.e("jsonList", ja.toString());
                            String n = ja.getString("message");
                            String m=ja.getString("name");
                            String dt=ja.getString("dateTime");
                            String dti=ja.getString("id");
                            notificationItems.add(new NotificationItem(n,m,dt,dti));
                        }
                        if (obj.getString("count").equals("0")){
                            n_img.setVisibility(View.VISIBLE);
                            n_text.setVisibility(View.VISIBLE);
                        }
                        recyclerView=findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Notifications.this);
                        adapter = new NotificationAdapter(Notifications.this, notificationItems,false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        /*if (jsonList.getString("ride_start").equals("2")){
                            notification.setText("your booking is Not Started.Booking id"+jsonList.getString("b_id")+","+"to"+jsonList.getString("start_date")+"contact no 1234567890,9999999999,sdcsdcsdcscdcssdcscdcs.Thank you for.");
                        }
                        else if (jsonList.getString("ride_start").equals("3")) {
                            notification.setText("your booking is Running.Booking id"+jsonList.getString("b_id")+","+"to"+jsonList.getString("start_date")+"contact no 1234567890,9999999999,sdcsdcsdcscdcssdcscdcs.Thank you for.");
                        }
                        else if (jsonList.getString("ride_start").equals("4")) {
                            notification.setText("your booking is successfullly Complete.Booking id"+jsonList.getString("b_id")+","+"to"+jsonList.getString("start_date")+"contact no 1234567890,9999999999,sdcsdcsdcscdcssdcscdcs.Thank you for.");
                        }
                        else {
                            notification.setText("your booking is successfullly Cancel.Booking id"+jsonList.getString("b_id")+","+"to"+jsonList.getString("start_date")+"contact no 1234567890,9999999999,sdcsdcsdcscdcssdcscdcs.Thank you for.");

                        }*/
                    }
                } catch (Throwable throwable) {
                    progressDialog.dismiss();
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
        queue.add(stringRequest);

    }
    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
