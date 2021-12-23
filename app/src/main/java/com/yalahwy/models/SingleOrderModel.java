package com.yalahwy.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleOrderModel implements Serializable {
    @SerializedName(value = "data", alternate = {"order"})
    private OrderModel data;
    private int status;
    private String message;

    public OrderModel getOrder() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
