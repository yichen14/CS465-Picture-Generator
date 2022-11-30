package com.example.picgenerator_.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.picgenerator_.ui.APICalls.PostTranslation;

import org.json.JSONException;

import java.util.Hashtable;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    // translating style from English to Chinese
    public String getStyle(String s) {
        Hashtable<String, String> dict = new Hashtable<String, String>();
        dict.put("Classic", "古风");
        dict.put("Anime", "二次元");
        dict.put("Realistic", "写实风格");
        dict.put("Ukiyo-e", "浮世绘");
        dict.put("Low Poly", "low poly");
        dict.put("Futurism", "未来主义");
        dict.put("Pixel Style", "像素风格");
        dict.put("Conceptual", "概念艺术");
        dict.put("Cyberpunk", "赛博朋克");
        dict.put("Lolita", "洛丽塔风格");
        dict.put("Baroque", "巴洛克风格");
        dict.put("Surrealism", "超现实主义");
        dict.put("Aquarelle Painting", "水彩画");
        dict.put("Vaporwave", "蒸汽波艺术");
        dict.put("Oil Painting", "油画");
        dict.put("Cartoon", "卡通画");
        return dict.get(s);
    }

    public LiveData<String> getText() {
        return mText;
    }
}