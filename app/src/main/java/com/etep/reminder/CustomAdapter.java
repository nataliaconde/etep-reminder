package com.etep.reminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemReminder> list;

    public CustomAdapter(Context context, ArrayList<ItemReminder> list){
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
        TextView objectId = (TextView) view.findViewById(R.id.txtObjectId);
        TextView description = (TextView) view.findViewById(R.id.txtDescription);
        TextView date = (TextView)  view.findViewById(R.id.txtDate);
        TextView time = (TextView)  view.findViewById(R.id.txtTime);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        title.setText(itemReminder.getTitle());
        description.setText(itemReminder.getDescription());
        imageView.setImageResource(itemReminder.getImageView());
        objectId.setText(itemReminder.getObjectId());
        date.setText(itemReminder.getDate());
        time.setText(itemReminder.getTime());

        return view;
    }
}
