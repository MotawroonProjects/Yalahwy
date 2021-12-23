package com.yalahwy.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private Data data;
    public String message;
    public int status;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public static class Data implements Serializable {
        private int id;
        private String name;
        private String photo;
        private String zip;
        private String city;
        private String country;
        private String address;
        private String phone;
        private String fax;
        private String email;
        private String created_at;
        private String updated_at;
        private String is_provider;
        private String status;
        private String verification_link;
        private String email_verified;
        private String affilate_code;
        private String affilate_income;
        private String shop_name;
        private String owner_name;
        private String shop_number;
        private String shop_address;
        private String reg_number;
        private String shop_message;
        private String shop_details;
        private String shop_image;
        private String f_url;
        private String g_url;
        private String t_url;
        private String l_url;
        private String is_vendor;
        private String f_check;
        private String g_check;
        private String t_check;
        private String l_check;
        private String mail_sent;
        private String shipping_cost;
        private String current_balance;
        private String date;
        private String ban;
        private String token;
        private String firebase_token;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getZip() {
            return zip;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getFax() {
            return fax;
        }

        public String getEmail() {
            return email;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getIs_provider() {
            return is_provider;
        }

        public String getStatus() {
            return status;
        }

        public String getVerification_link() {
            return verification_link;
        }

        public String getEmail_verified() {
            return email_verified;
        }

        public String getAffilate_code() {
            return affilate_code;
        }

        public String getAffilate_income() {
            return affilate_income;
        }

        public String getShop_name() {
            return shop_name;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public String getShop_number() {
            return shop_number;
        }

        public String getShop_address() {
            return shop_address;
        }

        public String getReg_number() {
            return reg_number;
        }

        public String getShop_message() {
            return shop_message;
        }

        public String getShop_details() {
            return shop_details;
        }

        public String getShop_image() {
            return shop_image;
        }

        public String getF_url() {
            return f_url;
        }

        public String getG_url() {
            return g_url;
        }

        public String getT_url() {
            return t_url;
        }

        public String getL_url() {
            return l_url;
        }

        public String getIs_vendor() {
            return is_vendor;
        }

        public String getF_check() {
            return f_check;
        }

        public String getG_check() {
            return g_check;
        }

        public String getT_check() {
            return t_check;
        }

        public String getL_check() {
            return l_check;
        }

        public String getMail_sent() {
            return mail_sent;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public String getCurrent_balance() {
            return current_balance;
        }

        public String getDate() {
            return date;
        }

        public String getBan() {
            return ban;
        }

        public String getToken() {
            return token;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFireBaseToken(String token) {
            this.firebase_token = token;
        }
    }

}
