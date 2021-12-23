package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class BottomImageDataModel implements Serializable {
    private List<GalleryModel> data;
    private int status;

    public int getStatus() {
        return status;
    }

    public List<GalleryModel> getData() {
        return data;
    }

}
