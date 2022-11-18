package com.example.picgenerator_.ui.gallery;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    Bitmap url;
    String keyword;

    public GalleryViewModel(Bitmap url, String keyword) {
        this.url = url;
        this.keyword = keyword;
    }

    public Bitmap getImg() {
        return url;
    }

    public String getKeyword() {
        return keyword;
    }
}