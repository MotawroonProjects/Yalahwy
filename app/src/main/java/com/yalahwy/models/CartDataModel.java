package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class CartDataModel implements Serializable {
    private String user_id;
    private double total=0.0;
    private String method;
    private String address;
    private String phone;
    private String coupon_code;
    private int coupon_id;
    private int type;

    private double coupon_discount=0.0;
    private double delivery_cost = 0.0;
    private double packaging_cost =0.0;
    private List<Data> cart;
    private List<CartModel> cartModelList;

    public CartDataModel() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(double coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public List<Data> getCart() {
        return cart;
    }

    public void setCart(List<Data> cart) {
        this.cart = cart;
    }

    public List<CartModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    public double getDelivery_cost() {
        return delivery_cost;
    }

    public void setDelivery_cost(double delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public double getPackaging_cost() {
        return packaging_cost;
    }

    public void setPackaging_cost(double packaging_cost) {
        this.packaging_cost = packaging_cost;
    }

    public static class CartModel implements Serializable{
        private String id;
        private String image;
        private String name;
        private int amount;
        private double cost;

        public CartModel(String id, String image, String name, int amount, double cost) {
            this.id = id;
            this.image = image;
            this.name = name;
            this.amount = amount;
            this.cost = cost;
        }

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public int getAmount() {
            return amount;
        }

        public double getCost() {
            return cost;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }
    }

    public static class Data implements Serializable{
        private String id;

        public Data(String id) {
            this.id = id;
        }

        public String getId() {

            return id;
        }
    }
}
