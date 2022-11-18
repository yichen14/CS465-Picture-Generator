package com.example.picgenerator_.ui.favorite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.picgenerator_.R;
import com.example.picgenerator_.databinding.FragmentFavBinding;
import com.example.picgenerator_.ui.APICalls.Images;
import com.example.picgenerator_.ui.Images.ImageDetail;
import com.example.picgenerator_.ui.Images.ImagesListPage;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FragmentFavBinding binding;

    ListView imagesListViewFav;
    static ArrayList<String> img_urls = new ArrayList<>();
    ArrayList<Bitmap> downloaded_imgs;
    AdapterImageListFavorite images_adapter;
    static String keyword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteViewModel favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imagesListViewFav = binding.favList;

        downloaded_imgs = new ArrayList<>();
        images_adapter = new AdapterImageListFavorite(keyword, downloaded_imgs, getActivity());
        imagesListViewFav.setAdapter(images_adapter);

        for (int i = 0; i < img_urls.size(); i++) {
            Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new ImagesListPage.OnDownloadCompleted() {
                @Override
                public void onDownloadCompleted(Bitmap bitmap) {
                    System.out.println("downloaded");
                    downloaded_imgs.add(bitmap);
                    images_adapter.notifyDataSetChanged();
                }
            });
            downloadImageTask.execute(img_urls.get(i));
        }

        return root;
    }

    public static void addImg(String url, String kd) {
        img_urls.add(url);
        keyword = kd;
    }

    public interface OnDownloadCompleted{
        void onDownloadCompleted(Bitmap bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

//
//public class FavoritePage extends Activity {
//    ListView imagesListView;
//    AdapterImageListFavorite images_adapter;
//    ArrayList<String> img_urls;
//    ArrayList<Bitmap> downloaded_imgs;
//    String keyword;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_fav);
//
//        imagesListView = findViewById(R.id.fav_list);
//
//
//        downloaded_imgs = new ArrayList<>();
//        images_adapter = new AdapterImageListFavorite(downloaded_imgs, FavoritePage.this);
//        imagesListView.setAdapter(images_adapter);
//        for (int i = 0; i < img_urls.size(); i++) {
//            Images.DownloadImageTask downloadImageTask = new Images.DownloadImageTask(null, new ImagesListPage.OnDownloadCompleted() {
//                @Override
//                public void onDownloadCompleted(Bitmap bitmap) {
//                    System.out.println("downloaded");
//                    downloaded_imgs.add(bitmap);
//                    images_adapter.notifyDataSetChanged();
//                }
//            });
//            downloadImageTask.execute(img_urls.get(i));
//        }
//        System.out.println("adapter");
//
//        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent detail_page = new Intent();
//                detail_page.setClass(FavoritePage.this, ImageDetail.class);
//                detail_page.putExtra("img_url", img_urls.get(i));
//                detail_page.putExtra("keyword", keyword);
//                startActivity(detail_page);
//            }
//        });
//    }
//
//
//    public void addToImgUrls(String img_url) {
//        this.img_urls.add(img_url);
//    }
//
//    public interface OnDownloadCompleted{
//        void onDownloadCompleted(Bitmap bitmap);
//    }
//}
