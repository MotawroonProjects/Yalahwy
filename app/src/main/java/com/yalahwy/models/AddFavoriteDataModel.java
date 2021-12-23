package com.yalahwy.models;

import java.io.Serializable;

public class AddFavoriteDataModel implements Serializable {
    private ProductModel.IsWishList data;

    public ProductModel.IsWishList getData() {
        return data;
    }
}
