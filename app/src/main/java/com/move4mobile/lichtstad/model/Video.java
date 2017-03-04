package com.move4mobile.lichtstad.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class Video {

    public String id;
    public long date;
    public Map<String, String> thumbnails;
    public String title;

}
