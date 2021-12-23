package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class MenuDataModel implements Serializable {
    private List<MenuModel> data;
    private int status;
    private double total;
    public List<MenuModel> getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }

    public int getStatus() {
        return status;
    }
}
