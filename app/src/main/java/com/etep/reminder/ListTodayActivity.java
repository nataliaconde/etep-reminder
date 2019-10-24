package com.etep.reminder;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListTodayActivity  extends AppCompatActivity {

    int numbers[] = { 1, 2, 3};
    String[] NAMES = {"A", "B", "C"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_tasks_tab);

        final ListView listViewToday = (ListView) findViewById(R.id.viewPager);
        update(listViewToday);
    }

    public void update(ListView listViewToday) {
        CustomAdapter cusadp = new CustomAdapter();
        listViewToday.setAdapter(cusadp);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return numbers.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_item_view_layout, null);
            TextView title = (TextView) findViewById(R.id.txtTitle);
            TextView description = (TextView) findViewById(R.id.txtDescription);
            Log.d("debug", "AAAA > " + NAMES[i]);
            title.setText(NAMES[i]);
            description.setText(NAMES[i]);

            return view;
        }
    }
}