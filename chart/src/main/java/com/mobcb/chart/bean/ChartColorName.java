package com.mobcb.chart.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

import java.io.Serializable;

/**
 * Created by Sanguine on 2018/4/19.
 */

public class ChartColorName implements Parcelable, Serializable {
    public static final Parcelable.Creator<ChartColorName> CREATOR = new Parcelable.Creator<ChartColorName>() {
        @Override
        public ChartColorName createFromParcel(Parcel source) {
            return new ChartColorName(source);
        }

        @Override
        public ChartColorName[] newArray(int size) {
            return new ChartColorName[size];
        }
    };
    private @ColorInt
    int color;
    private String name;

    public ChartColorName() {
    }

    protected ChartColorName(Parcel in) {
        this.color = in.readInt();
        this.name = in.readString();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.color);
        dest.writeString(this.name);
    }
}
