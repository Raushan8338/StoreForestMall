package com.storeforest.storeforest.order_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.user_wallet.User_wallet;


public class Cancel_order_thanku extends AppCompatActivity {
    Button continue_shopping;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_order_thanku);
        getSupportActionBar().hide();
        TextView amounts=findViewById(R.id.amounts);
        TextView booking_id=findViewById(R.id.booking_id);
        booking_id.setText("##STF"+getIntent().getStringExtra("b_id"));
        amounts.setText("Rs ."+getIntent().getStringExtra("booking_amount"));
        continue_shopping=findViewById(R.id.continue_shopping);
        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cancel_order_thanku.this, User_wallet.class);
                startActivity(intent);
                finish();
            }
        });
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2895663520931288/1911630395");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
