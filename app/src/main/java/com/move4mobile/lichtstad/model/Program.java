package com.move4mobile.lichtstad.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Date;

@IgnoreExtraProperties
public class Program {

    public String description;
    public String location;
    public long time;
    public String title;

    @PropertyName("image_url")
    public String imageUrl;

    public Date getTimeAsDate() {
        return new Date(time);
    }

    @Override
    public String toString() {
        return "Program{" +
                "description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", time=" + time +
                ", title='" + title + '\'' +
                '}';
    }
}
