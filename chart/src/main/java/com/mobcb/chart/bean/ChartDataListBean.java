package com.mobcb.chart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Sanguine on 2018/4/3.
 */

public class ChartDataListBean implements Serializable, Parcelable {
    /**
     * xDesc : 初出茅庐
     * yValue : 20
     * xValue : 0
     */

    private String xDesc;//X轴名称
    private float yValue;//Y轴数值
    private float xValue;//X轴顺序或排名



    public String getXDesc() {
        return xDesc;
    }

    public void setXDesc(String xDesc) {
        this.xDesc = xDesc;
    }

    public float getYValue() {
        return yValue;
    }

    public void setYValue(float yValue) {
        this.yValue = yValue;
    }

    public float getXValue() {
        return xValue;
    }

    public void setXValue(float xValue) {
        this.xValue = xValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.xDesc);
        dest.writeFloat(this.yValue);
        dest.writeFloat(this.xValue);
    }

    public ChartDataListBean() {
    }

    protected ChartDataListBean(Parcel in) {
        this.xDesc = in.readString();
        this.yValue = in.readFloat();
        this.xValue = in.readFloat();
    }

    public static final Creator<ChartDataListBean> CREATOR = new Creator<ChartDataListBean>() {
        @Override
        public ChartDataListBean createFromParcel(Parcel source) {
            return new ChartDataListBean(source);
        }

        @Override
        public ChartDataListBean[] newArray(int size) {
            return new ChartDataListBean[size];
        }
    };
}