package com.mobcb.chart.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sanguine on 2018/4/16.
 */

public class ChartIconObject implements Parcelable {
    /**
     * name : 一键上网
     * icon : http://epark.mobcb.com/zimgs/724086b59902494bb77ea338b8709572?f=PNG
     * urlParam : 14
     * urlType : 3
     * clientType : all
     * iconWidth : 164
     * iconHeight : 164
     * scale : 1
     */

    private String name;
    private String icon;
    private String urlParam;
    private String urlType;
    private String clientType;
    private int iconWidth;
    private int iconHeight;
    private int scale;

    protected ChartIconObject(Parcel in) {
        name = in.readString();
        icon = in.readString();
        urlParam = in.readString();
        urlType = in.readString();
        clientType = in.readString();
        iconWidth = in.readInt();
        iconHeight = in.readInt();
        scale = in.readInt();
    }

    public static final Creator<ChartIconObject> CREATOR = new Creator<ChartIconObject>() {
        @Override
        public ChartIconObject createFromParcel(Parcel in) {
            return new ChartIconObject(in);
        }

        @Override
        public ChartIconObject[] newArray(int size) {
            return new ChartIconObject[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(urlParam);
        dest.writeString(urlType);
        dest.writeString(clientType);
        dest.writeInt(iconWidth);
        dest.writeInt(iconHeight);
        dest.writeInt(scale);
    }
}
