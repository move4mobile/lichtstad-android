package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.move4mobile.lichtstad.snapshotparser.Keyed;

import java.util.Date;

@IgnoreExtraProperties
public class Program implements Keyed, Parcelable {

    @Exclude
    private String key;
    private String description;
    private Location location;
    private long time;
    private String title;

    private String imageUrl;

    public Date getTimeAsDate() {
        return new Date(getTime());
    }

    @Override
    public String toString() {
        return "Program{" +
                "description='" + getDescription() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", time=" + getTime() +
                ", title='" + getTitle() + '\'' +
                '}';
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("location")
    public Location getLocation() {
        return location;
    }

    @PropertyName("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @PropertyName("time")
    public long getTime() {
        return time;
    }

    @PropertyName("time")
    public void setTime(long time) {
        this.time = time;
    }

    @PropertyName("title")
    public String getTitle() {
        return title;
    }

    @PropertyName("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @PropertyName("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    @PropertyName("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.description);
        dest.writeParcelable(this.location, flags);
        dest.writeLong(this.time);
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
    }

    public Program() {
    }

    protected Program(Parcel in) {
        this.key = in.readString();
        this.description = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.time = in.readLong();
        this.title = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<Program> CREATOR = new Parcelable.Creator<Program>() {
        @Override
        public Program createFromParcel(Parcel source) {
            return new Program(source);
        }

        @Override
        public Program[] newArray(int size) {
            return new Program[size];
        }
    };
}
