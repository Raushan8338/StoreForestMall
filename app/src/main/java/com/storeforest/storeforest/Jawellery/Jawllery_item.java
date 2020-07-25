package com.storeforest.storeforest.Jawellery;

public class Jawllery_item {
    String id;
    String userid;
    String shop_name;
    String distance;
    String shop_image;
    String status;
    String rating;
    String min;
    String address;
    String delivery_charge;
    // String book;
    public Jawllery_item (String id,String userid,String shop_name,String distance,String shop_image,String status,String rating,String min,String address,String delivery_charge) {
        this.id=id;
        this.userid=userid;
        this.shop_name=shop_name;
        this.distance=distance;
        this.shop_image=shop_image;
        this.status=status;
        this.rating=rating;
        this.min=min;
        this.address=address;
        this.delivery_charge=delivery_charge;


    }

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getDistance() {
        return distance;
    }

    public String getShop_image() {
        return shop_image;
    }

    public String getStatus() {
        return status;
    }

    public String getRating() {
        return rating;
    }

    public String getMin() {
        return min;
    }

    public String getAddress() {
        return address;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }
}
