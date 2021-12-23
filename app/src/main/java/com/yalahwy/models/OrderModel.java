package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private int id;
    private String user_id;
    private String method;
    private String pay_amount;
    private String order_number;
    private String payment_status;
    private String customer_phone;
    private String customer_address;
    private String shipping_cost;
    private String packing_cost;
    private String status;
    private String coupon_discount;
    private String net_cost;
    private List<CartProductModel> new_cart;

    public int getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMethod() {
        return method;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public String getPacking_cost() {
        return packing_cost;
    }

    public List<CartProductModel> getNew_cart() {
        return new_cart;
    }

    public String getStatus() {
        return status;
    }

    public String getNet_cost() {
        return net_cost;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public static class CartProductModel implements Serializable{
        private int id;
        private String name;
        private String photo;
        private String price;
        private String product_counts;


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getPrice() {
            return price;
        }

        public String getProduct_counts() {
            return product_counts;
        }
    }

}
