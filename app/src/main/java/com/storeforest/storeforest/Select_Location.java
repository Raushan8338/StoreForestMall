package com.storeforest.storeforest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.navigation.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Select_Location extends AppCompatActivity {
    AutoCompleteTextView states;
    String city, latitudeName, longitudeName;
    SharedPreferences sharedPreferences;
    String [] cityNameList, latitudeList, longitudeList;
    public static final String MyPrefcity = "MyPrefcity";
    public static final String address = "city";
    public static final String user_lat = "user_lat";
    public static final String user_lng = "user_lng";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select__location);
        states = findViewById(R.id.state);
        Button search=findViewById(R.id.search);
        setTitle("Search Your Location");
        sharedPreferences = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (states.getText().toString().isEmpty()){
                    Toast.makeText(Select_Location.this, "Please Select Your Address", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                                    long id) {
                               // Intent intent=new Intent(Select_Location.this, Home.class);
                                //startActivity(intent);
                               // lats = Double.parseDouble(latitudeList[position]);
                               // lngs = Double.parseDouble(longitudeList[position]);
                               // Toast.makeText(Select_Location.this, ""+lat+"\n"+lng, Toast.LENGTH_SHORT).show();

                            }
                        });

                        Intent i= new Intent(Select_Location.this, Home.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(address,states.getText().toString());
                        editor.apply();
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        getCity();
    }
    private void getCity() {

        RequestQueue requestQueue = Volley.newRequestQueue(Select_Location.this);
        String url = "https://trendingstories4u.com/android_app/android_search.php";
        // Log.e("URL3",url);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<City_location> city_locations = new ArrayList<>();
                    JSONObject jsonObjec = new JSONObject(response);
                    JSONArray js=jsonObjec.getJSONArray("citySuccess");
                    Log.e("URL4",response);
                    cityNameList = new String[js.length()];

                    for (int i=0;i<js.length();i++){

                        jsonObjec = js.getJSONObject(i);

                        cityNameList[i] = jsonObjec.getString("city");

                    }

                    //Creating the instance of ArrayAdapter containing list of language names
                    try {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (Select_Location.this, android.R.layout.select_dialog_item, cityNameList);
                        //Getting the instance of AutoCompleteTextView
                        states.setThreshold(1);//will start working from first character
                        states.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        states.setTextColor(Color.BLACK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest1);

    }
}
