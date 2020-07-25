package com.storeforest.storeforest.payment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import com.storeforest.storeforest.Add_address;
import com.storeforest.storeforest.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.storeforest.storeforest.Login.MyPref;
import static com.storeforest.storeforest.Login.device_ids;
import static com.storeforest.storeforest.Login.mobile;
import static com.storeforest.storeforest.Login.user_name;
import static com.storeforest.storeforest.Shop_Category.Shop_Category_adapter.MyPrefid;
import static com.storeforest.storeforest.Verify_otp.user_id;
import static com.storeforest.storeforest.shop_info.Display_shop_Adapter.userId;


public class Payment extends AppCompatActivity {
    Button gpay;
    Button phonepay;
    Button bhim;
    Button cod;
    Button order_now;
    final int UPI_PAYMENT = 0;
    private static final int TEZ_REQUEST_CODE = 123;
    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    EditText amounts, note, name, upivirtualid;
    private static final int PHONEPE_REQUEST = 123;
    private static final String PHONEPE_PACKAGE_NAME = "com.phonepe.app";
    private static final int BHIM_REQUEST = 123;
    private static final String BHIM_PACKAGE_NAME = "in.org.npci.upiapp";
    SharedPreferences sharedPreferences;
    TextView tot_amount;
    TextView all_amount;
    ProgressDialog progressDialog;
    TextView delevery_charge;
    Float amountss;
    SharedPreferences sharedPreferences2;
    Float d_charge;
    Float to_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        setTitle("Payment Method");
        gpay=findViewById(R.id.gpay);
        phonepay=findViewById(R.id.phonepay);
        bhim=findViewById(R.id.bhim);
        cod=findViewById(R.id.cod);
        tot_amount=findViewById(R.id.tot_amount);
        all_amount=findViewById(R.id.all_amount);
        progressDialog=new ProgressDialog(this);
        note = (EditText) findViewById(R.id.note);
        final TextView check = findViewById(R.id.check);
        name = (EditText) findViewById(R.id.name);
        upivirtualid = (EditText) findViewById(R.id.upi_id);
        amounts = findViewById(R.id.amount_et);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        order_now=findViewById(R.id.order_now);
        delevery_charge=findViewById(R.id.delevery_charge);
        delevery_charge.setText("Rs. "+getIntent().getStringExtra("delivery_charge"));


        amountss=Float.parseFloat(getIntent().getStringExtra("amount"));
        d_charge=Float.parseFloat(getIntent().getStringExtra("delivery_charge"));
        to_price=amountss+d_charge;

