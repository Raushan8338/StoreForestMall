package com.storeforest.storeforest.Shop_Category;

public class Shop_category_item {
    String id;
    String category_name;
    String category;
    String image;
    String value;
    public Shop_category_item (String id,String category_name,String category,String image,String value) {
        this.id=id;
       this.category_name=category_name;
       this.category=category;
       this.image=image;
       this.value=value;
    }

    public String getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getValue() {
        return value;
    }
}
