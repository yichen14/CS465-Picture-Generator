package com.example.picgenerator_;

import android.app.Activity;
import android.os.Bundle;

public class Generate extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("im here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imagedetails);
    }
}