package com.move4mobile.lichtstad.model;

import com.google.firebase.database.PropertyName;

public class Location {

    private String name;

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }
}
