package com.example.picgenerator_.ui.APICalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.picgenerator_.ui.APICalls.PostTasks;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostTranslation {
    private String res = "";
    Context context;
    public PostTranslation(Context context){this.context = context;}
    public static final String QUERY_FOR_TRANSLATION = "https://api-free.deepl.com/v2/translate";
    public static final String Key = "df57b625-53e3-9d93-55b9-41f1081b6dc9:fx";


    // Call back
    public interface PostTranslateResponseListener {
        void onError(String message);
        void onResponse(String response) throws JSONException;
    }

    public void postTranslate(String input, PostTranslateResponseListener responseListener) throws JSONException {
        JSONObject jsonBodyObj = new JSONObject();
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(input);
        jsonBodyObj.put("text", new JSONArray(tmp));
        jsonBodyObj.put("target_lang", "ZH");
        System.out.println(jsonBodyObj);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, QUERY_FOR_TRANSLATION, jsonBodyObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response: "+ response);
                try {
                    res = response.getJSONArray("translations").getJSONObject(0).getString("text");

                    responseListener.onResponse(res);
                } catch (JSONException e) {
                    System.out.println("response error: ");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error: "+ error);
                if (error == null || error.networkResponse == null) {
                    return;
                }

                String body;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                System.out.println("statusCode: "+ statusCode);

                //get response body and parse with appropriate encoding
                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    System.out.println("body: "+ body);
                } catch (UnsupportedEncodingException e) {
                    // exception
                }

                responseListener.onError("some errors");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
//                headers.put("Host", "https://api-free.deepl.com/v2/translate");
                String auth = "DeepL-Auth-Key "+Key;
                System.out.println(auth);
                headers.put("Authorization", auth);
//                headers.put("Content-Type", "application/x-www-form-urlencoded");
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
