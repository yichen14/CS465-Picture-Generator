package com.example.picgenerator_.ui.Images;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picgenerator_.MainActivity;
import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.favorite.FavoriteFragment;
import com.example.picgenerator_.ui.gallery.GalleryFragment;
import android.graphics.Bitmap;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDetail extends Activity {
    ImageButton btn_back;
    ImageButton btn_fav;
    ImageButton btn_gallery;
    ImageButton btn_download;
    ImageButton btn_share;
    ImageView generated_image;
    TextView image_title;
    Animation scaleUp, scaleDown;
    String img_url;
    String keyword;
    String style;
    int ith_request;
    int ith_image;
    Intent image_list_intent;
    int flag = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details);

        btn_back = findViewById(R.id.btn_back);
        btn_fav = findViewById(R.id.btn_fav);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_download = findViewById(R.id.imagebutton9);
        btn_share = findViewById(R.id.imagebutton10);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);


        generated_image = findViewById(R.id.generated_image);
        image_title = findViewById(R.id.image_title);

        image_list_intent = getIntent();
        keyword = image_list_intent.getStringExtra("keyword");
        style = image_list_intent.getStringExtra("style");
        ith_request = image_list_intent.getIntExtra("ith_request", 0);
        ith_image = image_list_intent.getIntExtra("ith_image", 0);

        image_title.setText(keyword);
        generated_image.setImageBitmap(ImageBitmap.images.get(ith_request).get(ith_image));
        final Context ctx = this;

        btn_fav.setActivated(FavoriteFragment.checkIfExist(ith_request, ith_image));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_back.startAnimation(scaleUp);
                btn_back.startAnimation(scaleDown);
                finish();
            }
        });

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_fav.startAnimation(scaleUp);
                btn_fav.startAnimation(scaleDown);
                if (FavoriteFragment.checkIfExist(ith_request, ith_image)) {
                    btn_fav.setActivated(false);
                    Toast.makeText(ImageDetail.this, "Unsaved to myFavourite", Toast.LENGTH_SHORT).show();
                    FavoriteFragment.removeImg(ImageBitmap.images.get(ith_request).get(ith_image), keyword, ith_request, ith_image);
                } else {
                    btn_fav.setActivated(true);
                    Toast.makeText(ImageDetail.this, "Saved to myFavourite", Toast.LENGTH_SHORT).show();
                    FavoriteFragment.addImg(ImageBitmap.images.get(ith_request).get(ith_image), keyword, ith_request, ith_image);
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_gallery.startAnimation(scaleUp);
                btn_gallery.startAnimation(scaleDown);
                if (GalleryFragment.checkIfExist(ith_request, ith_image)) {
                    Toast.makeText(ImageDetail.this, "Unsaved to gallery", Toast.LENGTH_SHORT).show();
                    GalleryFragment.removeImg(ImageBitmap.images.get(ith_request).get(ith_image), keyword, ith_request, ith_image);
                } else {
                    Toast.makeText(ImageDetail.this, "Uploaded to gallery", Toast.LENGTH_SHORT).show();
                    GalleryFragment.addImg(ImageBitmap.images.get(ith_request).get(ith_image), keyword, ith_request, ith_image);
                }
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_download.startAnimation(scaleUp);
                btn_download.startAnimation(scaleDown);
                Toast.makeText(ImageDetail.this, "Saved to local", Toast.LENGTH_SHORT).show();
                Uri uri = ImageBitmap.saveTempBitmap(ImageBitmap.images.get(ith_request).get(ith_image));
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_share.startAnimation(scaleUp);
                btn_share.startAnimation(scaleDown);
                File imagesFolder = new File(getCacheDir(), "images");
                Uri uri = null;
                try {
                    imagesFolder.mkdirs();
                    File file = new File(imagesFolder, "shared_image.png");

                    FileOutputStream stream = new FileOutputStream(file);
                    ImageBitmap.images.get(ith_request).get(ith_image).compress(Bitmap.CompressFormat.PNG, 90, stream);
                    stream.flush();
                    stream.close();
                    uri = FileProvider.getUriForFile(ctx, ctx.getApplicationContext().getPackageName() + ".provider", file);

                } catch (IOException e) {
                    Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
                }


                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, "Share image using"));
            }
        });
    }
}
