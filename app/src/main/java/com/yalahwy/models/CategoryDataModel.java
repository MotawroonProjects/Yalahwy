package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class CategoryDataModel implements Serializable {
    private List<CategoryModel> data;
    private int status;

    public List<CategoryModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
