package com.example.picgenerator_.ui.favorite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImageDetail;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.ui.gallery.AdapterImageListGallery;
import com.example.picgenerator_.ui.gallery.GalleryPage;

import java.util.ArrayList;

public class FavoritePage extends Activity {
    Intent detail_page_intent;
    ListView imagesListView;
    AdapterImageListFavorite images_adapter;
    ArrayList<String> img_urls;
    ArrayList<Bitmap> downloaded_imgs;
    String keyword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fav);

        detail_page_intent = getIntent();

        imagesListView = findViewById(R.id.fav_list);
        img_urls = detail_page_intent.getStringArrayListExtra("img_urls");
        keyword = detail_page_intent.getStringExtra("keyword");
        String title = img_urls.size()+" Images Generated";
        System.out.println(title);

        downloaded_imgs = new ArrayList<>();
        images_adapter = new AdapterImageListFavorite(downloaded_imgs, FavoritePage.this);
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
                detail_page.setClass(FavoritePage.this, ImageDetail.class);
                detail_page.putExtra("img_url", img_urls.get(i));
                detail_page.putExtra("keyword", keyword);
                startActivity(detail_page);
            }
        });
    }


    public interface OnDownloadCompleted{
        void onDownloadCompleted(Bitmap bitmap);
    }
}
