package com.example.picgenerator_.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImageDetail;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.ui.home.adapter_images_list;

import java.util.ArrayList;
/**
 * todo: 把gallery里面的image传到page里面
 */
public class GalleryPage extends Activity {
    Intent detail_page_intent;
    Button btn_search;
    ListView imagesListView;
    EditText searchBar;
    AdapterImageListGallery images_adapter;
    ArrayList<String> img_urls;
    ArrayList<Bitmap> downloaded_imgs;
    String keyword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);

        detail_page_intent = getIntent();

        btn_search = findViewById(R.id.btn_search);
        imagesListView = findViewById(R.id.gallery_list);
        searchBar = findViewById(R.id.search_bar3);
        img_urls = detail_page_intent.getStringArrayListExtra("img_urls");
        keyword = detail_page_intent.getStringExtra("keyword");
        String title = img_urls.size()+" Images Generated";
        System.out.println(title);

        downloaded_imgs = new ArrayList<>();
        images_adapter = new AdapterImageListGallery(downloaded_imgs, GalleryPage.this);
        imagesListView.setAdapter(images_adapter);
        for (int i = 0; i < img_urls.size(); i++) {
            Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new ImagesListPage.OnDownloadCompleted() {
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

        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detail_page = new Intent();
                detail_page.setClass(GalleryPage.this, ImageDetail.class);
                detail_page.putExtra("img_url", img_urls.get(i));
                detail_page.putExtra("keyword", keyword);
                startActivity(detail_page);
            }
        });
        /**
         * todo: implement effect after clicking search button
         */
        btn_search.setOnClickListener(new View.OnClickListener() {
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
