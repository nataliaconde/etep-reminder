package com.etep.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapterSpinner extends ArrayAdapter {

    public CustomAdapterSpinner(@NonNull Context context, ArrayList<CustomItem> customList) {
        super(context, 0, customList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }
        CustomItem item = (CustomItem) getItem(position);
        ImageView spinnerImage = convertView.findViewById(R.id.ivSpinnerLayout);
        TextView spinnerText = convertView.findViewById(R.id.tvSpinnerLayout);
        if(item != null) {
            spinnerImage.setImageResource((item.getSpinnerIntImage()));
            spinnerText.setText((item.getSpinnerItemName()));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dropdown_layout, parent, false);
        }
        CustomItem item = (CustomItem) getItem(position);
        ImageView dropdownIV = convertView.findViewById(R.id.ivDropDownLayout);
        TextView dropdownTV = convertView.findViewById(R.id.tvDropDownLayout);
        if(item != null) {
            dropdownIV.setImageResource((item.getSpinnerIntImage()));
            dropdownTV.setText((item.getSpinnerItemName()));
        }
        return convertView;
    }
}
