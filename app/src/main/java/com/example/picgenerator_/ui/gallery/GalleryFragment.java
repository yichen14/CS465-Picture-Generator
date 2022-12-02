package com.example.picgenerator_.ui.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.databinding.FragmentGalleryBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImageDetail;
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
    static ArrayList<Pair<Integer, Integer>> iths = new ArrayList<>();
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
//        showing_imgs = new ArrayList<>(downloaded_imgs);
        images_adapter = new AdapterImageListGallery(downloaded_imgs, getActivity());

        imagesListViewGallery.setAdapter(images_adapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_search = search_bar3.getText().toString();
                showing_imgs = new ArrayList<>(downloaded_imgs);
                showing_imgs.removeIf(i -> !i.getKeyword().toLowerCase().contains(txt_search.toLowerCase()));
//                images_adapter.notifyDataSetChanged();
                AdapterImageListGallery tmp = new AdapterImageListGallery(showing_imgs, getActivity());
                imagesListViewGallery.setAdapter(tmp);
            }
        });

        imagesListViewGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detail_page = new Intent();
                detail_page.setClass(getActivity(), ImageDetail.class);
                detail_page.putExtra("keyword", keywords.get(i));
//                detail_page.putExtra("style", style);
                detail_page.putExtra("ith_request", iths.get(i).first);
                detail_page.putExtra("ith_image", iths.get(i).second);

                startActivity(detail_page);
            }
        });

        return root;
    }

    public static void addImg(Bitmap img, String kd, int ith_request, int ith_img) {
        imgs.add(img);
        keywords.add(kd);
        iths.add(new Pair<>(ith_request, ith_img));
    }
    public static void removeImg(Bitmap img, String kd, int ith_request, int ith_img) {
        for (int i = 0; i < imgs.size(); i++) {
            if (iths.get(i).first == ith_request && iths.get(i).second == ith_img) {
                imgs.remove(i);
                keywords.remove(i);
                iths.remove(i);
                break;
            }
        }
    }

    public static boolean checkIfExist(int ith_request, int ith_img) {
        for (Pair<Integer, Integer> i : iths) {
            if (i.first == ith_request && i.second == ith_img) {
                return true;
            }
        }
        return false;
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
