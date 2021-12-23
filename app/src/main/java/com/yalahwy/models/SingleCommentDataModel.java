package com.yalahwy.models;

import java.io.Serializable;

public class SingleCommentDataModel implements Serializable {
    private int status;
    private String message;
    private CommentModel data;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public CommentModel getData() {
        return data;
    }
}
