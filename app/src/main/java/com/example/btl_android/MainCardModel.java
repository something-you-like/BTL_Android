package com.example.btl_android;

public class MainCardModel {
    int icon;
    String function;


    public MainCardModel(int icon, String function) {
        this.icon = icon;
        this.function = function;
    }

    public int getIcons() {
        return icon;
    }

    public String getFunctions() {
        return function;
    }
}
