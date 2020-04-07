package com.project.designeng.e_healthcare.modelclasses;

public class PatientModel {
    int imgResId;
    String itemName;

    public int getImgResId() {
        return imgResId;
    }

    public String getItemName() {
        return itemName;
    }

    public PatientModel(int imgResId, String itemName) {

        this.imgResId = imgResId;
        this.itemName = itemName;
    }
}
