package com.storeforest.storeforest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

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
import com.google.android.material.snackbar.Snackbar;
import com.onesignal.OneSignal;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Verify_otp.user_id;


public class MainActivity extends AppCompatActivity {
    private long ms = 0, spalshTime = 3000;
    private boolean spalshActive = true, paused = false;
    private static final String ACTION_ID_MY_CUSTOM_ID = "MY_CUSTOM_ID";
    TextView cal;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    TextView location;
    TextView certificate;
    double lat;
    double lng;
    public static final String MyPrefcity = "MyPrefcity";
    public static final String address = "city";
    public static final String user_lat = "user_lat";
    public static final String user_lng = "user_lng";
    LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        location = findViewById(R.id.location);
        final ConstraintLayout cl = findViewById(R.id.logos);
        sharedPreferences = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (spalshActive && ms < spalshTime) {
                        if (!paused)
                            ms = ms + 100;
                        sleep(100);
                    }
                } catch (Exception e) {
                } finally {
                    if (!isOnline()) {
                        Snackbar snackbar = Snackbar.make(cl, "No Internet Access", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                recreate();
                            }
                        });
                        snackbar.show();
                    } else {
                        goMain();
                    }
                }
            }
        };
        thread.start();
        locationManager = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location mCurrentLocation = locationResult.getLastLocation();
                LatLng myCoordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                String cityName = getCityName(myCoordinates);
                lat = mCurrentLocation.getLatitude();
                lng = mCurrentLocation.getLongitude();
                sharedPreferences2 = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://trendingstories4u.com/android_app/auto_location_add.php?user_id=" + sharedPreferences2.getString(user_id, null) + "&c_location=" + cityName + "&lat=" + lat + "&lng=" + lng;
                Log.e("url", url);
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


                Log.e("lat", lat + "" + lng + "" + cityName + "");
                //Toast.makeText(MainActivity.this, cityName, Toast.LENGTH_SHORT).show();
                location.setText(cityName);
            }
        };
        try {
            if (Build.VERSION.SDK_INT >= 23) {

                requestLocation();
            } else
                requestLocation();
        } catch (Exception e) {
            //  Log.e("GeoLocation : ", e.getMessage());
        }
    }

    private void goMain() {
        // progressDialog=new ProgressDialog(Login.this);
        Intent i = new Intent(MainActivity.this, Login.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(address, location.getText().toString());
        editor.putString(user_lat, lat + "");
        editor.putString(user_lng, lng + "");
        editor.apply();

        startActivity(i);
        finish();
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private String getCityName(LatLng myCoordinates) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
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
            Toast.makeText(MainActivity.this, cityName, Toast.LENGTH_SHORT).show();
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setNumUpdates(1);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //  Log.d("mylog", "Last location too old getting new location!");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
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
}
