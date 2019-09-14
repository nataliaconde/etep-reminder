package com.etep.reminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterToday extends BaseAdapter {
    private Context context;
    private ArrayList<ItemReminder> list;

    public CustomAdapterToday(Context context, ArrayList<ItemReminder> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ItemReminder itemReminder = list.get(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_item_view_layout, null);

        TextView title = (TextView) view.findViewById(R.id.txtTitle);
        TextView description = (TextView) view.findViewById(R.id.txtDescription);
        title.setText(itemReminder.getTitle());
        description.setText(itemReminder.getDescription());

        return view;
    }
}