        tot_amount.setText("Rs."+getIntent().getStringExtra("amount"));
        all_amount.setText("Rs."+to_price);



        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                acceptadd();
            }
        });
        gpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setText("gp");

                payUsingUpi(name.getText().toString(),
                        upivirtualid.getText().toString(),
                        note.getText().toString(), to_price.toString(), check.getText().toString());
            }
        });
        phonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(Payment.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Currently Unavailable Phone pay Only you can pay Gpay")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setConfirmText("OK");
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
               /* check.setText("phone");

                payUsingUpi(name.getText().toString(),
                        upivirtualid.getText().toString(),
                        note.getText().toString(), amounts.getText().toString(), check.getText().toString());*/
            }
        });
        bhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setText("bhim");
                payUsingUpi(name.getText().toString(),
                        upivirtualid.getText().toString(),
                        note.getText().toString(), all_amount.getText().toString(), check.getText().toString());
            }
        });
    }
    void payUsingUpi(String name, String upiId, String note, String amounts,String check) {
       // Log.e("main ", "name " + name + "--up--" + upiId + "--" + note + "--" + amount);
        // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        if (check.equals("gp")) {
            Uri uri = Uri.parse("upi://pay").buildUpon()
                    .appendQueryParameter("pa", "raushankumar8338@okaxis")
                    .appendQueryParameter("pn", name)
                    //.appendQueryParameter("mc", "")
                    //.appendQueryParameter("tid", "02125412")
                    //.appendQueryParameter("tr", "25584584")
                    .appendQueryParameter("tn", note)
                    .appendQueryParameter("am", amounts)
                    .appendQueryParameter("cu", "INR")
                    //.appendQueryParameter("refUrl", "blueapp")
                    .build();
            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);
            // will always show a dialog to user to choose an app
            Intent chooser = upiPayIntent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
            // check if intent resolves
            if (null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(chooser, UPI_PAYMENT);
            } else {
                Toast.makeText(Payment.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
            }
        }
       /* else if (check.equals("phone")){
            Uri uri = Uri.parse("upi://pay").buildUpon()
                            .appendQueryParameter("pa",upiId)
                            .appendQueryParameter("pn",name)
                            //.appendQueryParameter("mc", "1234")
                            //.appendQueryParameter("tr", "123456789")
                            .appendQueryParameter("tn",note)
                            .appendQueryParameter("am",amounts)
                            .appendQueryParameter("cu", "INR")
                            //.appendQueryParameter("url", "https://test.merchant.website")
                            .build();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            Intent chooser = i.setPackage(PHONEPE_PACKAGE_NAME);
            // check if intent resolves
            if (null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(chooser, PHONEPE_REQUEST);
            } else {
                Toast.makeText(Payment.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
            }
        }*/
        else if (check.equals("bhim")){
            Uri uri = Uri.parse("upi://pay").buildUpon()
                    .appendQueryParameter("pa", "raushankumar8338@okaxis")
                    .appendQueryParameter("pn", name)
                    //.appendQueryParameter("mc", "")
                    //.appendQueryParameter("tid", "02125412")
                    //.appendQueryParameter("tr", "25584584")
                    .appendQueryParameter("tn", note)
                    .appendQueryParameter("am", amounts)
                    .appendQueryParameter("cu", "INR")
                    //.appendQueryParameter("refUrl", "blueapp")
                    .build();
            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);
            // will always show a dialog to user to choose an app
            Intent chooser = upiPayIntent.setPackage(BHIM_PACKAGE_NAME);
            // check if intent resolves
            if (null != chooser.resolveActivity(getPackageManager())) {
                startActivityForResult(chooser, BHIM_REQUEST);
            } else {
                Toast.makeText(Payment.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("main ", "response " + resultCode);
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("onActivityResult:", trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    //   Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Payment.this)) {
            String str = data.get(0);
            //  Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                /*Intent intent=new Intent(Payment.this,Thank_you.class);
                startActivity(intent);*/
                addToDB(approvalRefNo+"UPI");
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //  Toast.makeText(Payment.this, "TransactionHistory successful.", Toast.LENGTH_SHORT).show();
                //   Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(Payment.this, "TransactionHistory failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            //   Log.e("UPI", "Internet issue: ");
            Toast.makeText(Payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    public void acceptadd(){
        final Dialog da = new Dialog(Payment.this);
        da.setContentView(R.layout.select_payment_option);
        da.setCancelable(true);
        TextView address=da.findViewById(R.id.address);
        TextView pincode=da.findViewById(R.id.pincode);
        TextView changeAdd=da.findViewById(R.id.changeAdd);
        Button pay=da.findViewById(R.id.pay);
        pincode.setText("Pin Code"+getIntent().getStringExtra("pincode"));
        address.setText("Address : "+getIntent().getStringExtra("street_name")+","+getIntent().getStringExtra("landmark")+","+getIntent().getStringExtra("house_no"));
        changeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this, Add_address.class);
                startActivity(intent);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDB("COD");
            }
        });
        da.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        da.show();
    }
    public void addToDB(final String razorpayPaymentID) {
        sharedPreferences2 =getSharedPreferences(MyPrefid, Context.MODE_PRIVATE);
        progressDialog.show();
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        RequestQueue requestQueue = Volley.newRequestQueue(Payment.this);
        String url = "https://trendingstories4u.com/android_app/multi_booking.php?user_id="+sharedPreferences.getString(user_id,null)+"&house_no="+getIntent().getStringExtra("house_no")+"&street_name="+getIntent().getStringExtra("street_name")+"&pincode="+getIntent().getStringExtra("pincode")+"&landmark="+getIntent().getStringExtra("landmark")+"&apartment_no="+getIntent().getStringExtra("apartment_no")+"&item_count="+getIntent().getStringExtra("count_item")+"&user_name="+sharedPreferences.getString(user_name,null)+"&device_id="+sharedPreferences.getString(device_ids,null)+"&owner_userId="+sharedPreferences2.getString(userId,null)+"&payment_mode="+razorpayPaymentID+"&u_mobile="+sharedPreferences.getString(mobile,null);
        Log.e("car_ad",url);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                //  Toast.makeText(Spalash.this,"A
                //  //  ddress will be changed", Toast.LENGTH_LONG).show();
                Toast.makeText(Payment.this, "success", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Payment.this, Payment_success.class);
                intent.putExtra("shop_name",getIntent().getStringExtra("shop_name"));
                intent.putExtra("Payment_id",razorpayPaymentID);
                intent.putExtra("shopper_no",response);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent=new Intent(Payment.this, Payment_success.class);
                intent.putExtra("shop_name",getIntent().getStringExtra("shop_name"));
                intent.putExtra("Payment_id",razorpayPaymentID);
                startActivity(intent);
            }
        });
        requestQueue.add(stringRequest1);

    }

    public void makeToast(String str) {
        Toast.makeText(Payment.this, str, Toast.LENGTH_SHORT).show();
    }
    }


