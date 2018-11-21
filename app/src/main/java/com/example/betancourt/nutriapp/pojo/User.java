package com.example.betancourt.nutriapp.pojo;

public class User extends ObjectId{

    String name, doctor;

    public User() {
    }

    public User(String name, String doctor) {
        this.name = name;
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
