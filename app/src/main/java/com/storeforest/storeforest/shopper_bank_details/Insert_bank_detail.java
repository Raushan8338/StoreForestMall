package com.storeforest.storeforest.shopper_bank_details;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.Domain;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.update_shop_details.Update_thanku;

import java.util.HashMap;
import java.util.Map;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.user_id;


public class Insert_bank_detail extends AppCompatActivity {
    EditText acc_no;
    EditText ifsc_code;
    TextView branch_add;
    TextView micr;
    TextView bank_name;
    TextView h_name;
    EditText mobile_no;
    TextView branch_address;
    Button add_bank;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;

    String Acco_no,Ifscc,Branch_add,Micr,Bank_name,Branch_address,User_id,H_name,Mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_bank_detail);
        setTitle("Add Bank Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        acc_no=findViewById(R.id.acc_no);
        ifsc_code=findViewById(R.id.ifsc_code);
        branch_add=findViewById(R.id.branch_add);
        micr=findViewById(R.id.micr);
        bank_name=findViewById(R.id.bank_name);
        branch_address=findViewById(R.id.branch_address);
        add_bank=findViewById(R.id.add_bank);
        h_name=findViewById(R.id.h_name);
        mobile_no=findViewById(R.id.mobile_no);
        requestQueue= Volley.newRequestQueue(this);
        ifsc_code.setText(getIntent().getStringExtra("IFSC"));
        branch_add.setText(getIntent().getStringExtra("ADDRESS"));
        micr.setText(getIntent().getStringExtra("MICR"));
        bank_name.setText(getIntent().getStringExtra("BANK"));
        branch_address.setText(getIntent().getStringExtra("BRANCH"));
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        progressDialog=new ProgressDialog(Insert_bank_detail.this);
        Domain d=new Domain();
        final String url="https://trendingstories4u.com/android_app/add_bank.php?user_id="+sharedPreferences.getString(user_id, null);

        add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_no.getText().toString().isEmpty()) {
                    Toast.makeText(Insert_bank_detail.this, "Please enter your account no", Toast.LENGTH_SHORT).show();
                }
                else if (h_name.getText().toString().isEmpty()) {
                    Toast.makeText(Insert_bank_detail.this, "Please enter your Holder name", Toast.LENGTH_SHORT).show();
                }
                else if (mobile_no.getText().toString().isEmpty()) {
                    Toast.makeText(Insert_bank_detail.this, "Please enter your Mobile no", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setMessage("Please Wait");
                    progressDialog.show();
                    GetvaluefromEditText();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                         //   Log.e("Response", response);
                            Intent intent = new Intent(Insert_bank_detail.this, Update_thanku.class);
                            Toast.makeText(Insert_bank_detail.this, "Added Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    },
                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Insert_bank_detail.this, "Error On Uploading the Details", Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("acc_no", Acco_no);
                            params.put("ifsc", Ifscc);
                            params.put("bank_name", Bank_name);
                            params.put("branch_add", Branch_add);
                            params.put("micr", Micr);
                            params.put("branch_address", Branch_address);
                            params.put("holder_name", H_name);
                            params.put("mobile_no", Mobile);
                            return params;
                        }

                    };
                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(Insert_bank_detail.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
    private void GetvaluefromEditText() {
        Acco_no=acc_no.getText().toString().trim();
        Ifscc=ifsc_code.getText().toString().trim();
        Bank_name=bank_name.getText().toString().trim();
        Branch_add=branch_add.getText().toString().trim();
        Micr=micr.getText().toString().trim();
        Branch_address=branch_address.getText().toString().trim();
        H_name=h_name.getText().toString().trim();
        Mobile=mobile_no.getText().toString().trim();
    }
}
