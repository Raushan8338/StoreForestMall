
package com.storeforest.storeforest.manage_account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.storeforest.storeforest.R;
import com.storeforest.storeforest.Update_address;
import com.storeforest.storeforest.notification.Notifications;
import com.storeforest.storeforest.order_details.Order_details;

import static com.storeforest.storeforest.Verify_otp.mobile;
import static com.storeforest.storeforest.Verify_otp.MyPref;
import static com.storeforest.storeforest.Verify_otp.user_name;


public class Manage_account extends AppCompatActivity {
TextView manage_adddress;
TextView notification;
TextView order_history;
TextView password;
TextView mobiles;
TextView profile_name;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_account);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);

        setTitle("Manage account");

        password=findViewById(R.id.password);
        mobiles=findViewById(R.id.mobile);
        profile_name=findViewById(R.id.profile_name);
        profile_name.setText(sharedPreferences.getString(user_name,null));
        mobiles.setText(sharedPreferences.getString(mobile,null));
        password.setText("******");

    }
}
