package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.HashMap;
import java.util.Map;

public class MarkerVideo implements Parcelable {

    @Json(name = "id")
    private String id;

    @Json(name = "thumbnails")
    private Map<String, String> thumbnails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Map<String, String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.thumbnails.size());
        for (Map.Entry<String, String> entry : this.thumbnails.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public MarkerVideo() {
    }

    protected MarkerVideo(Parcel in) {
        this.id = in.readString();
        int thumbnailsSize = in.readInt();
        this.thumbnails = new HashMap<String, String>(thumbnailsSize);
        for (int i = 0; i < thumbnailsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.thumbnails.put(key, value);
        }
    }

    public static final Parcelable.Creator<MarkerVideo> CREATOR = new Parcelable.Creator<MarkerVideo>() {
        @Override
        public MarkerVideo createFromParcel(Parcel source) {
            return new MarkerVideo(source);
        }

        @Override
        public MarkerVideo[] newArray(int size) {
            return new MarkerVideo[size];
        }
    };
}
