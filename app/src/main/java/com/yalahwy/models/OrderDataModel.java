package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel implements Serializable {
    private List<OrderModel> data;
    private int status;

    public List<OrderModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
