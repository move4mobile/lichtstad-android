package com.move4mobile.lichtstad.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class Size implements Parcelable {

    private double width;
    private double height;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.getWidth());
        dest.writeDouble(this.getHeight());
    }

    public Size() {
    }

    protected Size(Parcel in) {
        this.setWidth(in.readDouble());
        this.setHeight(in.readDouble());
    }

    public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel source) {
            return new Size(source);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    @PropertyName("width")
    public double getWidth() {
        return width;
    }

    @PropertyName("width")
    public void setWidth(double width) {
        this.width = width;
    }

    @PropertyName("height")
    public double getHeight() {
        return height;
    }

    @PropertyName("height")
    public void setHeight(double height) {
        this.height = height;
    }
}
