package com.storeforest.storeforest.notification;

public class NotificationItem {
    String n;
    String m;
    String dt;
    String dti;
    public NotificationItem(String n, String m, String dt,String dti) {
        this.n=n;
        this.m=m;
        this.dt=dt;
        this.dti=dti;
    }

    public String getM() {
        return m;
    }

    public String getN() {
        return n;
    }

    public String getDt() {
        return dt;
    }

    public String getDti() {
        return dti;
    }
}
