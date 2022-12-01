package com.example.picgenerator_;

import android.os.Bundle;

import com.example.picgenerator_.ui.Images.ImageBitmap;
import com.example.picgenerator_.ui.gallery.GalleryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.picgenerator_.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favorite, R.id.navigation_gallery)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        new ImageBitmap();

//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a8830", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88v9", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88a2", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88ex", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a885q", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/6a3b99bc21171a8bed413237aa8e2a88i4", "School on corn field");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845daex", "Panda eating bamboo");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845dai4", "Panda eating bamboo");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845da30", "Panda eating bamboo");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845da5q", "Panda eating bamboo");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845daa2", "Panda eating bamboo");
//        GalleryFragment.addImg("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/91b9a61e772856c26beab9426ac845dav9", "Panda eating bamboo");
    }
}