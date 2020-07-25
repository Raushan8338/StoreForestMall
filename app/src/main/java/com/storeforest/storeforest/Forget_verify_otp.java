package com.storeforest.storeforest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

import java.util.Random;

public class Forget_verify_otp extends AppCompatActivity {
    ProgressDialog progressDialog;
    String device_id="";
    EditText otps;
    TextView resend;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_verify_otp);

        verify=findViewById(R.id.verify);
        otps=findViewById(R.id.otp);
        resend=findViewById(R.id.resend);
        TextView show_mobile=findViewById(R.id.mobile);
        setTitle("Verify Mobile");
       /* OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // Log.e("User:",userId);
                device_id=userId;
            }
        });*/
        //   Toast.makeText(this, getIntent().getStringExtra("otp"), Toast.LENGTH_SHORT).show();
        /*Start Resed otp*/
        show_mobile.setText(getIntent().getStringExtra("mobile"));
        new CountDownTimer(90000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend.setText("Resend Otp: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }
            public void onFinish() {
                resend.setText("Resend Now");
                resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        RequestQueue queue =  Volley.newRequestQueue(Forget_verify_otp.this);
                        final Integer randomNum = randInt(0, 99999);
                        String Url="https://alerts.prioritysms.com/api/web2sms.php?workingkey=A64f67d5675dce3e6ed43aa3cbf0a3963"+
                                "&to="+getIntent().getStringExtra("mobile")+"&sender=iturhs"+"&message="+"Dear,"+"Your Store Forest Forget Password OTP is "+randomNum.toString()+" dont share it with anyone.OTP is valid on 15 minute";

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                RequestQueue requestQueue = Volley.newRequestQueue(Forget_verify_otp.this);
                                String url = "https://trendingstories4u.com/android_app/send_otp_mail.php?otp="+randomNum.toString()+"&email="+getIntent().getStringExtra("email")+"&name="+getIntent().getStringExtra("name");
                                Log.e("url",url);
                                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //  Toast.makeText(Spalash.this,"Address will be changed", Toast.LENGTH_LONG).show();
                                        // Log.e("Device",response);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                //makeToast("Server linking failed !!! Check your Internet Connection.");
                            }
                        });
                        queue.add(stringRequest);

                    }
                });
            }

        }.start();

        /*End Resend OTP*/
        //  Log.e("otp",getIntent().getStringExtra("otp"));
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("otps",otps.getText().toString());
                if (otps.getText().toString().equals(getIntent().getStringExtra("otp"))){

                                        Intent intent=new Intent(Forget_verify_otp.this,Enter_forget_password.class);
                                        intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                                        startActivity(intent);
                                        finish();
                }
                else {
                    Toast.makeText(Forget_verify_otp.this, "OTP Invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public static int randInt(int min, int max) {
        // Usually this should be a field rather than a method variable so
        // that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
