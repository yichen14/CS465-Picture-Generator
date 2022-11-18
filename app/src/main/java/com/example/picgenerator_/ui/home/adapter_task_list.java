package com.example.picgenerator_.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.picgenerator_.R;

import java.util.ArrayList;

public class adapter_task_list extends ArrayAdapter<Task_model> {

    private ArrayList<Task_model> dataSet;
    Context mContext;

    // View lookup cache
    static class ViewHolder {
        TextView txt_task_name;
        TextView txt_task_style;
        TextView txt_task_status;
    }

    public adapter_task_list(ArrayList<Task_model> data, Context context) {
        super(context, R.layout.task_list_adapter, data);
        this.dataSet = data;
        this.mContext=context;
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
            convertView = inflater.inflate(R.layout.task_list_adapter, parent, false);
            viewHolder.txt_task_name = (TextView) convertView.findViewById(R.id.task_name);
            viewHolder.txt_task_style = (TextView) convertView.findViewById(R.id.task_style);
            viewHolder.txt_task_status = (TextView) convertView.findViewById(R.id.task_status);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txt_task_name.setText(dataModel.getTask_name());
        viewHolder.txt_task_style.setText(dataModel.getStyle());
        viewHolder.txt_task_status.setText(dataModel.getTask_status());

        // Return the completed view to render on screen
        return convertView;
    }
}