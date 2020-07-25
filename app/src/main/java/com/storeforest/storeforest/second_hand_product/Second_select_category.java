package com.storeforest.storeforest.second_hand_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.storeforest.storeforest.R;
import com.storeforest.storeforest.second_hand_product.manage_second_hand.Manage_second_hand;
import com.storeforest.storeforest.second_hand_product.second_hand_item.Second_hand_item;
import com.storeforest.storeforest.second_hand_product.second_hand_item.Second_hand_product_item;

public class Second_select_category extends AppCompatActivity {
CardView mobile;
LinearLayout add_second;
LinearLayout add_item;
CardView electronic;
CardView car;
CardView bike;
CardView properties;
CardView furniture;
CardView fashion;
CardView hobiess;
CardView jobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_select_category);
        setTitle("select category");
        mobile=findViewById(R.id.mobile);
        add_second=findViewById(R.id.add_second);
        add_item=findViewById(R.id.add_item);
        car=findViewById(R.id.car);
        electronic=findViewById(R.id.electronic);
        bike=findViewById(R.id.bike);
        properties=findViewById(R.id.properties);
        furniture=findViewById(R.id.furniture);
        fashion=findViewById(R.id.fashion);
        hobiess=findViewById(R.id.hobiess);
        jobs=findViewById(R.id.jobs);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Add_second_hand_product.class);
                startActivity(intent);
            }
        });
        add_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Manage_second_hand.class);
                startActivity(intent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Mobiles");
                startActivity(intent);
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Electronic");
                startActivity(intent);
            }
        });
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Car");
                startActivity(intent);
            }
        });
        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Bike");
                startActivity(intent);
            }
        });
        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Properties");
                startActivity(intent);
            }
        });
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Furniture");
                startActivity(intent);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Fashion");
                startActivity(intent);
            }
        });
        hobiess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Hobbies");
                startActivity(intent);
            }
        });
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Second_select_category.this, Second_hand_product_item.class);
                intent.putExtra("category","Job");
                startActivity(intent);
            }
        });


    }
}
