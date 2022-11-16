package com.example.picgenerator_.ui.APICalls;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class APIUtilizer {
    private static APIUtilizer instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private APIUtilizer(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized APIUtilizer getInstance(Context context) {
        if (instance == null) {
            instance = new APIUtilizer(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}