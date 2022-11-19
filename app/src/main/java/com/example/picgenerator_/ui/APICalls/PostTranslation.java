package com.example.picgenerator_.ui.APICalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.picgenerator_.ui.APICalls.PostTasks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostTranslation {
    private String res = "";
    Context context;
    public PostTranslation(Context context){this.context = context;}
    public static final String QUERY_FOR_TRANSLATION = "https://api-free.deepl.com/v2/translate";
    public static final String Key = "375bf1d8-7572-12fd-bb00-1412aa7f9398:fx";


    // Call back
    public interface PostTranslateResponseListener {
        void onError(String message);
        void onResponse(String response) throws JSONException;
    }

    public void postTranslate(String input, PostTranslateResponseListener responseListener) throws JSONException {
        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("text", input);
        jsonBodyObj.put("target_lang", "ZH");
        System.out.println(jsonBodyObj);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, QUERY_FOR_TRANSLATION, jsonBodyObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response: "+ response);
                try {
                    res = response.getJSONObject("translations").getString("text");
                    responseListener.onResponse(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                responseListener.onError("some errors");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
//                headers.put("Host", "https://api-free.deepl.com/v2/translate");
                headers.put("Authorization", "DeepL-Auth-Key "+Key);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("User-Agent", "YourApp/1.2.3");
                return headers;
            }
        };
        APIUtilizer.getInstance(context).addToRequestQueue(request);

    }

    public String getResult(){
        return this.res;
    }

}
