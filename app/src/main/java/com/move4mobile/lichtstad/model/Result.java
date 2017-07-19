package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Result implements Parcelable {

    private String imageUrl;

    private String title;

    private String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getImageUrl());
        dest.writeString(this.getTitle());
        dest.writeString(this.getUrl());
    }

    public Result() {
    }

    protected Result(Parcel in) {
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
