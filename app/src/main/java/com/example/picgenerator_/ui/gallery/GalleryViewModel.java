package com.example.picgenerator_.ui.gallery;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    Bitmap img;
    String keyword;

    public GalleryViewModel(Bitmap img, String keyword) {
        this.img = img;
        this.keyword = keyword;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getKeyword() {
        return keyword;
    }
}