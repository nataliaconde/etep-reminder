package com.etep.reminder;

public class CustomItem {

    private String spinnerItemName;
    private int spinnerIntImage;

    public CustomItem(String spinnerText, int spinnerImage) {
        this.spinnerItemName = spinnerText;
        this.spinnerIntImage = spinnerImage;
    }

    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public int getSpinnerIntImage() {
        return spinnerIntImage;
    }
}
