package com.oneonepsilon.epsilonstream;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.ConnectivityManager;


import com.oneonepsilon.epsilonstream.WebAppInterface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;


public class WebActivity extends AppCompatActivity {
    private static final String mofoURL = "https://www.epsilonstream.com"; //"http://192.168.0.4:3000/";

    WebView myWebView;
    WebAppInterface myWebAppInterface;
    ConnectivityManager myConnectivityManager;

    private boolean isConnected() {
        return myConnectivityManager.getActiveNetworkInfo() != null
                && myConnectivityManager.getActiveNetworkInfo().isAvailable()
                && myConnectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void retryConnection() {
        myWebView.loadUrl(mofoURL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        myConnectivityManager = (ConnectivityManager)
                this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 0);
            }
        }

        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // We don't really know what this does, but let's chuck it in there anyway
        //webSettings.setMediaPlaybackRequiresUserGesture(true);
        myWebAppInterface = new WebAppInterface(this.getApplicationContext(), myWebView, mofoURL);
        myWebView.addJavascriptInterface(myWebAppInterface, "Android");
        myWebView.setWebViewClient(new WebViewClient());

        if (!isConnected()) {
            myWebView.loadUrl("file:///android_asset/NoConnection.html");
            Toast.makeText(getApplicationContext(), "No Internet => no mAth!", Toast.LENGTH_LONG).show();
        } else {
            myWebView.loadUrl(mofoURL);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}


