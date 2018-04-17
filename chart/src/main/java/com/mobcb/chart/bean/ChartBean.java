package com.mobcb.chart.bean;

import java.util.List;

/**
 * Created by Sanguine on 2018/4/3.
 */

public class ChartBean {

    private List<ChartDetailBean> chartList;
    /**
     * displayType : overlay
     * title : 会员等级与数量、积分关系图
     */

    private String displayType;
    private String title;

    public List<ChartDetailBean> getChartList() {
        return chartList;
    }

    public void setChartList(List<ChartDetailBean> chartList) {
        this.chartList = chartList;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
