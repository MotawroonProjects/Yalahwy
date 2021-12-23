package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel2 implements Serializable {
    private int status;
    private Data data;

    public Data getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class Data implements Serializable{
        private List<ProductModel> products;
        public List<ProductModel> getData() {
            return products;
        }
    }





}
