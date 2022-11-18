package com.example.picgenerator_.ui.Images;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.APICalls.Images;

public class ImageDetail extends Activity {
    ImageButton btn_back;
    ImageView generated_image;
    Bitmap img;
    byte[] byteArray;
    String img_url;
    Intent image_list_intent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details);

        btn_back = findViewById(R.id.btn_back);
        generated_image = findViewById(R.id.generated_image);

        image_list_intent = getIntent();
//        byteArray = image_list_intent.getByteArrayExtra("img_byteArray");
        img_url = image_list_intent.getStringExtra("img_url");
//        img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new ImagesListPage.OnDownloadCompleted() {
            @Override
            public void onDownloadCompleted(Bitmap bitmap) {
                System.out.println("ondownloadlistener triggers");
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
    }
}
