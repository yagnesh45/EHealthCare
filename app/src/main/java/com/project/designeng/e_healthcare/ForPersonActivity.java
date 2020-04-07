package com.project.designeng.e_healthcare;

import android.os.Bundle;
/*import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;*/
import android.view.View;
import android.widget.ListView;

import com.project.designeng.e_healthcare.adapters.PatientAdapter;
import com.project.designeng.e_healthcare.modelclasses.PatientModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ForPersonActivity extends AppCompatActivity {
    ListView personListView;
    ArrayList<PatientModel> patientListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_person);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        personListView = findViewById(R.id.list_person);
        patientListData = new ArrayList<>();

        prepareData();
        PatientAdapter patientAdapter = new PatientAdapter(this, patientListData);
        personListView.setAdapter(patientAdapter);
    }

    private void prepareData() {
        PatientModel object1 = new PatientModel(R.drawable.round_face_black_48,"Fitness Tracking");
        patientListData.add(object1);

        object1 = new PatientModel(R.drawable.round_face_black_48,"Add details");
        patientListData.add(object1);

        object1 = new PatientModel(R.drawable.round_face_black_48,"Reminder & Workout Reminder");
        patientListData.add(object1);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
