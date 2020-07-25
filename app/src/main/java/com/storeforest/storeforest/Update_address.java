package com.storeforest.storeforest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.mobile;
import static com.storeforest.storeforest.Login.user_id;
import static com.storeforest.storeforest.Login.user_name;
import static com.storeforest.storeforest.MainActivity.MyPrefcity;
import static com.storeforest.storeforest.MainActivity.address;

public class Update_address extends AppCompatActivity {
    Button proceed;
    EditText first_name;
    EditText last_name;
    EditText contact_no;
    EditText house_no;
    EditText street_name;
    EditText apartment_no;
    EditText landmark;
    EditText pincode;
    String First_name,Lastname,Contactno,Houseno,Streetno,Apartmentno,Landmark,Pincode;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_address);
        proceed=findViewById(R.id.proceed);
        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        contact_no=findViewById(R.id.contact_no);
        setTitle("Update address");
        apartment_no=findViewById(R.id.apartment_no);
        street_name=findViewById(R.id.street_name);
        landmark=findViewById(R.id.landmark);
        pincode=findViewById(R.id.pincode);
        house_no=findViewById(R.id.house_no);
        setTitle("Add Address");
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences(MyPrefcity, Context.MODE_PRIVATE);
        first_name.setText(sharedPreferences.getString(user_name,null));
        contact_no.setText(sharedPreferences.getString(mobile,null));
        street_name.setText(sharedPreferences2.getString(address,null));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait !");
        progressDialog.show();
        String urls = "https://trendingstories4u.com/android_app/check_address.php?user_id=" +sharedPreferences.getString(user_id,null) ;
        RequestQueue queues = Volley.newRequestQueue(Update_address.this);
        StringRequest stringRequests = new StringRequest(Request.Method.GET, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    //  Log.e("url",response);
                    JSONObject obj = new JSONObject(response);
                    //res.setText(obj.getString("message"));
                    if (obj.getString("result").equals("true")) {
                        JSONObject js = obj.getJSONObject("series");
                        first_name.setText(js.getString("first_name"));
                        last_name.setText(js.getString("kast_name"));
                        contact_no.setText(js.getString("contact_no"));
                        apartment_no.setText(js.getString("apartment_no"));
                        street_name.setText(js.getString("street_name"));
                        landmark.setText(js.getString("landmark"));
                        pincode.setText(js.getString("pincode"));
                        house_no.setText(js.getString("house_no"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //  dialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Update_address.this, "Server linking failed !!! Check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        });
        queues.add(stringRequests);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();
                GetValueFromEditText();

                if (contact_no.getText().toString().equals("")){
                    Toast.makeText(Update_address.this, "Please Enter Contact no", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (street_name.getText().toString().equals("")){
                    Toast.makeText(Update_address.this, "Please Enter Street name", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                else if (house_no.getText().toString().equals("")){
                    Toast.makeText(Update_address.this, "Please Enter Your House no", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (pincode.getText().toString().equals("")){
                    Toast.makeText(Update_address.this, "Please Enter house no", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    String Url="https://trendingstories4u.com/android_app/add_address.php?user_id="+sharedPreferences.getString(user_id,null);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("response",response);
                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                    Toast.makeText(Update_address.this, response, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Update_address.this, Address_update_thnku.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                    // Showing error message if something goes wrong.
                                    Toast.makeText(Update_address.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("first_name", First_name);
                            params.put("last_name", Lastname);
                            params.put("contact_no", Contactno);
                            params.put("house_no", Houseno);
                            params.put("street_name", sharedPreferences2.getString(address,null));
                            params.put("apartment_no", Apartmentno);
                            params.put("landmark", Landmark);
                            params.put("pincode", Pincode);

                            return params;
                        }
                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(Update_address.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                    // Creating string request with post method.
                }
            }
        });
    }
    private void GetValueFromEditText() {
        First_name=first_name.getText().toString().trim();
        Lastname=last_name.getText().toString().trim();
        Contactno=contact_no.getText().toString().trim();
        Houseno=house_no.getText().toString().trim();
        Streetno=street_name.getText().toString().trim();
        Apartmentno=apartment_no.getText().toString().trim();
        Landmark=last_name.getText().toString().trim();
        Pincode=pincode.getText().toString().trim();

    }
}
