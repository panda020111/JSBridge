package com.example.jsbridge;

import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by yunchang on 2018/4/19.
 */

public class Callback {

    private static final String CALLBACK_JS_FORMAT = "javascript:JSBridge.onFinish('%s', '%s')";
    private String mPort;
    private WeakReference<WebView> mWebViewWeakReference;

    public Callback(WebView view, String port) {
        mWebViewWeakReference = new WeakReference<WebView>(view);
        mPort = port;
    }

    // 执行返回的结果
    public void apply(JSONObject jsonObject) {

        // 这个的唤起不是在主线程，可能是在js线程；
        final String execJs = String.format(CALLBACK_JS_FORMAT, mPort, String.valueOf(jsonObject));
        if (mWebViewWeakReference != null && mWebViewWeakReference.get() != null) {
            mWebViewWeakReference.get().loadUrl(execJs);
        }
    }

}
