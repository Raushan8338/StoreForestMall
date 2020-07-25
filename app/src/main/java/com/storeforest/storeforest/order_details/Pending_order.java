package com.storeforest.storeforest.order_details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.storeforest.storeforest.R;

public class Pending_order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_order);
        getSupportActionBar().hide();
    }
}
