package com.example.jsbridge;

import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by yunchang on 2018/4/19.
 */

public class BridgeImpl implements IBridge {

    public static void showToast(WebView webView, JSONObject param, final Callback callback) {
        String message = param.optString("msg");
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();

        if (null != callback) {
            try {
                JSONObject object = new JSONObject();
                object.put("key", "value");
                callback.apply(getJSONObjectResult(0, "ok", object));
            } catch (Exception e) {

            }
        }
    }


    private static JSONObject getJSONObjectResult(int code, String msg, JSONObject result) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.putOpt("result", result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
