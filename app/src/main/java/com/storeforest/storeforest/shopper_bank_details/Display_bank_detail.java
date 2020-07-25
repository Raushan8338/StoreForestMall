package com.storeforest.storeforest.shopper_bank_details;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.R;

public class Display_bank_detail extends AppCompatActivity {
    TextView acc_no;
    TextView ifsc_code;
    TextView branch_add;
    TextView micr;
    TextView bank_name;
    TextView h_name;
    TextView mobile_no;
    TextView branch_address;
    Button add_bank;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bank_detail);
        acc_no=findViewById(R.id.acc_no);
        setTitle("Bank Details");
        ifsc_code=findViewById(R.id.ifsc_code);
        branch_add=findViewById(R.id.branch_add);
        micr=findViewById(R.id.micr);
        bank_name=findViewById(R.id.bank_name);
        branch_address=findViewById(R.id.branch_address);
        add_bank=findViewById(R.id.add_bank);
        h_name=findViewById(R.id.h_name);
        mobile_no=findViewById(R.id.mobile_no);
        requestQueue= Volley.newRequestQueue(this);
        acc_no.setText(getIntent().getStringExtra("acc_no"));
        ifsc_code.setText(getIntent().getStringExtra("ifsc_code"));
        branch_add.setText(getIntent().getStringExtra("ADDRESS"));
        micr.setText(getIntent().getStringExtra("MICR"));
        bank_name.setText(getIntent().getStringExtra("BANK"));
     //   sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
    }
}
