package com.example.jsbridge;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by yunchang on 2018/4/19.
 */

public class JSBridge {

    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();

    public static void register(String exposedName, Class<? extends IBridge> clazz) {
        if (!exposedMethods.containsKey(exposedName)) {
            exposedMethods.put(exposedName, getAllMethod(clazz));
        }
    }

    private static HashMap<String, Method> getAllMethod(Class injectClass) {
        HashMap<String, Method> methodHashMap = new HashMap<>();

        Method[] methods = injectClass.getDeclaredMethods();
        for (Method method : methods) {
            String name;
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }

            // 方法的几个参数；参数校验；
            Class[] parameters = method.getParameterTypes();
            if (null != parameters && parameters.length == 3) {
                if (parameters[0] == WebView.class && parameters[1] == JSONObject.class) {
                    methodHashMap.put(name, method);
                }
            }
        }
        return methodHashMap;
    }

    public static String callJava(WebView webView, String urlString) {
        String methodName = "";
        String className = "";
        String params = "{}";
        String port = "";

        // 解析urlString
        if (!TextUtils.isEmpty(urlString) && urlString.startsWith("JSBridge")) {
            Uri uri = Uri.parse(urlString);
            className = uri.getHost();
            params = uri.getQuery();
            port = uri.getPort() + "";
            String path = uri.getPath();

            if (!TextUtils.isEmpty(path)) {
                methodName = path.replace("/", "");
            }
        }

        if (exposedMethods.containsKey(className)) {
            HashMap<String, Method> methodHashMap = exposedMethods.get(className);

            if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)) {
                Method method = methodHashMap.get(methodName);
                if (method != null) {
                    try {
                        method.invoke(null, webView, new JSONObject(params), new Callback(webView, port));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }
}
