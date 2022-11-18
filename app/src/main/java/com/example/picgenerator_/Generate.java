package com.example.picgenerator_;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.APICalls.PostTasks;
import com.example.picgenerator_.ui.home.adapter_images_list;
import com.example.picgenerator_.ui.home.adapter_task_list;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Generate extends Activity {
    Intent detail_page_intent;
    ImageButton btn_back;
    ListView imagesListView;
    adapter_images_list images_adapter;
    ArrayList<String> img_urls;
    ArrayList<Bitmap> downloaded_imgs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_list);

        detail_page_intent = getIntent();

        btn_back = findViewById(R.id.btn_back);
        imagesListView = findViewById(R.id.images_list_view);

        img_urls = detail_page_intent.getStringArrayListExtra("img_urls");
        downloaded_imgs = new ArrayList<>();
        images_adapter = new adapter_images_list(downloaded_imgs, Generate.this);
        imagesListView.setAdapter(images_adapter);
        for (int i = 0; i < img_urls.size(); i++) {
            Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new OnDownloadCompleted() {
                @Override
                public void onDownloadCompleted(Bitmap bitmap) {
                    System.out.println("downloaded");
                    downloaded_imgs.add(bitmap);
                    images_adapter.notifyDataSetChanged();
                }
            });
            downloadImageTask.execute(img_urls.get(i));
        }
        System.out.println("adapter");


        // back button to home screen
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public interface OnDownloadCompleted{
        void onDownloadCompleted(Bitmap bitmap);
    }
}