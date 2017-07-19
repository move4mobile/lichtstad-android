package com.move4mobile.lichtstad.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.move4mobile.lichtstad.snapshotparser.Keyed;

import java.util.Date;

@IgnoreExtraProperties
public class Program implements Keyed {

    @Exclude
    private String key;
    private String description;
    private Location location;
    private long time;
    private String title;

    @PropertyName("image_url")
    private String imageUrl;

    public Date getTimeAsDate() {
        return new Date(getTime());
    }

    @Override
    public String toString() {
        return "Program{" +
                "description='" + getDescription() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", time=" + getTime() +
                ", title='" + getTitle() + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
