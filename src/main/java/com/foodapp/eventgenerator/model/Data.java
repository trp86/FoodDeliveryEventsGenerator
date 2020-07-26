package com.foodapp.eventgenerator.model;

public class Data {

    String OrderId;
    String TimestampUtc;

    public Data(String orderId, String timestampUtc) {
        this.OrderId = orderId;
        this.TimestampUtc = timestampUtc;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTimestampUtc() {
        return TimestampUtc;
    }

    public void setTimestampUtc(String timestampUtc) {
        TimestampUtc = timestampUtc;
    }



}
