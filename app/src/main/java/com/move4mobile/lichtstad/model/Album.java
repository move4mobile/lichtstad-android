package com.move4mobile.lichtstad.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Date;

@IgnoreExtraProperties
public class Album {

    @PropertyName("photo_count")
    public long photoCount;

    @PropertyName("thumbnail")
    public String thumbnailUrl;

    @PropertyName("thumbnail_size")
    public Size thumbnailSize;

    public String title;

    @PropertyName("upload_time")
    public long uploadTime;

    public Date getUploadTimeAsDate() {
        return new Date(uploadTime);
    }

}
