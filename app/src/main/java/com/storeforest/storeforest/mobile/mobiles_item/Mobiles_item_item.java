package com.storeforest.storeforest.mobile.mobiles_item;

public class Mobiles_item_item {
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
    String description;
    String return_policy;
    // String book;
    public Mobiles_item_item(String name, String id, String image, String price, String status, String dis, String user_ids, String offer_price, String attribute, String wait_details,String description,String return_policy) {
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
        this.description=description;
        this.return_policy=return_policy;
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

    public String getDescription() {
        return description;
    }

    public String getReturn_policy() {
        return return_policy;
    }
}
