package com.example.betancourt.nutriapp.pojo;

public class Register extends ObjectId {

    String activity, brand, date, food_id, food_name, food_type, hour, img_name, ingredient, place, quantity, who;

    public Register() {
    }

    public Register(String activity, String brand, String date, String food_id, String food_name, String food_type, String hour, String img_name, String ingredient, String place, String quantity, String who) {
        this.activity = activity;
        this.brand = brand;
        this.date = date;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_type = food_type;
        this.hour = hour;
        this.img_name = img_name;
        this.ingredient = ingredient;
        this.place = place;
        this.quantity = quantity;
        this.who = who;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
