package com.example.betancourt.nutriapp.pojo;

public class Food extends ObjectId{

    String name, img_name, quantity;

    public Food() {
    }

    public Food(String name, String img_name, String quantity) {
        this.name = name;
        this.img_name = img_name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
