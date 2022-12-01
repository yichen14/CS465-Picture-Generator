package com.example.picgenerator_.ui.favorite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import com.example.picgenerator_.R;
import com.example.picgenerator_.databinding.FragmentFavBinding;
import com.example.picgenerator_.databinding.FragmentGalleryBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImageDetail;
import com.example.picgenerator_.ui.Images.ImagesListPage;
import com.example.picgenerator_.ui.gallery.AdapterImageListGallery;
import com.example.picgenerator_.ui.gallery.GalleryViewModel;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavBinding binding;

    ListView imagesListViewFav;
    static ArrayList<Bitmap> imgs = new ArrayList<>();
    static ArrayList<String> keywords = new ArrayList<>();
    ArrayList<FavoriteViewModel> downloaded_imgs;
    AdapterImageListFavorite images_adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imagesListViewFav = binding.favList;
        downloaded_imgs = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            downloaded_imgs.add(new FavoriteViewModel(imgs.get(i), keywords.get(i)));
        }
        images_adapter = new AdapterImageListFavorite(downloaded_imgs, getActivity());
        imagesListViewFav.setAdapter(images_adapter);

        return root;
    }

    public static void addImg(Bitmap img, String kd) {
        imgs.add(img);
        keywords.add(kd);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
