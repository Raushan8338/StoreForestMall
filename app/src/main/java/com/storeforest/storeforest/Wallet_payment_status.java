package com.storeforest.storeforest;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Wallet_payment_status extends AppCompatActivity {
    TextView certain;
    TextView massage;
    TextView massage1;
    TextView time;
    TextView time1;
    TextView t_id;
    TextView t_detail;
    TextView info;
    TextView o_id;
    TextView t_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_payment_status);
        certain=findViewById(R.id.certain);
        massage=findViewById(R.id.massage);
        massage1=findViewById(R.id.massage1);
        time=findViewById(R.id.time);
        time1=findViewById(R.id.time1);
        t_id=findViewById(R.id.t_id);
        t_detail=findViewById(R.id.t_detail);
        info=findViewById(R.id.info);
        o_id=findViewById(R.id.o_id);
        t_date=findViewById(R.id.t_date);
        getSupportActionBar().hide();
        t_date.setText(getIntent().getStringExtra("time"));
        o_id.setText(getIntent().getStringExtra("t_id"));

        if (getIntent().getBooleanExtra("status",false)){
            certain.setText(getIntent().getStringExtra("certain"));
            certain.setTextColor(Color.RED);
            massage.setText("Successfully Debited from your account");
            massage.setTextColor(Color.RED);
            massage1.setText("Debited from your account");
            time.setText(getIntent().getStringExtra("time"));
            time1.setText(getIntent().getStringExtra("time"));
            t_id.setText(getIntent().getStringExtra("t_id"));
            t_detail.setText(getIntent().getStringExtra("t_detail"));
            info.setText("Debited for");
        } else {
            certain.setText(getIntent().getStringExtra("certain"));
            certain.setTextColor(Color.GREEN);
            massage.setText("Successfully Credited to your account");
            massage.setTextColor(Color.GREEN);
            massage1.setText("Credited from your account");
            time.setText(getIntent().getStringExtra("time"));
            time1.setText(getIntent().getStringExtra("time"));
            t_id.setText(getIntent().getStringExtra("t_id"));
            t_detail.setText(getIntent().getStringExtra("t_detail"));
            info.setText("Credited for");
        }

    }
}
