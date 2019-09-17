package com.etep.reminder;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TodayTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.today_tasks_tab, container, false);
        ListView listViewToday = (ListView) view.findViewById(R.id.listViewToday);

        ArrayList<ItemReminder> items = new ArrayList<ItemReminder>();

        for(int i = 0; i < 2; i++){

            ItemReminder itemReminder = new ItemReminder();
            itemReminder.setTitle("Carro "+ ("a"  + 1));
            itemReminder.setDescription("Marca "+ (i + 1));

            items.add(itemReminder);
        }

        CustomAdapterToday cusadp = new CustomAdapterToday(getActivity(), items);
        listViewToday.setAdapter(cusadp);

        listViewToday.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                Toast.makeText(getActivity(), "Movie Selected : "+position,   Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
