package com.example.picgenerator_.ui.home;

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

public class adapter_images_list  extends ArrayAdapter<Bitmap> {

    private ArrayList<Bitmap> dataSet;
    private List<String> img_urls;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView img_image_detail;
    }

    public adapter_images_list(ArrayList<Bitmap> data, Context context) {
        super(context, R.layout.images_list_detail, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Bitmap bitmap = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        adapter_images_list.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.images_list_detail, parent, false);
            viewHolder.img_image_detail = convertView.findViewById(R.id.image_detail);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (adapter_images_list.ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.img_image_detail.setImageBitmap(bitmap);
        // Return the completed view to render on screen
        return convertView;
    }
}