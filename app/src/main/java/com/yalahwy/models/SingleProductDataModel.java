package com.yalahwy.models;

import java.io.Serializable;

public class SingleProductDataModel implements Serializable {
    private ProductModel data;
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    public ProductModel getData() {
        return data;
    }
}
