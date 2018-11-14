package com.example.betancourt.nutriapp.pojo;

public class ObjectId {

    public String objectId;

    public <T extends ObjectId> T withId(final String id){
        this.objectId= id;
        return (T) this;
    }
}
