package com.mobcb.chart.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;

import com.mobcb.chart.ChartConstants;

import java.io.Serializable;

/**
 * Created by Sanguine on 2018/4/19.
 */

public class DataProperty implements Parcelable, Serializable {
    public static final Creator<DataProperty> CREATOR = new Creator<DataProperty>() {
        @Override
        public DataProperty createFromParcel(Parcel source) {
            return new DataProperty(source);
        }

        @Override
        public DataProperty[] newArray(int size) {
            return new DataProperty[size];
        }
    };
    private @ColorInt
    int color;
    private String name;
    private @ChartConstants
    String chartType;

    public DataProperty() {
    }

    protected DataProperty(Parcel in) {
        this.color = in.readInt();
        this.name = in.readString();
        this.chartType = in.readString();
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
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
        dest.writeString(this.chartType);
    }
}
