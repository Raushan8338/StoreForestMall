package com.storeforest.storeforest.shop_info;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.Login;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.order_details.Order_details;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.mode;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.Login.user_name;

public class Profile_menu extends AppCompatActivity {
ImageView user_profile;
TextView user_names;
TextView order;
TextView playrating;
TextView switchtopartner;
TextView login;
SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_menu);
        user_profile=findViewById(R.id.user_profile);
        switchtopartner=findViewById(R.id.switchtopartner);
        user_names=findViewById(R.id.user_name);
        order=findViewById(R.id.order);
        playrating=findViewById(R.id.playrating);
        login=findViewById(R.id.login);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        user_names.setText(sharedPreferences.getString(user_name,null));
        playrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.ftstravel.ftatravel&hl=en  User Coupon code FTS_FRIEND and get 50 to 100 discount on every booking");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Profile_menu.this);
                builder.setMessage("Are you sure you want to logout")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent intent5=new Intent(Profile_menu.this, Login.class);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().commit();
                                startActivity(intent5);
                                finish();
                                recreate();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile_menu.this, Order_details.class);
                startActivity(intent);
            }
        });
        sharedPreferences1 =getSharedPreferences(MyPref, Context.MODE_PRIVATE);

    }
}
