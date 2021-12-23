package com.yalahwy.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChildModel implements Serializable {

    private String status;
    private String slug;
    private String name;
    private String subcategory_id;
    private int id;

    public String getStatus() {
        return status;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public int getId() {
        return id;
    }
}
