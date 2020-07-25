package com.storeforest.storeforest.payment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.navigation.Home;

import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.MyPrefid;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.shop_name;


public class Payment_success extends AppCompatActivity {
    TextView transactionid;
    TextView resend;
    TextView o_status;
    TextView shop_confirmation;
    private AdView mAdView;
    TextView s_mobile;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_success);
        shop_confirmation = findViewById(R.id.shop_confirmation);
        setTitle("Order status");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        s_mobile = findViewById(R.id.s_mobile);
        sharedPreferences =getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        s_mobile.setText(getIntent().getStringExtra("shopper_no"));
        s_mobile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" +getIntent().getStringExtra("shopper_no") ;
                i.setData(Uri.parse(p));
                startActivity(i);
            }
        });
        adView.setAdUnitId("ca-app-pub-2895663520931288/1911630395");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        shop_confirmation.setText("Order sent to "+sharedPreferences.getString(shop_name,null)+" waiting for them to confirm that they can deliver it.");
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(" waiting for them to confirm that they can deliver it")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent=new Intent(Payment_success.this, Home.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}
