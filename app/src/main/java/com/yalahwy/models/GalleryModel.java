package com.yalahwy.models;

import java.io.Serializable;

public class GalleryModel implements Serializable {
    private int id;
    private String product_id;
    private String photo;

    public GalleryModel(int id, String product_id, String photo) {
        this.id = id;
        this.product_id = product_id;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPhoto() {
        return photo;
    }
}
