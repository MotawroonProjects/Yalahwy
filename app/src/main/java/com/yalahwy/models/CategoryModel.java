package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {
    private int id;
    private String category_id;
    private String name;
    private int status;
    private List<ProductModel> products;

    public int getId() {
        return id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public List<ProductModel> getProducts() {
        return products;
    }
}
