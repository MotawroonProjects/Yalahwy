package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {
    private List<ProductModel> data;
    private int status;

    public int getStatus() {
        return status;
    }

    public List<ProductModel> getData() {
        return data;
    }

}
