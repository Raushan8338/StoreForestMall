package com.storeforest.storeforest.navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.storeforest.storeforest.Add_address;
import com.storeforest.storeforest.Login;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Select_Location;
import com.storeforest.storeforest.booking_details.Booking_details;
import com.storeforest.storeforest.manage_account.Manage_account;
import com.storeforest.storeforest.notification.Live_chat;
import com.storeforest.storeforest.notification.Notifications;
import com.storeforest.storeforest.second_hand_product.Add_second_hand_product;
import com.storeforest.storeforest.second_hand_product.Second_select_category;
import com.storeforest.storeforest.user_wallet.User_wallet;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;
import static com.storeforest.storeforest.Verify_otp.MyPref;
import static com.storeforest.storeforest.Verify_otp.emailid;
import static com.storeforest.storeforest.Verify_otp.user_name;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView text;
    private DrawerLayout drawer;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    TextView user_names;
    ImageView user_images;
    TextView nav_email;
    TextView profile_statuss;
    ImageView image;
    TextView locations;
    AppUpdateManager mAppUpdateManager;
    SharedPreferences sharedPreferences5;
    InstallStateUpdatedListener installStateUpdatedListener;
    public static final String MyPrefs = "MyPrefs";
    public static final String user_image = "user_image";
    public static final String MyPrefses = "MyPrefses";
    public static final String email_vaerify = "email_vaerify";
    public static final String device_id = "device_id";
    TextView textCartItemCount;
    TextView notification;
    int mCartItemCount = 10;
    TextView info;
    int RC_APP_UPDATE;
    LocationCallback mLocationCallback;
    LinearLayout livechat;
    Button forget;
    LocationManager locationManager;
    FusedLocationProviderClient mFusedLocationClient;
    SharedPreferences sharedPreferences4;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // text=findViewById(R.id.user_name);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
//       text.setText(sharedPreferences.getString(name,null));
        // TextView names=findViewById(R.id.user_name);
        // names.setText(sharedPreferences.getString(name,null));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sharedPreferences4 = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        toolbar.setTitle(sharedPreferences4.getString(address, null));
        // toolbar.setT
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nav_email = headerView.findViewById(R.id.nav_email);
        user_names = headerView.findViewById(R.id.user_name);
        image = headerView.findViewById(R.id.image);
        locations = headerView.findViewById(R.id.locations);

        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Home.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

        locationManager = (LocationManager) Home.this.getSystemService(LOCATION_SERVICE);
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
                if (Home.this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        sharedPreferences1 = getSharedPreferences(MyPrefses, Context.MODE_PRIVATE);
        sharedPreferences5 = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);

        //checkUpdate();
       /* final RequestQueue requestQueue= Volley.newRequestQueue(this);
        final Domain d=new Domain();
        String url=d.getProfile_status()+"?user_id="+ sharedPreferences.getString(user_id,null);
        //  Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        final StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    // makeToast(obj.toString());
                    if (obj.getString("result").equals("true")) {
                        JSONObject js = obj.getJSONObject("series");
                        Log.e("response",js.getString("e_p"));
                        Picasso.get().load(d.getProfile_img() + js.getString("e_p"))
                                .fit()
                                .centerCrop()
                                .into(user_images);
                    }
                } catch (Throwable throwable) {
                    Log.e("Parsing Problem", "Error", throwable);
                    makeToast("KYC Document is Not Uploaded ! Please Upload KYC Document");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                makeToast("Please Check Your Internet Connection");
            }
        }

        );
        requestQueue.add(stringRequest);
        user_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });

        */
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        user_names.setText("Hello ," + sharedPreferences.getString(user_name, null));
        locations.setText("Address : " + sharedPreferences5.getString(address, null));
        nav_email.setText(sharedPreferences.getString(emailid, null));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();

        }
        //  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();

        }
        installStateUpdatedListener = new
                InstallStateUpdatedListener() {
                    @Override
                    public void onStateUpdate(InstallState state) {
                        if (state.installStatus() == InstallStatus.DOWNLOADED) {
                            popupSnackbarForCompleteUpdate();
                        } else if (state.installStatus() == InstallStatus.INSTALLED) {
                            if (mAppUpdateManager != null) {
                                mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                            }
                        } else {
                            Log.i("TAG  ", "InstallStateUpdatedListener: state: " + state.installStatus());
                        }
                    }
                };
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.registerListener(installStateUpdatedListener);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.FLEXIBLE, Home.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate();
                } else {
                    Log.e("Tag ", "checkForAppUpdateAvailability : something else");
                }
            }
        });

    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.drawer_layout),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.completeUpdate();
                }
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.referesh:
                Intent intent = new Intent(Home.this, Select_Location.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                // item.setTitle("profidk");
                //Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
                Intent intente = new Intent(this, Manage_account.class);
                startActivity(intente);
                break;
            case R.id.help:
                //Toast.makeText(this, "SERIES", Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(this, Live_chat.class);
                startActivity(intent6);
                break;
            case R.id.sell_buy:
                Intent sell_buys = new Intent(this, Second_select_category.class);
                startActivity(sell_buys);
                //Toast.makeText(this, "NEWS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_money:
                Intent intent1 = new Intent(this, Add_address.class);
                startActivity(intent1);
                //Toast.makeText(this, "NEWS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_booking:
                Intent intent2 = new Intent(this, Booking_details.class);
                startActivity(intent2);
                break;
            case R.id.wallet:
                Intent intent3 = new Intent(this, User_wallet.class);
                startActivity(intent3);
                break;
            case R.id.notification:
                Intent intent8 = new Intent(this, Notifications.class);
                startActivity(intent8);
                break;
            case R.id.share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.storeforest.storeforest&hl=en_IN");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent intent5 = new Intent(Home.this, Login.class);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear().commit();
                                startActivity(intent5);
                                finish();
                                recreate();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Do you want to exit")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        Home.super.onBackPressed();
                                        moveTaskToBack(true);
                                        finish();


                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    private String getCityName(LatLng myCoordinates) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
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
            Toast.makeText(Home.this, cityName, Toast.LENGTH_SHORT).show();
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setNumUpdates(1);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //  Log.d("mylog", "Last location too old getting new location!");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Home.this);
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
    public void makeToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
