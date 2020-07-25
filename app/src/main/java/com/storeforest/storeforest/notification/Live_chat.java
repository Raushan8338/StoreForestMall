package com.storeforest.storeforest.notification;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.storeforest.storeforest.R;
import com.storeforest.storeforest.WebInterface;


public class Live_chat extends AppCompatActivity {
    WebView testPage;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_chat);
        testPage=findViewById(R.id.webview);
        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(this);
        String url="https://tawk.to/chat/5eca2a3bc75cbf1769eecb4c/default?$_tawk_popout=true";
        progressDialog=new ProgressDialog(Live_chat.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        //String url1 = "https://foodtravelstay.com/travel/stay/";
        //adharPage.loadUrl(url);
        testPage.loadUrl(url);
        WebSettings webSettings = testPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
        testPage.addJavascriptInterface(new WebInterface(this), "Android");

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(testPage, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        testPage.getSettings().setAppCacheEnabled(true);
        testPage.getSettings().setDatabaseEnabled(true);
        testPage.getSettings().setDomStorageEnabled(true);
        testPage.setWebChromeClient(new WebChromeClient());
        testPage.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //String url = request.getUrl().toString();
                progressDialog.show();
                return false;
                //return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                testPage.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(Live_chat.this).create();
                alertDialog.setTitle("Connection Error");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Check your Internet connection and try again.");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.show();
            }
        });
    }
}
