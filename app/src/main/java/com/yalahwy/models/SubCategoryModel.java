package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class SubCategoryModel implements Serializable {

    private String status;
    private String slug;
    private String photo;
    private String name;
    private String nameEN;
    private String category_id;
    private int id;
    private List<ChildModel> childs;


    public String getStatus() {
        return status;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public int getId() {
        return id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getPhoto() {
        return photo;
    }

    public List<ChildModel> getChilds() {
        return childs;
    }




}
