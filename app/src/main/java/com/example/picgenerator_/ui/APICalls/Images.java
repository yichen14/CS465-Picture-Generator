package com.example.picgenerator_.ui.APICalls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Images {
    public List<TaskReadyEvent> listeners;
    public static final String QUERY_FOR_CHECKING_STATUS = "https://wenxin.baidu.com/moduleApi/portal/api/rest/1.0/ernievilg/v1/getImg?access_token=";
    Context context;


    // constructor
    public Images(Context context) {
        this.listeners = new ArrayList<>();
        this.context = context;
    }

    public void addTaskReadyListener(TaskReadyEvent listener) {
        listeners.add(listener);
    }

    // check status on task
    private void checkStatus(String token, String taskId) throws JSONException {
        String url = QUERY_FOR_CHECKING_STATUS+token;
        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("taskId", taskId);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBodyObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String waiting = "";
                JSONArray imgUrlsJson;
                try {
                    waiting = response.getJSONObject("data").getString("waiting");
                    for (TaskReadyEvent listener : listeners) {
                        // if task is ready, trigger listeners, and return list of imgUrls
                        if (waiting.equals("0")) {
                            imgUrlsJson = response.getJSONObject("data").getJSONArray("imgUrls");
                            List<String> imgUrls = new ArrayList<>();
                            for (int i = 0; i < imgUrlsJson.length(); i++) {
                                imgUrls.add(imgUrlsJson.getJSONObject(i).getString("image"));
                            }
                            listener.onTaskReady(imgUrls);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        APIUtilizer.getInstance(context).addToRequestQueue(request);
    }

    public void getImages(ScheduledExecutorService service, String token, String taskId) {
        Runnable check = new Runnable() {
            boolean done = false;
            public void run() {
                try {
                    checkStatus(token, taskId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        service.scheduleAtFixedRate(check, 0, 2, TimeUnit.SECONDS);
    };

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}