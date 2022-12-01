package com.example.picgenerator_.ui.gallery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.databinding.FragmentGalleryBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.ui.favorite.AdapterImageListFavorite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class
GalleryFragment extends Fragment {
    ListView imagesListViewGallery;
    Button btn_search;
    EditText search_bar3;
//    static ArrayList<String> img_urls = new ArrayList<>();
    static ArrayList<Bitmap> imgs = new ArrayList<>();
    ArrayList<GalleryViewModel> downloaded_imgs;
    ArrayList<GalleryViewModel> showing_imgs;
    AdapterImageListGallery images_adapter;
    static ArrayList<String> keywords = new ArrayList<>();
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imagesListViewGallery = binding.galleryList;
        btn_search = binding.btnSearch;
        search_bar3 = binding.searchBar3;

        downloaded_imgs = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            downloaded_imgs.add(new GalleryViewModel(imgs.get(i), keywords.get(i)));
        }
        images_adapter = new AdapterImageListGallery(downloaded_imgs, getActivity());

        imagesListViewGallery.setAdapter(images_adapter);
//        for (int i = 0; i < img_urls.size(); i++) {
//            Images.DownloadImageTaskTmp downloadImageTask = new Images.DownloadImageTaskTmp(i, null, new GalleryFragment.OnDownloadCompletedTmp() {
//                @Override
//                public void OnDownloadCompletedTmp(Bitmap bitmap, int i) {
//                    System.out.println("downloaded");
//                    downloaded_imgs.add(new GalleryViewModel(bitmap, keywords.get(i)));
//                    showing_imgs = downloaded_imgs;
//                    images_adapter.notifyDataSetChanged();
//                }
//            });
//            downloadImageTask.execute(img_urls.get(i));
//        }


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_search = search_bar3.getText().toString();
                showing_imgs = downloaded_imgs;
                showing_imgs.removeIf(i -> !i.getKeyword().toLowerCase().contains(txt_search.toLowerCase()));
                images_adapter.notifyDataSetChanged();
            }
        });

        return root;
    }



    public static void addImg(Bitmap img, String kd) {
        imgs.add(img);
        keywords.add(kd);
    }
    public interface OnDownloadCompletedTmp{
        void OnDownloadCompletedTmp(Bitmap bitmap, int i);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
//[https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a8830, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88v9, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88a2, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88ex, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a885q, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88i4]
//[https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845daex, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845dai4, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845da30, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845da5q, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845daa2, https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845dav9]