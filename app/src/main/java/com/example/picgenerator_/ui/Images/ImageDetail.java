package com.example.picgenerator_.ui.Images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.favorite.FavoriteFragment;

/**
 * todo: 实现image上传到gallery和加入favorite
 */
public class ImageDetail extends Activity {
    ImageButton btn_back;
    ImageButton btn_fav;
    ImageButton btn_gallery;
    ImageView generated_image;
    TextView image_title;
    String img_url;
    String keyword;
    Intent image_list_intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details);

        btn_back = findViewById(R.id.btn_back);
        btn_fav = findViewById(R.id.btn_fav);
        btn_gallery = findViewById(R.id.btn_gallery);
        generated_image = findViewById(R.id.generated_image);
        image_title = findViewById(R.id.image_title);

        image_list_intent = getIntent();
        img_url = image_list_intent.getStringExtra("img_url");
        keyword = image_list_intent.getStringExtra("keyword");
        image_title.setText(keyword);

        Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new ImagesListPage.OnDownloadCompleted() {
            @Override
            public void onDownloadCompleted(Bitmap bitmap) {
                generated_image.setImageBitmap(bitmap);
            }
        });
        downloadImageTask.execute(img_url);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteFragment.addImg(img_url, keyword);
            }
        });

//        btn_gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }
}
