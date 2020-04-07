package com.project.designeng.e_healthcare.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.designeng.e_healthcare.BMICalculation;
import com.project.designeng.e_healthcare.ForPersonActivity;
import com.project.designeng.e_healthcare.MedicineReminderActivity;
import com.project.designeng.e_healthcare.R;
import com.project.designeng.e_healthcare.modelclasses.PatientModel;

import java.util.ArrayList;

public class PatientAdapter extends BaseAdapter {
    private Context context;

    public PatientAdapter(Context context, ArrayList<PatientModel> patientData) {
        this.context = context;
        this.patientData = patientData;
    }

    private ArrayList<PatientModel> patientData;
    @Override
    public int getCount() {
        return patientData.size();
    }

    @Override
    public Object getItem(int i) {
        return patientData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view1, ViewGroup viewGroup) {
        @SuppressLint({"ViewHolder", "InflateParams"}) View view = LayoutInflater.from(context).inflate(R.layout.patient_list_item,null);

            TextView textView = view.findViewById(R.id.list_txt);
            ImageView imageView = view.findViewById(R.id.list_img);

            PatientModel modelData = patientData.get(i);
            textView.setText(modelData.getItemName());
            imageView.setBackgroundResource(modelData.getImgResId());

            if(modelData.getItemName().equals("Go to normal person")){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, ForPersonActivity.class));
                    }
                });
            }
            if(modelData.getItemName().equals("BMI Calculator")){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, BMICalculation.class));
                    }
                });
            }
            if(modelData.getItemName().equals("Medicine Reminder")){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, MedicineReminderActivity.class));
                    }
                });
            }
        return view;
    }
}
