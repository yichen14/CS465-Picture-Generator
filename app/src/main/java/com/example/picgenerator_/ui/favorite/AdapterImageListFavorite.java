package com.example.picgenerator_.ui.favorite;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.picgenerator_.R;
import com.example.picgenerator_.ui.gallery.AdapterImageListGallery;

import java.util.ArrayList;
import java.util.List;

public class AdapterImageListFavorite extends ArrayAdapter<FavoriteViewModel> {
    private ArrayList<FavoriteViewModel> dataSet;
    private List<String> imgUrls;
    Context mContext;
    String kd;

    // View lookup cache
    private static class ViewHolder {
        ImageView img_fav;
        TextView keyword;
    }

    public AdapterImageListFavorite(ArrayList<FavoriteViewModel> data, Context context) {
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
        AdapterImageListFavorite.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new AdapterImageListFavorite.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fav_img_list_adapter, parent, false);
            viewHolder.img_fav = convertView.findViewById(R.id.img);
            viewHolder.keyword = convertView.findViewById(R.id.keyword);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AdapterImageListFavorite.ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.img_fav.setImageBitmap(bitmap);
        viewHolder.keyword.setText(kd);

        // Return the completed view to render on screen
        return convertView;
    }
}
