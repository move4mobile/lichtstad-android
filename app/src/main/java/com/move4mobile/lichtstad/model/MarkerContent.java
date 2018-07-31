package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class MarkerContent implements Parcelable {

    @Json(name = "title")
    private String title;

    @Json(name = "text")
    private String description;

    @Json(name = "images")
    private List<String> imageUrls;

    @Json(name = "video")
    private MarkerVideo video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public MarkerVideo getVideo() {
        return video;
    }

    public void setVideo(MarkerVideo video) {
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeStringList(this.imageUrls);
        dest.writeParcelable(this.video, flags);
    }

    public MarkerContent() {
    }

    protected MarkerContent(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.imageUrls = in.createStringArrayList();
        this.video = in.readParcelable(MarkerVideo.class.getClassLoader());
    }

    public static final Creator<MarkerContent> CREATOR = new Creator<MarkerContent>() {
        @Override
        public MarkerContent createFromParcel(Parcel source) {
            return new MarkerContent(source);
        }

        @Override
        public MarkerContent[] newArray(int size) {
            return new MarkerContent[size];
        }
    };
}
