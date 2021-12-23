package com.yalahwy.models;

import java.io.Serializable;

public class MenuModel implements Serializable {
    private int id;
    private String product_id;
    private String user_id;
    private String status;
    private String amount;
    private ProductModel product;

    public int getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public ProductModel getProduct() {
        return product;
    }


}