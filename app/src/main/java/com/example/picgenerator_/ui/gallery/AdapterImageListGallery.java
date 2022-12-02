package com.example.picgenerator_.ui.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.picgenerator_.R;
import java.util.ArrayList;
import java.util.List;


import android.widget.TextView;

public class AdapterImageListGallery extends ArrayAdapter<GalleryViewModel> {
    private ArrayList<GalleryViewModel> dataSet;
    private List<String> imgUrls;
    Context mContext;
    String kd;

    // View lookup cache
    private static class ViewHolder {
        ImageView img_fav;
        TextView keyword;
    }

    public AdapterImageListGallery(ArrayList<GalleryViewModel> data, Context context) {
        super(context, R.layout.fragment_fav, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bitmap bitmap = getItem(position).getImg();
        String kd = getItem(position).getKeyword();
        // Check if an existing view is being reused, otherwise inflate the view
        AdapterImageListGallery.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new AdapterImageListGallery.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fav_img_list_adapter, parent, false);
            viewHolder.img_fav = convertView.findViewById(R.id.img);
            viewHolder.keyword = convertView.findViewById(R.id.keyword);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdapterImageListGallery.ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.img_fav.setImageBitmap(bitmap);
        viewHolder.keyword.setText(kd);

        // Return the completed view to render on screen
        return convertView;
    }
}
