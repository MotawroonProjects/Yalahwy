package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class SubCategoryDataModel implements Serializable {
    private List<SubCategoryModel> data;
    private int status;
    private String message;

    public List<SubCategoryModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
