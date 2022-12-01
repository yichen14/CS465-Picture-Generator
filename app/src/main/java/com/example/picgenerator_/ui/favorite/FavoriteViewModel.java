package com.example.picgenerator_.ui.favorite;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {
    Bitmap img;
    String keyword;

    public FavoriteViewModel(Bitmap img, String keyword) {
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
