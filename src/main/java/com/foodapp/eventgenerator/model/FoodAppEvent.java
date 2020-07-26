package com.foodapp.eventgenerator.model;

public class FoodAppEvent {

    String Type;
    Data data;

    public FoodAppEvent(String type, Data data) {
        this.Type = type;
        this.data = data;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
