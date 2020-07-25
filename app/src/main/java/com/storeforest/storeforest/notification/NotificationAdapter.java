package com.storeforest.storeforest.notification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.R;


import java.util.ArrayList;

import static com.storeforest.storeforest.Login.MyPref;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private ArrayList<NotificationItem> notificationItems;
    SharedPreferences sharedPreferences;
    private Context context;
    ProgressDialog progressDialog;



    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent, false);
        NotificationViewHolder lvh = new NotificationViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final NotificationItem currentItem = notificationItems.get(position);
        sharedPreferences = context.getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        holder.dear.setText("Dear" +" , "+ currentItem.getM()+"");
        holder.dateTime.setText(currentItem.getDt());
        holder.notification.setText(currentItem.getN());
        progressDialog=new ProgressDialog(context);
        holder.close_dailog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                String url = "https://trendingstories4u.com/android_app/notification_update.php?id="+currentItem.getDti();
                Log.e("car_ad",url);
                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("response",response);
                        //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, Notifications.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView dear;
        TextView notification;
        TextView dateTime;
        ImageView close_dailog;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            dear=itemView.findViewById(R.id.dear);
            notification=itemView.findViewById(R.id.notification);
            dateTime=itemView.findViewById(R.id.dateTime);
            close_dailog=itemView.findViewById(R.id.close_dailog);
        }
    }

    public NotificationAdapter(Context context, ArrayList<NotificationItem> notificationItems, boolean b){
        this.context=context;
        this.notificationItems=notificationItems;
    }
}
