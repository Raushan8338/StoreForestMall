package com.storeforest.storeforest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.storeforest.storeforest.update_shop_details.Update_thanku;


public class WebInterface {
    Context mContext;
    ProgressDialog progressDialog;
    /** Instantiate the interface and set the context */
    public WebInterface(Context c) {
        mContext = c;
       // progressDialog=new ProgressDialog(mContext);
        //progressDialog.setMessage("Please wait");
        //progressDialog.setCancelable(false);
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void sendA3(){
        Intent intent = new Intent(mContext, Update_thanku.class);
        mContext.startActivity(intent);
        ((Activity)mContext).finish();
    }

    @JavascriptInterface
    public void dismissProgress(){
        new Thread() {
            public void run() {
                try{
                    // Grab your data
                } catch (Exception e) { }

                // When grabbing data is finish: Dismiss your Dialog
              //  progressDialog.dismiss();
            }
        }.start();

    }


}

