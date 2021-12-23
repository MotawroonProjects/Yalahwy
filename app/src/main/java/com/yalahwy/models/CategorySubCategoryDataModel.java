package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class CategorySubCategoryDataModel implements Serializable {
    private List<CategorySubCategoryModel> data;
    private int status;

    public List<CategorySubCategoryModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
