package com.storeforest.storeforest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Verify_otp extends AppCompatActivity {
    public static final String MyPref = "MyPref";
    public static final String user_id = "user_id";
    public static final String user_name = "name";
    public static final String mobile = "mobile";
    public static final String email_status="email_status";
    public  static  final  String profile_status="profile_status";
    public static final String emailid = "email";
    public static final String device_ids = "device_id";
    public static final String alternate_mobile = "alternate_mobile";
    public static final String emergency_mobile = "emergency_mobile";
    public static final String status = "status";
    public static final String mode = "mode";
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String device_id="";
    EditText otps;
    TextView resend;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_otp);
        verify=findViewById(R.id.verify);
        otps=findViewById(R.id.otp);
        resend=findViewById(R.id.resend);
        TextView show_mobile=findViewById(R.id.mobile);
        new OTP_Receiver().setEditText(otps);
        setTitle("Verify Mobile");
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // Log.e("User:",userId);
                device_id=userId;
            }
        });
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
                        RequestQueue queue =  Volley.newRequestQueue(Verify_otp.this);
                        final Integer randomNum = randInt(0, 99999);
                        String Url="https://alerts.prioritysms.com/api/web2sms.php?workingkey=A64f67d5675dce3e6ed43aa3cbf0a3963"+
                                "&to="+getIntent().getStringExtra("mobile")+"&sender=iturhs"+"&message="+"Dear,"+getIntent().getStringExtra("name")+"Your Store Forest Registration OTP is "+randomNum.toString()+" dont share it with anyone.OTP is valid on 15 minute";

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                RequestQueue requestQueue = Volley.newRequestQueue(Verify_otp.this);
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
                    sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                    final ProgressDialog progressDialog=new ProgressDialog(Verify_otp.this);
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(Verify_otp.this);
                String Url="https://trendingstories4u.com/android_app/register_user.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        Log.e("response",response);
                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(user_id,response+"");
                                        editor.putString(user_name,getIntent().getStringExtra("name"));
                                        editor.putString(mobile,getIntent().getStringExtra("mobiles"));
                                        editor.putString(emailid,getIntent().getStringExtra("email"));
                                        editor.putString(device_ids,device_id);
                                        editor.putBoolean(status,true);
                                        editor.apply();
                                        Intent intent=new Intent(Verify_otp.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    //  progressDialog.dismiss();
                                    // Showing error message if something goes wrong.
                               //     Toast.makeText(Verify_otp.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("name", getIntent().getStringExtra("name"));
                            params.put("mobile", getIntent().getStringExtra("mobile"));
                            params.put("email", getIntent().getStringExtra("email"));
                            params.put("password", getIntent().getStringExtra("password"));
                            params.put("android_id", device_id);

                            return params;
                        }
                    };

                    // Creating RequestQueue.

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                 }
                else {
                    Toast.makeText(Verify_otp.this, "OTP Invalid", Toast.LENGTH_SHORT).show();
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
