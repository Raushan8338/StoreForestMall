package com.storeforest.storeforest.addTocart;

public class Display_cart_item {
    String p;
    String b;
    String g;
    String c;
    String f;
   String v;
   String bid;
   String pay;
    String location;
    String atrribute;
    String item_category;
    String wait_details;
    String quantity;
    String total_a;
    public Display_cart_item(String p, String b, String g, String c, String f,String v,String bid,String pay,String location,String atrribute,String  item_category,String wait_details,String quantity,String total_a) {
        this.p=p;
        this.b=b;
        this.g=g;
        this.c=c;
        this.f=f;
        this.v=v;
        this.bid=bid;
        this.pay=pay;
        this.location=location;
        this.atrribute=atrribute;
        this.item_category=item_category;
        this.wait_details=wait_details;
        this.quantity=quantity;
        this.total_a=total_a;
    }

    public String getP() {
        return p;
    }

    public String getB() {
        return b;
    }

    public String getG() {
        return g;
    }

    public String getC() {
        return c;
    }

    public String getF() {
        return f;
    }

    public String getV() {
        return v;
    }

    public String getBid() {
        return bid;
    }

    public String getPay() {
        return pay;
    }

    public String getLocation() {
        return location;
    }

    public String getAtrribute() {

        return atrribute;
    }

    public String getItem_category() {
        return item_category;
    }

    public String getWait_details() {
        return wait_details;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotal_a() {
        return total_a;
    }
}
