package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Date;

@IgnoreExtraProperties
public class Album implements Parcelable {

    @Exclude
    private String key;

    @Exclude
    private String year;

    @PropertyName("photo_count")
    private long photoCount;

    @PropertyName("thumbnail")
    private String thumbnailUrl;

    @PropertyName("thumbnail_size")
    private Size thumbnailSize;

    private String title;

    @PropertyName("upload_time")
    private long uploadTime;

    public Date getUploadTimeAsDate() {
        return new Date(getUploadTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getKey());
        dest.writeString(this.getYear());
        dest.writeLong(this.getPhotoCount());
        dest.writeString(this.getThumbnailUrl());
        dest.writeParcelable(this.getThumbnailSize(), flags);
        dest.writeString(this.getTitle());
        dest.writeLong(this.getUploadTime());
    }

    public Album() {
    }

    protected Album(Parcel in) {
        this.setKey(in.readString());
        this.setYear(in.readString());
        this.setPhotoCount(in.readLong());
        this.setThumbnailUrl(in.readString());
        this.setThumbnailSize(in.<Size>readParcelable(Size.class.getClassLoader()));
        this.setTitle(in.readString());
        this.setUploadTime(in.readLong());
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(long photoCount) {
        this.photoCount = photoCount;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Size getThumbnailSize() {
        return thumbnailSize;
    }

    public void setThumbnailSize(Size thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
