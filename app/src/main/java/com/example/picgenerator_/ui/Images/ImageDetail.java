package com.example.picgenerator_.ui.Images;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.favorite.FavoriteFragment;
import com.example.picgenerator_.ui.gallery.GalleryFragment;

public class ImageDetail extends Activity {
    ImageButton btn_back;
    ImageButton btn_fav;
    ImageButton btn_gallery;
    ImageView generated_image;
    TextView image_title;
    String img_url;
    String keyword;
    String style;
    int ith_request;
    int ith_image;
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
        keyword = image_list_intent.getStringExtra("keyword");
        style = image_list_intent.getStringExtra("style");
        ith_request = image_list_intent.getIntExtra("ith_request", 0);
        ith_image = image_list_intent.getIntExtra("ith_image", 0);

        image_title.setText(keyword);
        generated_image.setImageBitmap(ImageBitmap.images.get(ith_request).get(ith_image));


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ImageDetail.this, "Saved to myFavourite", Toast.LENGTH_SHORT).show();
                FavoriteFragment.addImg(ImageBitmap.images.get(ith_request).get(ith_image), keyword);
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ImageDetail.this, "Uploaded to gallery", Toast.LENGTH_SHORT).show();
                GalleryFragment.addImg(img_url, keyword);
            }
        });
    }
}
