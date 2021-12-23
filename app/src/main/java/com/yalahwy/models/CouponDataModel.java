package com.yalahwy.models;

import java.io.Serializable;

public class CouponDataModel implements Serializable {
    private CouponModel data;
    private int status;
    private String message;

    public static class CouponModel implements Serializable{
        private int id;
        private String code;
        private String price;
        private String status;
        private int type;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getPrice() {
            return price;
        }

        public String getStatus() {
            return status;
        }

        public int getType() {
            return type;
        }
    }

    public CouponModel getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
