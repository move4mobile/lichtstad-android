package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.move4mobile.lichtstad.snapshotparser.Keyed;

@IgnoreExtraProperties
public class Photo implements Keyed, Parcelable {

    @Exclude
    private String key;

    private String thumbnailUrl;

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

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @PropertyName("thumbnail")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @PropertyName("thumbnail")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @PropertyName("image")
    public String getImageUrl() {
        return imageUrl;
    }

    @PropertyName("image")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @PropertyName("size")
    public Size getSize() {
        return size;
    }

    @PropertyName("size")
    public void setSize(Size size) {
        this.size = size;
    }
}
