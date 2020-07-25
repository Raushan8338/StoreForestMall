package com.storeforest.storeforest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import java.util.Random;

public class Register extends AppCompatActivity {
    TextView text;
    EditText name;
    EditText mobiles;
    Button signups;
    EditText email;
    RequestQueue requestQueue;
    EditText password;
    ProgressDialog progressDialog;
    private RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    String Name,Email,Mobile,Password;
    String device_id="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private AppCompatCheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        text=findViewById(R.id.log);
        name=findViewById(R.id.name);
        mobiles=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        getSupportActionBar().hide();
        radioGroup=findViewById(R.id.groupradio);
        signups=findViewById(R.id.signup);
        checkbox = (AppCompatCheckBox) findViewById(R.id.checkbox);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // Log.e("User:",userId);
                device_id=userId;
            }
        });
        final String android_id = Settings.Secure.getString(Register.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener( new RadioGroup .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId) {

                RadioButton  radioButton = (RadioButton)group .findViewById(checkedId);
            }
        });

        requestQueue = Volley.newRequestQueue(Register.this);
        progressDialog = new ProgressDialog(Register.this);
        signups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are sending otp");
                progressDialog.show();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
                // Showing response message coming from server.
                // String pass=password.getText().toString();

                if (name.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Please Enter your name", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (mobiles.getText().toString().equals("")){
                    Toast.makeText(Register.this, "Please insert Mobile No", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                else if (password.getText().toString().length() < 2){
                    Toast.makeText(Register.this, "Please Enter min 8 digit password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Register.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    if(email.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {

                        final Integer randomNum = randInt(0, 99999);
                        Log.e("otp",randomNum.toString());
                        if (email.getText().toString().trim().matches(emailPattern)) {
                            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                            String url = "https://trendingstories4u.com/android_app/check_register.php?mobile="+mobiles.getText().toString();
                            Log.e("url",url);
                            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("success")){
                                        progressDialog.dismiss();
                                        Toast.makeText(Register.this, "Your Mobile No Already Registred ! Forget your password than login", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                           String Url="https://alerts.prioritysms.com/api/web2sms.php?workingkey=A64f67d5675dce3e6ed43aa3cbf0a3963"+
                                   "&to="+mobiles.getText().toString()+"&sender=iturhs"+"&message="+randomNum.toString()+"Dear,"+name.getText().toString()+"Your Store Forest Registration OTP is "+randomNum.toString()+" dont share it with anyone.OTP is valid on 15 minute";
                           Log.e("url",Url);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //    Log.e("response",response);
                                            // Hiding the progress dialog after all task complete.
                                            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                                            String url = "https://trendingstories4u.com/android_app/send_otp_mail.php?otp="+randomNum.toString()+"&email="+email.getText().toString()+"&name="+name.getText().toString();
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
                                            requestQueue.add(stringRequest1);
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(Register.this, Verify_otp.class);
                                            intent.putExtra("name", name.getText().toString());
                                            intent.putExtra("mobile", mobiles.getText().toString());
                                            intent.putExtra("password", password.getText().toString());
                                            intent.putExtra("email", email.getText().toString());
                                            intent.putExtra("device_id", device_id);
                                            intent.putExtra("otp", randomNum.toString());
                                            startActivity(intent);
                                        }
                                   },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            // Hiding the progress dialog after all task complete.
                                            progressDialog.dismiss();
                                            // Showing error message if something goes wrong.
                                        //    Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                            // Creating RequestQueue.
                            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

                            // Adding the StringRequest object into requestQueue.
                            requestQueue.add(stringRequest);
                                        // Adding the StringRequest object into requestQueue.
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue.add(stringRequest1);

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Creating string request with post method.
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
