package com.example.picgenerator_.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.picgenerator_.R;

import java.util.ArrayList;

public class adapter_task_list extends ArrayAdapter<Task_model> implements View.OnClickListener {

    private ArrayList<Task_model> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txt_task_name;
        TextView txt_task_style;
        Button btn_task_status;
    }

    public adapter_task_list(ArrayList<Task_model> data, Context context) {
        super(context, R.layout.task_list_home, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        task_model dataModel=(task_model)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task_model dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_list_home, parent, false);
            viewHolder.txt_task_name = (TextView) convertView.findViewById(R.id.task_name);
            viewHolder.txt_task_style = (TextView) convertView.findViewById(R.id.task_style);
            viewHolder.btn_task_status = (Button) convertView.findViewById(R.id.task_status);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txt_task_name.setText(dataModel.getTask_name());
        viewHolder.txt_task_style.setText(dataModel.getStyle());
        viewHolder.btn_task_status.setText(dataModel.getTask_status());

        // Return the completed view to render on screen
        return convertView;
    }
}