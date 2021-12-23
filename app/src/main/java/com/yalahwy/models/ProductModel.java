package com.yalahwy.models;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private int id;
    private String sku;
    private String product_type;
    private String affiliate_link;
    private String user_id;
    private String category_id;
    private String subcategory_id;
    private String childcategory_id;
    private String attributes;
    private String name="";
    private String name_EN="";
    private String slug;
    private String photo;
    private String thumbnail;
    private String file;
    private String price;
    private String details;
    private String previous_price;
    private String stock;
    private String policy;
    private String status;
    private String views;
    private String features;
    private String product_condition;
    private String ship;
    private String is_meta;
    private String meta_tag;
    private String meta_description;
    private String youtube;
    private String type;
    private String license;
    private String license_qty;
    private String link;
    private String platform;
    private String region;
    private String licence_type;
    private String measure;
    private String featured;
    private String best;
    private String top;
    private String hot;
    private String latest;
    private String big;
    private String trending;
    private String sale;
    private String created_at;
    private String updated_at;
    private String is_discount;
    private String discount_date;
    private String whole_sell_qty;
    private String whole_sell_discount;
    private String is_catalog;
    private String catalog_id;
    private String details_clear;
    private String policy_clear;

    private IsWishList is_wishlist;
    private String product_rate;
    private List<GalleryModel> galleries;
    private List<CommentModel> comments;

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getAffiliate_link() {
        return affiliate_link;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public String getChildcategory_id() {
        return childcategory_id;
    }

    public String getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getPhoto() {
        return photo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getFile() {
        return file;
    }






    public String getPrice() {
        return price;
    }

    public String getPrevious_price() {
        return previous_price;
    }

    public String getStock() {
        return stock;
    }

    public String getPolicy() {
        return policy;
    }

    public String getStatus() {
        return status;
    }

    public String getViews() {
        return views;
    }

    public String getFeatures() {
        return features;
    }



    public String getProduct_condition() {
        return product_condition;
    }

    public String getShip() {
        return ship;
    }

    public String getIs_meta() {
        return is_meta;
    }

    public String getMeta_tag() {
        return meta_tag;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getType() {
        return type;
    }

    public String getLicense() {
        return license;
    }

    public String getLicense_qty() {
        return license_qty;
    }

    public String getLink() {
        return link;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRegion() {
        return region;
    }

    public String getLicence_type() {
        return licence_type;
    }

    public String getMeasure() {
        return measure;
    }

    public String getFeatured() {
        return featured;
    }

    public String getBest() {
        return best;
    }

    public String getTop() {
        return top;
    }

    public String getHot() {
        return hot;
    }

    public String getLatest() {
        return latest;
    }

    public String getBig() {
        return big;
    }

    public String getTrending() {
        return trending;
    }

    public String getSale() {
        return sale;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getIs_discount() {
        return is_discount;
    }

    public String getDiscount_date() {
        return discount_date;
    }

    public String getWhole_sell_qty() {
        return whole_sell_qty;
    }

    public String getWhole_sell_discount() {
        return whole_sell_discount;
    }

    public String getIs_catalog() {
        return is_catalog;
    }

    public String getCatalog_id() {
        return catalog_id;
    }

    public String getDetails_clear() {
        return details_clear;
    }

    public String getDetails() {
        return details;
    }

    public String getPolicy_clear() {
        return policy_clear;
    }

    public IsWishList getIs_wishlist() {
        return is_wishlist;
    }

    public String getProduct_rate() {
        return product_rate;
    }

    public String getName_EN() {
        return name_EN;
    }

    public List<GalleryModel> getGalleries() {
        return galleries;
    }


    public List<CommentModel> getComments() {
        return comments;
    }

    public void setIs_wishlist(IsWishList is_wishlist) {
        this.is_wishlist = is_wishlist;
    }

    public static class IsWishList implements Serializable{
        private int id;
        private String user_id;
        private String product_id;


        public int getId() {
            return id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getProduct_id() {
            return product_id;
        }
    }
}
