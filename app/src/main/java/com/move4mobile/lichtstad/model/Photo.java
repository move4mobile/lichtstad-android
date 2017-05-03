package com.move4mobile.lichtstad.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Photo {

    @PropertyName("thumbnail")
    public String thumbnailUrl;

    @PropertyName("image")
    public String imageUrl;

    public Size size;

}
