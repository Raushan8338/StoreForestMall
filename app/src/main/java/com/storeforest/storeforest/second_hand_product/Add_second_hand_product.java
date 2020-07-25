package com.storeforest.storeforest.second_hand_product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.storeforest.storeforest.City_location;
import com.storeforest.storeforest.R;
import com.storeforest.storeforest.second_hand_product.sec_upload_img.Add_second_hand_img;
import com.storeforest.storeforest.second_hand_product.second_hand_item.Second_hand_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Add_second_hand_product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] users = {"Mobiles","Car", "Bike","Properties", "Electronic","Furniture","Fashion","Hobbies"};
    AutoCompleteTextView brand;
    EditText title;
    EditText describe;
    EditText address;
    Button checkout;
    EditText price;
    TextView category;
    String city, latitudeName, longitudeName;
    String [] cityNameList, latitudeList, longitudeList;
    ArrayList<Second_hand_item> searchProductList = new ArrayList<Second_hand_item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_second_hand_product);
        brand=findViewById(R.id.brand);
        title=findViewById(R.id.title);
        describe=findViewById(R.id.describe);
        address=findViewById(R.id.describe);
        category=findViewById(R.id.category);
        checkout=findViewById(R.id.checkout);
        price=findViewById(R.id.price);
        final Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brand.getText().toString().equals("")){
                    Toast.makeText(Add_second_hand_product.this, "Please Enter Brand name", Toast.LENGTH_SHORT).show();
                  //  progressDialog.dismiss();
                }
                else if (title.getText().toString().equals("")){
                    Toast.makeText(Add_second_hand_product.this, "Please Enter Item Name", Toast.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                }
                else if (price.getText().toString().equals("")){
                    Toast.makeText(Add_second_hand_product.this, "Please Enter Item Price", Toast.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                }
                else if (describe.getText().toString().equals("")){
                    Toast.makeText(Add_second_hand_product.this, "Please Enter Item Description", Toast.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                }
                else if (address.getText().toString().equals("")){
                    Toast.makeText(Add_second_hand_product.this, "Please Enter your address", Toast.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                }
                else {
                    Intent intent = new Intent(Add_second_hand_product.this, Add_second_hand_img.class);
                    intent.putExtra("category", category.getText().toString());
                    intent.putExtra("brand", brand.getText().toString());
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("describe", describe.getText().toString());
                    intent.putExtra("address", address.getText().toString());
                    intent.putExtra("price", price.getText().toString());
                    startActivity(intent);
                    finish();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Add_second_hand_product.this);
        String url = "https://foodtravelstay.com/travel/stay/android_API/search_vehicle_brand.php";
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

                        cityNameList[i] = jsonObjec.getString("purpose");

                    }

                    //Creating the instance of ArrayAdapter containing list of language names
                    try {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (Add_second_hand_product.this, android.R.layout.select_dialog_item, cityNameList);
                        //Getting the instance of AutoCompleteTextView
                        brand.setThreshold(1);//will start working from first character
                        brand.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        brand.setTextColor(Color.BLACK);
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category.setText(users[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
