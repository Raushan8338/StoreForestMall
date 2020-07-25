package com.storeforest.storeforest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Enter_forget_password extends AppCompatActivity {
EditText password;
EditText password1;
Button update;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_forget_password);
        password=findViewById(R.id.password);
        password1=findViewById(R.id.password1);
        update=findViewById(R.id.update);
        progressDialog=new ProgressDialog(Enter_forget_password.this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                if (password.getText().toString().isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(Enter_forget_password.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else if (password1.getText().toString().isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(Enter_forget_password.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals(password1.getText().toString())) {

                            RequestQueue requestQueue = Volley.newRequestQueue(Enter_forget_password.this);
                            String url = "https://trendingstories4u.com/android_app/forget_enter_pass.php?password=" + password.getText().toString() + "&mobile=" +getIntent().getStringExtra("mobile");
                            Log.e("url", url);
                            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(Enter_forget_password.this,Login.class);
                                    Toast.makeText(Enter_forget_password.this, "Password has been changed", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                }

                            });
                    requestQueue.add(stringRequest1);
                }
            }
        });
    }
}
