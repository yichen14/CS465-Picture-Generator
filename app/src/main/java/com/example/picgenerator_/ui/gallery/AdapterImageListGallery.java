package com.example.picgenerator_.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.home.adapter_images_list;

import java.util.ArrayList;
import java.util.List;

public class AdapterImageListGallery extends ArrayAdapter<Bitmap> {
    private ArrayList<Bitmap> dataSet;
    private List<String> imgUrls;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView img_image_detail;
    }

    public AdapterImageListGallery(ArrayList<Bitmap> data, Context context) {
        super(context, R.layout.images_list_adapter, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bitmap bitmap = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        AdapterImageListGallery.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new AdapterImageListGallery.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.images_list_adapter, parent, false);
            viewHolder.img_image_detail = convertView.findViewById(R.id.image_detail);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdapterImageListGallery.ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.img_image_detail.setImageBitmap(bitmap);
        // Return the completed view to render on screen
        return convertView;
    }
}
