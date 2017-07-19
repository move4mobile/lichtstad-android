package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.move4mobile.lichtstad.snapshotparser.Keyed;

import java.util.Date;

@IgnoreExtraProperties
public class Album implements Keyed, Parcelable {

    @Exclude
    private String key;

    @Exclude
    private String year;

    private long photoCount;

    private String thumbnailUrl;

    private Size thumbnailSize;

    private String title;

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

    @PropertyName("photo_count")
    public long getPhotoCount() {
        return photoCount;
    }

    @PropertyName("photo_count")
    public void setPhotoCount(long photoCount) {
        this.photoCount = photoCount;
    }

    @PropertyName("thumbnail")
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @PropertyName("thumbnail")
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @PropertyName("thumbnail_size")
    public Size getThumbnailSize() {
        return thumbnailSize;
    }

    @PropertyName("thumbnail_size")
    public void setThumbnailSize(Size thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("upload_time")
    public long getUploadTime() {
        return uploadTime;
    }

    @PropertyName("upload_time")
    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }
}
