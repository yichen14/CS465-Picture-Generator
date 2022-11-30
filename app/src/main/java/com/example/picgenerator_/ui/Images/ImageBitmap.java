package com.example.picgenerator_.ui.Images;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ImageBitmap {
    static List<List<Bitmap>> images = new ArrayList<List<Bitmap>>(20);

    public ImageBitmap() {
        for (int i = 0; i < 10; i++) {
            images.add(new ArrayList<Bitmap>());
        }
    }
}
