package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Photo implements Parcelable {

    @Exclude
    public String key;

    @PropertyName("thumbnail")
    public String thumbnailUrl;

    @PropertyName("image")
    public String imageUrl;

    public Size size;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.size, flags);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.key = in.readString();
        this.thumbnailUrl = in.readString();
        this.imageUrl = in.readString();
        this.size = in.readParcelable(Size.class.getClassLoader());
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
}
