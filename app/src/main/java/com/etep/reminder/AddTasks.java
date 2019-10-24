package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bolts.Bolts;

public class AddTasks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        if(bundle != null && bundle.size() >= 1) updateScreen(bundle);

        selectArray(bundle);

        final TextView txtDate = (TextView) findViewById(R.id.txtDatePicker);
        final TextView timePickerTxt = (TextView) findViewById(R.id.txtTimePicker);

        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtDescription = (EditText) findViewById(R.id.edtDescription);

        Spinner priority = (Spinner) findViewById(R.id.spinnerPriority);
        ImageButton ImageButton = (ImageButton) findViewById(R.id.btnDatePicker);
        ImageButton btnTimePicker = (ImageButton) findViewById(R.id.btnTimePicker);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            goToActivity(LogInScreen.class);
        }

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle != null && bundle.size() >= 1){
                    String date = bundle.getString("date");
                    String year = date.substring(0,4);
                    String month = date.substring(5,7);
                    String day = date.substring(8,10);
                    datePickerUpdate(txtDate, day, month, year);
                } else {
                    datePicker(txtDate);
                }

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle != null && bundle.size() >= 1) {
                    String time = bundle.getString("time");
                    timePickerUpdate(timePickerTxt, time);
                } else {
                    timePicker(timePickerTxt);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.save_task_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        final TextView txtDate = (TextView) findViewById(R.id.txtDatePicker);
        final TextView timePickerTxt = (TextView) findViewById(R.id.txtTimePicker);
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final TextView objectId = (TextView) findViewById(R.id.objectId);
        final EditText edtDescription = (EditText) findViewById(R.id.edtDescription);
        Spinner spinner = (Spinner)findViewById(R.id.spinnerPriority);
        String[] optionsPriority = {"low", "medium", "high"};

        if(item.getItemId()==R.id.createNewTask){
            if(checkIfExists(edtName, edtDescription, txtDate, timePickerTxt)) {
                String edtNameField = edtName.getText().toString();
                String txtObjectId = objectId.getText().toString();
                String edtDescriptionField = edtDescription.getText().toString();
                String datePickerTxtField = txtDate.getText().toString();
                String timePickerTxtField = timePickerTxt.getText().toString();
                Integer position = spinner.getSelectedItemPosition();

                final Map<String, String> params = new HashMap<>();
                params.put("title", edtNameField);
                params.put("description", edtDescriptionField);
                params.put("userId", ParseUser.getCurrentUser().getObjectId());
                params.put("priority", optionsPriority[position]);
                params.put("date", datePickerTxtField);
                params.put("time", timePickerTxtField);

                if(!txtObjectId.isEmpty()){
                    params.put("objectId",  txtObjectId);
                    ParseCloud.callFunctionInBackground("updateTask", params,
                            new FunctionCallback<String>() {
                                @Override
                                public void done(String object, com.parse.ParseException e) {
                                    if (e == null) {
                                        // Everything is alright
                                        Toast.makeText(AddTasks.this, getString(R.string.taskObjectUpdated), Toast.LENGTH_SHORT).show();
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        goToActivity(ListTasksScreen.class);
                                                    }
                                                }, Toast.LENGTH_SHORT);

                                    } else {
                                        // Something went wrong
                                        Toast.makeText(AddTasks.this, getString(R.string.taskObjectNotCreated), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    ParseCloud.callFunctionInBackground("saveNewTask", params,
                            new FunctionCallback<String>() {
                                @Override
                                public void done(String object, com.parse.ParseException e) {
                                    if (e == null) {
                                        // Everything is alright
                                        Toast.makeText(AddTasks.this, getString(R.string.taskObjectCreated), Toast.LENGTH_SHORT).show();
                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        goToActivity(ListTasksScreen.class);
                                                    }
                                                }, Toast.LENGTH_SHORT);

                                    } else {
                                        // Something went wrong
                                        Toast.makeText(AddTasks.this, getString(R.string.taskObjectNotCreated), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }




            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void updateScreen(Bundle bundle){
        final TextView txtObjectId = (TextView) findViewById(R.id.objectId);
        final TextView txtDate = (TextView) findViewById(R.id.txtDatePicker);
        final TextView timePickerTxt = (TextView) findViewById(R.id.txtTimePicker);
        final EditText edtName = (EditText) findViewById(R.id.edtName);
        final EditText edtDescription = (EditText) findViewById(R.id.edtDescription);

        edtName.setText(bundle.getString("title"));
        edtDescription.setText(bundle.getString("description"));
        txtDate.setText(bundle.getString("date"));
        timePickerTxt.setText(bundle.getString("time"));
        txtObjectId.setText(bundle.getString("objectId"));

    }

    private boolean checkIfExists(EditText edtName, EditText edtDescription, TextView txtDate, TextView timePickerTxt) {
        if(edtName.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.addTaskName), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edtDescription.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.addTaskDescription), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtDate.getText().toString().equals(""))

        if(timePickerTxt.getText().toString().equals("")){
            Toast.makeText(this, getString(R.string.addTaskTime), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void goToActivity(Class<?> view) {
        Intent i = new Intent(AddTasks.this, view);
        startActivity(i);
    }
    public void datePickerUpdate(final TextView txtDate, String dayD, String monthD, final String yearD) {
        int year = Integer.valueOf(yearD);
        int month = Integer.valueOf(monthD);
        int day = Integer.valueOf(dayD);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTasks.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        txtDate.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }
    public void datePicker(final TextView txtDate){
        Calendar calendar = Calendar.getInstance();
        int year = Integer.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTasks.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        txtDate.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void timePickerUpdate(final TextView timePickerTxt, String time){

        String[] data = (time).split(":");
        int hour = Integer.valueOf (data[0]);
        int minute = Integer.valueOf (data[1]);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTasks.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timePickerTxt.setText( hour + ":" + minute + ":000");
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Selecione o horário");
        mTimePicker.show();
    }

    public void timePicker(final TextView timePickerTxt){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTasks.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timePickerTxt.setText( selectedHour + ":" + selectedMinute + ":000");
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Selecione o horário");
        mTimePicker.show();
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void selectArray(Bundle bundle){
        final Spinner priority = (Spinner) findViewById(R.id.spinnerPriority);
        ArrayList<CustomItem> customList = new ArrayList<>();
        customList.add(new CustomItem("Baixa", R.drawable.prioritylow));
        customList.add(new CustomItem("Média", R.drawable.prioritymedium));
        customList.add(new CustomItem("Alta", R.drawable.priorityhigh));

        CustomAdapterSpinner adapter  = new CustomAdapterSpinner(this, customList);
        if(bundle != null && bundle.size() >= 1){
            ArrayList<String> optionsPriority = new ArrayList<String>(3);
            optionsPriority.add("low");
            optionsPriority.add("medium");
            optionsPriority.add("high");

            int selectPriority = optionsPriority.indexOf(bundle.getString("priority"));

            priority.setAdapter(adapter);
            priority.setSelection(selectPriority);
            priority.setOnItemSelectedListener(this);
            Log.d("spinner", "aqui 1");
        } else {
            priority.setAdapter(adapter);
            priority.setOnItemSelectedListener(this);
            Log.d("spinner", "aqui 2");
        }
    }
}
