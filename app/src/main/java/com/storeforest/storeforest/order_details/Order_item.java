package com.storeforest.storeforest.order_details;

import androidx.fragment.app.Fragment;

public class Order_item extends Fragment {
    String b_id;
    String booking_status;
    String booking_amount;
    String payment_mode;
    String transaction_id;
    String item_name;
    String item_image;
    String wait_details;
    String attribute;
    String b_item_id;
    String quantity;
    public Order_item(String b_id, String booking_status, String booking_amount,String payment_mode, String transaction_id, String item_name, String item_image,String wait_details, String attribute,String b_item_id,String quantity) {
        this.b_id=b_id;
        this.booking_status=booking_status;
        this.booking_amount=booking_amount;
        this.payment_mode=payment_mode;
        this.transaction_id=transaction_id;
        this.item_name=item_name;
        this.item_image=item_image;
        this.wait_details=wait_details;
        this.attribute=attribute;
        this.b_item_id=b_item_id;
        this.quantity=quantity;

    }

    public String getB_id() {
        return b_id;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public String getBooking_amount() {
        return booking_amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_image() {
        return item_image;
    }

    public String getWait_details() {
        return wait_details;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getB_item_id() {
        return b_item_id;
    }

    public String getQuantity() {
        return quantity;
    }
}