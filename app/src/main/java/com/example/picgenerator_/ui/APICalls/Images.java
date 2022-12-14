package com.example.picgenerator_.ui.APICalls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.ui.gallery.GalleryFragment;
import com.example.picgenerator_.ui.gallery.GalleryViewModel;

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
                System.out.println("response: "+ response);
                try {
                    waiting = response.getJSONObject("data").getString("waiting");
                    System.out.println("waiting: "+ waiting);
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
                    System.out.println("im here");
                    checkStatus(token, taskId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        service.scheduleAtFixedRate(check, 0, 2, TimeUnit.SECONDS);
    };



    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmap;
        ImagesListPage.OnDownloadCompleted onDownloadCompleted;

        public DownloadImageTask(Bitmap bitmap, ImagesListPage.OnDownloadCompleted onDownloadCompleted) {
            this.bitmap = bitmap;
            this.onDownloadCompleted = onDownloadCompleted;
        }

        protected Bitmap doInBackground(String... urls) {
            System.out.println("urls: "+urls);
            String urldisplay = urls[0];
            System.out.println("urldisplay: "+urldisplay);
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
            System.out.println("download completed");
            bitmap=result;
            onDownloadCompleted.onDownloadCompleted(result);
        }
    }

    public static class DownloadImageTaskTmp extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmap;
        int i;
        GalleryFragment.OnDownloadCompletedTmp onDownloadCompletedTmp;

        public DownloadImageTaskTmp(int i, Bitmap bitmap, GalleryFragment.OnDownloadCompletedTmp onDownloadCompletedTmp) {
            this.bitmap = bitmap;
            this.i = i;
            this.onDownloadCompletedTmp = onDownloadCompletedTmp;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            System.out.println("urldisplay: "+urldisplay);
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
            System.out.println("download completed");
            bitmap=result;
            i = i;
            onDownloadCompletedTmp.OnDownloadCompletedTmp(result, i);
        }
    }
}