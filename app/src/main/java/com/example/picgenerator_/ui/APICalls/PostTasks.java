package com.example.picgenerator_.ui.APICalls;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PostTasks {
    public static final String AK = "FG0FG1bGAm04DoxduGmp2endv6Xd4Met";
    public static final String SK = "DTvuzzvGpyaWz63FHLCxooEnXLPMwFLA";
    public static final String QUERY_FOR_TOKEN = "https://wenxin.baidu.com/moduleApi/portal/api/oauth/token?grant_type=client_credentials&client_id=";
    public static final String QUERY_FOR_CREATING_TASK = "https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/txt2img?access_token=$";

    Context context;
    String token;
    String taskId;
    String waiting;

    // constructor
    public PostTasks(Context context) {
        this.context = context;
    }

    // Call back
    public interface GetTokenResponseListener {
        void onError(String message);
        void onResponse(String response);
    }
    // get accessToken
    private void getToken(GetTokenResponseListener getTokenResponseListener) {
        String url = QUERY_FOR_TOKEN +AK+"&client_secret="+SK;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                token = "";
                try {
                    token = response.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getTokenResponseListener.onResponse(token);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getTokenResponseListener.onError("Something Wrong");
            }
        });
        APIUtilizer.getInstance(context).addToRequestQueue(request);
    }


    // Call back
    public interface PostTaskResponseListener {
        void onError(String message);
        void onResponse(String[] response);
    }
    // Post task to generate pic
    public void postTask(String keyword, String style, PostTaskResponseListener postTaskResponseListener) throws JSONException {
        // construct request body (text&style)
        System.out.println("Posted a generation task!");
        System.out.println(keyword);
        System.out.println(style);
        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("text", keyword);

        jsonBodyObj.put("style", style);

        getToken(new GetTokenResponseListener() {
            // If error
            @Override
            public void onError(String message) {
                Toast.makeText(context, "GetToken: Something Wrong", Toast.LENGTH_SHORT).show();
            }

            // If token received
            @Override
            public void onResponse(String token) {
                String url = QUERY_FOR_CREATING_TASK+token;
                // post task to generate pic
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBodyObj, new Response.Listener<JSONObject>(){
                    @Override
                    // if posted
                    public void onResponse(JSONObject response) {
                        taskId = "";
                        try {
                            taskId = response.getJSONObject("data").getString("taskId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // return token and taskId
                        String[] res = {token, taskId};
                        postTaskResponseListener.onResponse(res);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        postTaskResponseListener.onError("Something Wrong");
                    }
                });
                APIUtilizer.getInstance(context).addToRequestQueue(request);
            }
        });
    }
}