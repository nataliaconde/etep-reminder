package com.etep.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTasks extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Spinner priority = (Spinner) findViewById(R.id.spinnerPriority);
        ImageButton ImageButton = (ImageButton) findViewById(R.id.btnDatePicker);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            goToActivity(LogInScreen.class);
        }

        ArrayList<CustomItem> customList = new ArrayList<>();
        customList.add(new CustomItem("Baixa", R.drawable.prioritylow));
        customList.add(new CustomItem("Média", R.drawable.prioritymedium));
        customList.add(new CustomItem("Alta", R.drawable.priorityhigh));

        CustomAdapterSpinner adapter  = new CustomAdapterSpinner(this, customList);

        if(priority != null){
            priority.setAdapter(adapter);
            priority.setOnItemSelectedListener(this);
        }

        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
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
        if(item.getItemId()==R.id.createNewTask){
        }
        return super.onOptionsItemSelected(item);

    }

    public void goToActivity(Class<?> view) {
        Intent i = new Intent(AddTasks.this, view);
        startActivity(i);
    }

    public void datePicker(){
        final TextView txtDate = (TextView) findViewById(R.id.txtDatePicker);
        Calendar calendar = Calendar.getInstance();
        int year = Integer.valueOf(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTasks.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        txtDate.setText(year + "/" + (month + 1) + "/" + day);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
