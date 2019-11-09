package com.etep.reminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.SubscriptionHandling;

import java.util.ArrayList;
import java.util.List;

public class NextDaysTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.next_days_tasks_tab, container, false);
        final ListView listViewNextDays = (ListView) view.findViewById(R.id.listNextDays);
        TextView empty = (TextView) view.findViewById(R.id.empty);
        listViewNextDays.setEmptyView(empty);

        final ArrayList<ItemReminder> items = new ArrayList<ItemReminder>();

        final SwipeRefreshLayout mSwipeRefreshLayout;
        mSwipeRefreshLayout = view.findViewById(R.id.swipeContainerNextDays);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAllContent(listViewNextDays, items);
                        mSwipeRefreshLayout.setRefreshing(false);
                        //Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        SharedConnection parseL = new SharedConnection();
        ParseLiveQueryClient parseLiveQueryClient = parseL.get();

        objectCreated(parseLiveQueryClient, listViewNextDays, items);
        objectUpdated(parseLiveQueryClient, listViewNextDays, items);
        objectDeleted(parseLiveQueryClient, listViewNextDays, items);

       getAllContent(listViewNextDays, items);
        return view;
    }

    public void notifyDataContent(final ListView listViewNextDays, final ArrayList items) {
        CustomAdapter cusadp = new CustomAdapter(getActivity(), items);
        listViewNextDays.setAdapter(cusadp);
    }

    public void getAllContent(final ListView listViewNextDays, final ArrayList items) {
        items.clear();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("status", "nextdays");
        query.orderByAscending("datetime");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> List, ParseException e) {

                if (e == null) {
                    for (ParseObject object : List) {
                        ItemReminder itemReminder = new ItemReminder();
                        itemReminder.setTitle(formatTextWithDots("title", object.getString("title")));
                        itemReminder.setDescription(formatTextWithDots("description", object.getString("description")));
                        itemReminder.setDate(object.getString("date"));
                        itemReminder.setTime(object.getString("time"));
                        itemReminder.setPriority(object.getString("priority"));
                        itemReminder.setObjectId(object.getObjectId());
                        int resourceId;
                        if (getView() != null && object.getString("priority") != null) {
                            switch (object.getString("priority")) {
                                case "low":
                                    resourceId = getView().getResources().getIdentifier("prioritylow", "drawable", "com.etep.reminder");
                                    itemReminder.setImageView(resourceId);
                                    break;
                                case "medium":
                                    resourceId = getView().getResources().getIdentifier("prioritymedium", "drawable", "com.etep.reminder");
                                    itemReminder.setImageView(resourceId);
                                    break;
                                case "high":
                                    resourceId = getView().getResources().getIdentifier("priorityhigh", "drawable", "com.etep.reminder");
                                    itemReminder.setImageView(resourceId);
                                    break;
                                default:
                                    resourceId = getView().getResources().getIdentifier("prioritylow", "drawable", "com.etep.reminder");
                                    itemReminder.setImageView(resourceId);
                                    break;

                            }
                        }

                        items.add(itemReminder);
                    }
                    CustomAdapter cusadp = new CustomAdapter(getActivity(), items);
                    listViewNextDays.setAdapter(cusadp);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        listViewNextDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                TextView txtobjectId = (TextView) v.findViewById(R.id.txtObjectId);
                String object = txtobjectId.getText().toString();
                String title = getString(R.string.titleAlert);
                String action = getString(R.string.actionAlert);
                choice(title, action, object, listViewNextDays, items, position);
            }
        });
    }

    private void choice(String title, String action, final String objectId, final ListView listViewNextDays, final ArrayList items, final int position) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(action);
        builder.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                deleteElement(objectId);
                items.remove(position);
                notifyDataContent(listViewNextDays, items);
            }
        });
        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                updateElement(objectId);

            }
        });

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alert = builder.create();
        alert.show();
    }

    public void deleteElement(String objectId) {
        final String[] message = {""};
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    object.deleteInBackground();
                    message[0] = getString(R.string.itemObjectDeleted);
                } else {
                    message[0] = getString(R.string.itemObjectNotDeleted);
                }
            }
        });
        Toast.makeText(getActivity(), message[0], Toast.LENGTH_SHORT).show();
    }

    public String updateElement(String objectId) {
        final String[] message = {""};
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Reminder");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(getActivity(), AddTasks.class);
                    intent.putExtra("update", true);
                    intent.putExtra("objectId", object.getObjectId());
                    intent.putExtra("title", object.getString("title"));
                    intent.putExtra("description", object.getString("description"));
                    intent.putExtra("date", object.getString("date"));
                    intent.putExtra("time", object.getString("time"));
                    intent.putExtra("userId", object.getString("userId"));
                    intent.putExtra("priority", object.getString("priority"));
                    startActivity(intent);
                } else {
                    message[0] = getString(R.string.itemObjectNotDeleted);
                }
            }
        });
        return message[0];
    }

    public String formatTextWithDots(String type, String data){
        String text = "";
        if (type.equals("title") && data.length() >= 30 ) {
            text = data.substring(0, 30)+ "...";
        } else if (type.equals("description") && data.length() >= 42 ){
            text = data.substring(0, 42)+ "...";
        } else {
            text = data;
        }
        Log.d("formatText", text);
        return (text);
    }

    public void objectUpdated(ParseLiveQueryClient parseLiveQueryClient, final ListView listViewToday, final ArrayList items){
        if (parseLiveQueryClient != null) {
            ParseQuery<ParseObject> parseQuery = new ParseQuery("Reminder");
            parseQuery.whereEqualTo("user", ParseUser.getCurrentUser());
            SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            getAllContent(listViewToday, items);
                        }
                    });
                }
            });
        }
    }

    public void objectDeleted(ParseLiveQueryClient parseLiveQueryClient, final ListView listViewToday, final ArrayList items) {
        if (parseLiveQueryClient != null) {
            ParseQuery<ParseObject> parseQuery = new ParseQuery("Reminder");
            parseQuery.whereEqualTo("user", ParseUser.getCurrentUser());
            SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.DELETE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            getAllContent(listViewToday, items);
                        }
                    });
                }
            });
        }
    }

    public void objectCreated(ParseLiveQueryClient parseLiveQueryClient, final ListView listViewToday, final ArrayList items) {
        if (parseLiveQueryClient != null) {
            ParseQuery<ParseObject> parseQuery = new ParseQuery("Reminder");
            parseQuery.whereEqualTo("user", ParseUser.getCurrentUser());
            SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, final ParseObject object) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            getAllContent(listViewToday, items);
                        }
                    });
                }
            });
        }
    }
}