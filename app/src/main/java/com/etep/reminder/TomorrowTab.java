package com.etep.reminder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TomorrowTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.tomorrow_tasks_tab, container, false);
        final ListView listViewToday = (ListView) view.findViewById(R.id.listViewToday);
        final ArrayList<ItemReminder> items = new ArrayList<ItemReminder>();

        Log.d("DEBUG","DEBUG" + String.valueOf(ParseUser.getCurrentUser().getObjectId()));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> List, ParseException e) {

                if (e == null) {
                    for(ParseObject object : List){

                        ItemReminder itemReminder = new ItemReminder();
                        itemReminder.setTitle(object.getString("title"));

                        Log.d("DEBUG","DEBUG" + object.getString("title"));

                        itemReminder.setDescription(object.getString("description"));
                        itemReminder.setDate(object.getString("date"));
                        itemReminder.setPriority(object.getString("priority"));

                        items.add(itemReminder);
                    }
                    CustomAdapterToday cusadp = new CustomAdapterToday(getActivity(), items);
                    listViewToday.setAdapter(cusadp);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

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
