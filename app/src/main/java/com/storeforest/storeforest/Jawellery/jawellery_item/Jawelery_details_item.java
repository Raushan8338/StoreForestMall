package com.storeforest.storeforest.Jawellery.jawellery_item;

public class Jawelery_details_item {
    String name;
    String id;
    String image;
    String price;
    String status;
    String dis;
    String user_ids;
    String offer_price;
    String 	attribute;
    String wait_details;
    // String book;
    public Jawelery_details_item(String name, String id, String image, String price, String status, String dis, String user_ids, String offer_price, String attribute, String wait_details) {
        this.name=name;
        this.id=id;
        this.image=image;
        this.price=price;
        this.status=status;
        this.dis=dis;
        this.user_ids=user_ids;
        this.offer_price=offer_price;
        this.attribute=	attribute;
        this.wait_details=wait_details;
        // this.book=book;

    }

    public String getFront_img() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getDis() {
        return dis;
    }


    public String getUser_ids() {
        return user_ids;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getWait_details() {
        return wait_details;
    }
}
