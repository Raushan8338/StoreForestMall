package com.storeforest.storeforest.shopper_bank_details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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


import org.json.JSONObject;

public class Add_bank_detail extends AppCompatActivity {
    EditText ifsc;
    Button validate;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    LinearLayout ifsc_invisible;
    LinearLayout detail_ifsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_detail);
        setTitle("Add Bank Details");

        ifsc = findViewById(R.id.ifsc);
        validate = findViewById(R.id.verify);
        progressDialog = new ProgressDialog(Add_bank_detail.this);
       /* if (ifsc.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter Bank IFSC Code", Toast.LENGTH_SHORT).show();
        }
        else {*/
        progressDialog.setMessage("Please wait");
        Domain d = new Domain();
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                Domain d = new Domain();
                RequestQueue requestQueue = Volley.newRequestQueue(Add_bank_detail.this);
                String url = d.getIfsc_code_validation() + ifsc.getText().toString();
                Log.e("URL", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            Intent intent = new Intent(Add_bank_detail.this, Insert_bank_detail.class);
                            intent.putExtra("BRANCH", obj.getString("BRANCH"));
                            intent.putExtra("ADDRESS", obj.getString("ADDRESS"));
                            intent.putExtra("MICR", obj.getString("MICR"));
                            intent.putExtra("BANK", obj.getString("BANK"));
                            intent.putExtra("IFSC", obj.getString("IFSC"));
                            startActivity(intent);
                            Log.e("Response", response);
                            progressDialog.dismiss();
                        } catch (Throwable throwable) {
                            progressDialog.dismiss();
                            Log.e("Parsing problem", "Not parsing", throwable);
                            //    makeToast("Something went wrong!!! Please try again.");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("IFSC", ifsc.getText().toString());
                        progressDialog.dismiss();
                    }
                });
                requestQueue.add(stringRequest);

                //}
            }
        });
    }
    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
