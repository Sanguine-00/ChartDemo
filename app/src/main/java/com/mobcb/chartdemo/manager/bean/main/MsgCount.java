package com.mobcb.chartdemo.manager.bean.main;

/**
 * Created by xf on 2016/6/1.
 */
public class MsgCount {
    private String newsType;
    private int totalCount;
    private int unreadCount;

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
