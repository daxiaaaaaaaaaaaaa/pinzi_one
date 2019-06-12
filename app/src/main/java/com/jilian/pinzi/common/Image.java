package com.jilian.pinzi.common;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String path;
    private String name;
    private long dateTime;
    private boolean isCheck;

    protected Image(Parcel in) {
        path = in.readString();
        name = in.readString();
        dateTime = in.readLong();
        isCheck = in.readByte() != 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Image() {
    }

    public Image(String path, String name, long dateTime) {

        this.path = path;
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeLong(dateTime);
        dest.writeByte((byte) (isCheck ? 1 : 0));
    }
}
