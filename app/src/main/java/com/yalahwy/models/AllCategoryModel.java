package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class AllCategoryModel implements Serializable {

    private List<SingleCategoryModel> data;
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<SingleCategoryModel> getData() {
        return data;
    }


}
