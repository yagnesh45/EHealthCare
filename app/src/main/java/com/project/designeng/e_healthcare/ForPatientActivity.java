package com.project.designeng.e_healthcare;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
/*import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;*/
import android.widget.ListView;

import com.project.designeng.e_healthcare.adapters.PatientAdapter;
import com.project.designeng.e_healthcare.modelclasses.PatientModel;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ForPatientActivity extends AppCompatActivity {

    ListView patientListView;
    ArrayList<PatientModel> patientListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_patient);

        // to create a notification
        createNotificationChannel();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        patientListView = findViewById(R.id.list_patient);
        patientListData = new ArrayList<PatientModel>();
        prepareData();
        PatientAdapter patientAdapter = new PatientAdapter(this, patientListData);
        patientListView.setAdapter(patientAdapter);
    }

    void prepareData() {
        PatientModel object1 = new PatientModel(R.drawable.round_face_black_48, "Medicine Info");
        patientListData.add(object1);

        object1 = new PatientModel(R.drawable.round_face_black_48, "Medicine Reminder");
        patientListData.add(object1);

        object1 = new PatientModel(R.drawable.round_face_black_48, "BMI Calculator");
        patientListData.add(object1);

        object1 = new PatientModel(R.drawable.round_face_black_48, "Go to normal person");
        patientListData.add(object1);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Timer Notification";
            String description = "Notification is used for reminding user abut expired timer";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("TimerNotification", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
