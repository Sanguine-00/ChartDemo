package com.mobcb.chart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Sanguine on 2018/4/3.
 */

public class ChartDetailBean implements Parcelable {


    public static final Creator<ChartDetailBean> CREATOR = new Creator<ChartDetailBean>() {
        @Override
        public ChartDetailBean createFromParcel(Parcel source) {
            return new ChartDetailBean(source);
        }

        @Override
        public ChartDetailBean[] newArray(int size) {
            return new ChartDetailBean[size];
        }
    };
    /**
     * chartDataList : [{"xDesc":"初出茅庐","yValue":10,"xValue":0},{"xDesc":"小试牛刀","yValue":1,"xValue":1}]
     * name : credit
     * chartType : bar
     * title : 会员等级与积分
     */

    private String name;
    //图表类型
    private String chartType;
    //标题
    private String title;
    private List<ChartDataListBean> chartDataList;
    //时间格式化，返回null时表示不支持时间筛选
    private String dateFormat;
    ////子图表ID
    private String childId;

    public ChartDetailBean() {
    }

    protected ChartDetailBean(Parcel in) {
        this.name = in.readString();
        this.chartType = in.readString();
        this.title = in.readString();
        this.chartDataList = in.createTypedArrayList(ChartDataListBean.CREATOR);
        this.dateFormat = in.readString();
        this.childId = in.readString();
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChartDataListBean> getChartDataList() {
        return chartDataList;
    }

    public void setChartDataList(List<ChartDataListBean> chartDataList) {
        this.chartDataList = chartDataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.chartType);
        dest.writeString(this.title);
        dest.writeTypedList(this.chartDataList);
        dest.writeString(this.dateFormat);
        dest.writeString(this.childId);
    }
}
