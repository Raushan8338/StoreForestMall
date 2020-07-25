package com.storeforest.storeforest.Shop_item_category;

public class Shop_details_item_category {
    String user_id;
    String c_image;
    String c_name;
    String c_value;

    public Shop_details_item_category(String  user_id,String c_image,String c_name,String c_value) {
        this.user_id=user_id;
        this.c_image=c_image;
        this.c_name=c_name;
        this.c_value=c_value;


    }

    public String getUser_id() {
        return user_id;
    }

    public String getC_image() {
        return c_image;
    }

    public String getC_name() {
        return c_name;
    }

    public String getC_value() {
        return c_value;
    }
}
