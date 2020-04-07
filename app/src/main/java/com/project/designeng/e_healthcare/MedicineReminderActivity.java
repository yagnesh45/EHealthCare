package com.project.designeng.e_healthcare;

import android.app.TimePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TimePicker;

public class MedicineReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                /*TimePickerDialog timePickerDialog = new TimePickerDialog(MedicineReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        Snackbar.make(view, "Timer set at"+hourOfDay+":"+minutes, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }, 0, 0, false);
                timePickerDialog.show();*/
                openDialog();
            }
        });
    }

    private void openDialog(){
        TimerDialog.display(getSupportFragmentManager());
    }

}
