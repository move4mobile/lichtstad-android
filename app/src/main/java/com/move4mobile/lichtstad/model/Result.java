package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.move4mobile.lichtstad.snapshotparser.Keyed;

public class Result implements Keyed, Parcelable {

    @Exclude
    private String key;

    private String imageUrl;

    private String title;

    private String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getKey());
        dest.writeString(this.getImageUrl());
        dest.writeString(this.getTitle());
        dest.writeString(this.getUrl());
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.setKey(in.readString());
        this.setImageUrl(in.readString());
        this.setTitle(in.readString());
        this.setUrl(in.readString());
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
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

    @PropertyName("image")
    public String getImageUrl() {
        return imageUrl;
    }

    @PropertyName("image")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("url")
    public String getUrl() {
        return url;
    }

    @PropertyName("url")
    public void setUrl(String url) {
        this.url = url;
    }
}
