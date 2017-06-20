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
    public String key;

    @Exclude
    public String year;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.year);
        dest.writeLong(this.photoCount);
        dest.writeString(this.thumbnailUrl);
        dest.writeParcelable(this.thumbnailSize, flags);
        dest.writeString(this.title);
        dest.writeLong(this.uploadTime);
    }

    public Album() {
    }

    protected Album(Parcel in) {
        this.key = in.readString();
        this.year = in.readString();
        this.photoCount = in.readLong();
        this.thumbnailUrl = in.readString();
        this.thumbnailSize = in.readParcelable(Size.class.getClassLoader());
        this.title = in.readString();
        this.uploadTime = in.readLong();
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
}
