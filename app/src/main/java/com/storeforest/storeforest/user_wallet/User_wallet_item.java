package com.storeforest.storeforest.user_wallet;

public class User_wallet_item {
    String s;
    String date_time;
    String deposited;
    String diducted_balance;
    String amou;
    String t_detail;
    String bank_name;
    String acc_no;

    public User_wallet_item(String s, String date_time, String deposited, String diducted_balance, String amount, String t_detail, String bank_name, String acc_no) {
        this.s = s;
        this.date_time = date_time;
        this.deposited = deposited;
        this.diducted_balance = diducted_balance;
        this.amou = amount;
        this.bank_name = bank_name;
        this.t_detail = t_detail;
        this.acc_no = acc_no;
    }

    public String getS() {
        return s;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getDeposited() {
        return deposited;
    }

    public String getDiducted_balance() {
        return diducted_balance;
    }
    public String getAmou() {
        return amou;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getT_detail() {
        return t_detail;
    }

    public String getAcc_no() {
        return acc_no;
    }
}