package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class SendCartModel implements Serializable {
    private String user_id;
    private String total;
    private String customer_address;
    private String shipping_address;
    private String shipping_cost;
    private String packing_cost;
    private String phone;
    private String coupon_code;
    private int coupon_id;
    private String coupon_discount;
    private String net_cost;
    private List<Cart> cart;

    public SendCartModel(String user_id, String total, String customer_address, String shipping_address, String shipping_cost, String packing_cost, String phone, String coupon_code,int coupon_id, String coupon_discount,String net_cost, List<Cart> cart) {
        this.user_id = user_id;
        this.total = total;
        this.customer_address = customer_address;
        this.shipping_address = shipping_address;
        this.shipping_cost = shipping_cost;
        this.packing_cost = packing_cost;
        this.phone = phone;
        this.coupon_code = coupon_code;
        this.coupon_id=coupon_id;
        this.coupon_discount = coupon_discount;
        this.net_cost = net_cost;
        this.cart = cart;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTotal() {
        return total;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public String getPacking_cost() {
        return packing_cost;
    }

    public String getPhone() {
        return phone;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public String getNet_cost() {
        return net_cost;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public static class Cart implements Serializable{
        private String id;

        public Cart(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
