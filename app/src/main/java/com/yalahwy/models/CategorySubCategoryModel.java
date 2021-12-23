package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class CategorySubCategoryModel implements Serializable {

    private int id;
    private String name_en;
    private Object name;
    private String slug;
    private int status;
    private String photo;
    private int is_featured;
    private String image;
    private String nameEN;
    private List<SubCategoryModel> subs;

    public int getId() {
        return id;
    }

    public String getName_en() {
        return name_en;
    }

    public Object getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public int getStatus() {
        return status;
    }

    public String getPhoto() {
        return photo;
    }

    public int getIs_featured() {
        return is_featured;
    }

    public String getImage() {
        return image;
    }

    public String getNameEN() {
        return nameEN;
    }

    public List<SubCategoryModel> getSubs() {
        return subs;
    }
}
