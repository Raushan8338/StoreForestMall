package com.storeforest.storeforest.booking_details;

import androidx.fragment.app.Fragment;

public class Booking_details_item extends Fragment {
    String user_id;
    String total_item;
    String booking_status;
    String shop_id;
    String booking_amount;
    String delivery_charge;
    String booking_date;
    String shop_image;
    String address;
    String shop_name;
    String item_id;
    public Booking_details_item(String user_id,String total_item,String booking_status,String shop_id,String booking_amount,String delivery_charge,String booking_date,String shop_image,String address,String shop_name,String item_id) {
        this.user_id=user_id;
        this.total_item=total_item;
        this.booking_status=booking_status;
        this.shop_id=shop_id;
        this.booking_amount=booking_amount;
        this.delivery_charge=delivery_charge;
        this.booking_date=booking_date;
        this.shop_image=shop_image;
        this.address=address;
        this.shop_name=shop_name;
        this.item_id=item_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTotal_item() {
        return total_item;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getBooking_amount() {
        return booking_amount;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getShop_image() {
        return shop_image;
    }

    public String getAddress() {
        return address;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getItem_id() {
        return item_id;
    }
}