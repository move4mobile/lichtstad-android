package com.move4mobile.lichtstad.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Map;

@IgnoreExtraProperties
public class Video {

    private String id;
    private long date;
    private Map<String, String> thumbnails;
    private String title;

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("date")
    public long getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(long date) {
        this.date = date;
    }

    @PropertyName("thumbnails")
    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    @PropertyName("thumbnails")
    public void setThumbnails(Map<String, String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }
}
