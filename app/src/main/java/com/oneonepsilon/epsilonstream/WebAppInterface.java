package com.oneonepsilon.epsilonstream;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by yoninazarathy on 25/4/18.
 */


public class WebAppInterface {
    Context mContext;

    WebView myWebView;
    
    /** Instantiate the interface and set the context */
    WebAppInterface(Context c, WebView wb, String mU) {
        mContext = c;
        myWebView = wb;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void retryConnection() {
        Toast.makeText(mContext, "Retrying connection", Toast.LENGTH_LONG).show();
    }
}
