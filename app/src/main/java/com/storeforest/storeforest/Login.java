package com.storeforest.storeforest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.onesignal.OneSignal;
import com.storeforest.storeforest.navigation.Home;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Login extends AppCompatActivity {
    EditText email;
    EditText pwd;
    TextView textView;
    TextView forget;
    LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    private AppCompatCheckBox checkbox;
    public static final String MyPref = "MyPref";
    public static final String user_id = "user_id";
    public static final String user_name = "name";
    public static final String mobile = "mobile";
    public static final String email_status = "email_status";
    public static final String profile_status = "profile_status";
    public static final String emailid = "email";
    public static final String device_ids = "device_id";
    public static final String alternate_mobile = "alternate_mobile";
    public static final String emergency_mobile = "emergency_mobile";
    public static final String status = "status";
    ImageView facebook;
    public static final String mode = "mode";
    String device_id = "";
    Button login;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    ImageView gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        email = findViewById(R.id.mobile);

        pwd = findViewById(R.id.password1);
        textView = findViewById(R.id.textc);
        gmail = findViewById(R.id.gmail);
        forget = findViewById(R.id.forget);
        login = findViewById(R.id.signin);
        facebook = findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/storeForestMall/?ref=bookmarks";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // Log.e("User:",userId);
                device_id = userId;
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:trendingstories4u@gmail.com"));
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });
        checkbox = (AppCompatCheckBox) findViewById(R.id.checkbox);
        requestSMSPermission();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Domain d = new Domain();
                progressDialog = new ProgressDialog(Login.this);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                String url = "https://trendingstories4u.com/android_app/login_user.php?mobile=" + email.getText().toString() + "&password=" + pwd.getText().toString() + "&device_id=" + device_id;
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //res.setText(obj.getString("message"));
                            if (obj.getString("result").equals("true")) {
                                JSONObject js = obj.getJSONObject("series");
                                //textView.setText(user.toString());
                                progressDialog.dismiss();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(user_id, js.getString("user_id"));
                                editor.putString(user_name, js.getString("name"));
                                editor.putString(mobile, js.getString("mobile"));
                                editor.putString(emailid, js.getString("email"));
                                editor.putString(device_ids, device_id);
                                editor.putBoolean(status, true);
                                editor.apply();
                                RequestQueue queue1 = Volley.newRequestQueue(Login.this);
                                try {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("device_id", device_id);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                       /* String url = d.getSetAppId()+"?mobile="+user.getString("mobile")+"&u_id="+device_id;
                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                makeToast("Login Successfully");

                                //Log.e("Device",response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue1.add(stringRequest1);*/

                            } else {
                                progressDialog.dismiss();
                                makeToast("Mobile No And Password Is Incorrect");
                            }
                        } catch (Throwable throwable) {
                            progressDialog.dismiss();
                            Log.e("Parsing problem", "Not parsing", throwable);
                            makeToast("Something went wrong!!! Please try again.");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        makeToast("Server linking failed !!! Check your Internet Connection.");
                    }
                });
                queue.add(stringRequest);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Integer randomNum = randInt(0, 99999);
                final Dialog da = new Dialog(Login.this);
                da.setContentView(R.layout.forget_enter_mobile);
                Button send_otp = da.findViewById(R.id.send_otp);
                final EditText e_mobiles = da.findViewById(R.id.e_mobile);
                send_otp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog = new ProgressDialog(Login.this);
                        progressDialog.show();
                        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
                        String url = "https://trendingstories4u.com/android_app/check_register.php?mobile=" + e_mobiles.getText().toString();
                        //    Log.e("url",url);
                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                if (response.equals("success")) {
                                    String Url = "https://alerts.prioritysms.com/api/web2sms.php?workingkey=A64f67d5675dce3e6ed43aa3cbf0a3963" +
                                            "&to=" + e_mobiles.getText().toString() + "&sender=iturhs" + "&message=Your Store Forest Forget Password OTP is " + randomNum.toString() + " dont share it with anyone.OTP is valid on 15 minute";
                                    //  Log.e("url",Url);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Intent intent = new Intent(Login.this, Forget_verify_otp.class);
                                                    intent.putExtra("otp", randomNum.toString());
                                                    intent.putExtra("mobile", e_mobiles.getText().toString());
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
                                    RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

                                    // Adding the StringRequest object into requestQueue.
                                    requestQueue.add(stringRequest);
                                } else {
                                    Toast.makeText(Login.this, "Mobile No is not register with us !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(stringRequest1);
                    }
                });
                da.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                da.show();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(status, false)) {

            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }
      /*  checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });*/
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                // Log.e("User:",userId);
                device_id = userId;
            }
        });

        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Login.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("mylog", "Not granted");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    requestLocation();
                } else
                    requestLocation();
            } else
                requestLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }

        locationManager = (LocationManager) Login.this.getSystemService(LOCATION_SERVICE);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location mCurrentLocation = locationResult.getLastLocation();
                LatLng myCoordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                String cityName = getCityName(myCoordinates);
            }
        };
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Login.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Log.d("mylog", "Not granted");
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    requestLocation();
                } else
                    requestLocation();
            } else
                requestLocation();
        } catch (Exception e) {
            //  Log.e("GeoLocation : ", e.getMessage());
        }
    }

    private void requestSMSPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    public void makeToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private String getCityName(LatLng myCoordinates) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(Login.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();
            Log.d("mylog", "Complete Address: " + addresses.toString());
            Log.d("mylog", "Address: " + address);
            myCity = address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }

    private void requestLocation() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = locationManager.getBestProvider(criteria, true);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);
        // Log.d("mylog", "In Requesting Location");
        if (location != null && (System.currentTimeMillis() - location.getTime()) <= 1000 * 2) {
            LatLng myCoordinates = new LatLng(location.getLatitude(), location.getLongitude());
            String cityName = getCityName(myCoordinates);
            Toast.makeText(Login.this, cityName, Toast.LENGTH_SHORT).show();
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setNumUpdates(1);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //  Log.d("mylog", "Last location too old getting new location!");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Login.this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.requestLocationUpdates(locationRequest,
                    mLocationCallback, Looper.myLooper());
        }

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
