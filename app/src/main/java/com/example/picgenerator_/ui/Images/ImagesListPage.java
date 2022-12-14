package com.example.picgenerator_.ui.Images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.home.Task_model;
import com.example.picgenerator_.ui.home.adapter_images_list;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class ImagesListPage extends Activity {
    Intent detail_page_intent;
    ImageButton btn_back;
    ListView imagesListView;
    adapter_images_list images_adapter;
    ArrayList<String> img_urls;
    TextView image_list_title;
    String keyword;
    String style;
    int ith_request;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_list);

        detail_page_intent = getIntent();

        btn_back = findViewById(R.id.btn_back);
        imagesListView = findViewById(R.id.images_list_view);
        image_list_title = findViewById(R.id.image_list_title);

        img_urls = detail_page_intent.getStringArrayListExtra("img_urls");
        keyword = detail_page_intent.getStringExtra("keyword");
        style = detail_page_intent.getStringExtra("style");
        ith_request = detail_page_intent.getIntExtra("ith_request", 0);
        String title = img_urls.size()+" Images Generated";
        image_list_title.setText(title);

        images_adapter = new adapter_images_list((ArrayList<Bitmap>) ImageBitmap.images.get(ith_request), ImagesListPage.this);
        imagesListView.setAdapter(images_adapter);
        for (int i = 0; i < img_urls.size(); i++) {
            Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new OnDownloadCompleted() {
                @Override
                public void onDownloadCompleted(Bitmap bitmap) {
                    System.out.println("downloaded");
                    ImageBitmap.images.get(ith_request).add(bitmap);
                    images_adapter.notifyDataSetChanged();
                }
            });
            downloadImageTask.execute(img_urls.get(i));
        }
        System.out.println("adapter");

        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detail_page = new Intent();
                detail_page.setClass(ImagesListPage.this, ImageDetail.class);
                detail_page.putExtra("keyword", keyword);
                detail_page.putExtra("style", style);
                detail_page.putExtra("ith_request", ith_request);
                detail_page.putExtra("ith_image", i);

                startActivity(detail_page);
            }
        });

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