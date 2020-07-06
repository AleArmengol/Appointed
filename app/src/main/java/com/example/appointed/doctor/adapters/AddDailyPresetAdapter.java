package com.example.appointed.doctor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appointed.R;
import com.example.appointed.models.TimeFrame;

import java.util.List;


public class AddDailyPresetAdapter extends ArrayAdapter<TimeFrame> {


    public AddDailyPresetAdapter(Context context, List<TimeFrame> time_frames) {
        super(context,0,time_frames);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TimeFrame timeFrame = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_add_preset_items, parent, false);
            TextView textStart = convertView.findViewById(R.id.text_start);
            textStart.setText("Hora de inicio: ");
            TextView textEnd = convertView.findViewById(R.id.text_end);
            textEnd.setText("Hora de fin: ");
            EditText edit_start1 = convertView.findViewById(R.id.edit_start1);
            EditText edit_start2 = convertView.findViewById(R.id.edit_start2);
            edit_start1.setText(timeFrame.getStart_time());
            edit_start2.setText(timeFrame.getStart_time());
            EditText edit_end1 = convertView.findViewById(R.id.edit_end1);
            EditText edit_end2 = convertView.findViewById(R.id.edit_end2);
            edit_end1.setText(timeFrame.getEnd_time());
            edit_end2.setText(timeFrame.getEnd_time());
        }
        return convertView;

    }

}
