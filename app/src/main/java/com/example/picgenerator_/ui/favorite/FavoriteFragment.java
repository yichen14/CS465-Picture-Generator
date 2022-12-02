package com.example.picgenerator_.ui.favorite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.picgenerator_.databinding.FragmentFavBinding;
import com.example.picgenerator_.ui.Images.ImageDetail;
import com.example.picgenerator_.ui.Images.ImagesListPage;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavBinding binding;

    ListView imagesListViewFav;
    static ArrayList<Bitmap> imgs = new ArrayList<>();
    static ArrayList<String> keywords = new ArrayList<>();
    static ArrayList<Pair<Integer, Integer>> iths = new ArrayList<>();
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

        imagesListViewFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
