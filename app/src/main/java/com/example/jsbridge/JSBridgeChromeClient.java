package com.example.jsbridge;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by yunchang on 2018/4/19.
 */

public class JSBridgeChromeClient extends WebChromeClient {


    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

        // 调用js bridge;
        result.confirm(JSBridge.callJava(view, message));
        return true;
    }
}
