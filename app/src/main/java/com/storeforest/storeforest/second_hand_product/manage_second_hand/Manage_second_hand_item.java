package com.storeforest.storeforest.second_hand_product.manage_second_hand;

public class Manage_second_hand_item {
    String id;
    String user_id;
    String brand;
    String title;
    String address;
    String describe;
    String contact_no;
    String name;
    String p_image;
    String image;
    String status;
    String price;
    public Manage_second_hand_item(String id, String user_id, String brand, String title, String address, String describe, String contact_no, String name, String p_image, String image,String status,String price) {
        this.id=id;
        this.user_id=user_id;
        this.brand=brand;
        this.title=title;
        this.address=address;
        this.describe=describe;
        this.contact_no=contact_no;
        this.name=name;
        this.p_image=p_image;
        this.image=image;
        this.status=status;
        this.price=price;

    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getDescribe() {
        return describe;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getName() {
        return name;
    }

    public String getP_image() {
        return p_image;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }
}
