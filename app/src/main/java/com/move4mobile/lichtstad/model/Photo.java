package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Photo implements Parcelable {

    @Exclude
    private String key;

    @PropertyName("thumbnail")
    private String thumbnailUrl;

    @PropertyName("image")
    private String imageUrl;

    private Size size;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getKey());
        dest.writeString(this.getThumbnailUrl());
        dest.writeString(this.getImageUrl());
        dest.writeParcelable(this.getSize(), flags);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.setKey(in.readString());
        this.setThumbnailUrl(in.readString());
        this.setImageUrl(in.readString());
        this.setSize(in.<Size>readParcelable(Size.class.getClassLoader()));
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
