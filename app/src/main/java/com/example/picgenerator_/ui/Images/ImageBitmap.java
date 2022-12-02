package com.example.picgenerator_.ui.Images;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageBitmap {
    static List<List<Bitmap>> images = new ArrayList<List<Bitmap>>(20);

    public static Uri saveTempBitmap(Bitmap bitmap) {
        if (isExternalStorageWritable()) {
            return saveImage(bitmap);
        }else{
            System.out.println("Save image fail");
            return null;
        }
    }

    private static Uri saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
        File myDir = new File(root);
        System.out.println(root);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "PicGenerator_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.fromFile(file);
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public ImageBitmap() {
        for (int i = 0; i < 10; i++) {
            images.add(new ArrayList<Bitmap>());
        }
    }

}
