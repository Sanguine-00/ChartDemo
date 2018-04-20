package com.mobcb.chart.bean;

/**
 * Created by Sanguine on 2018/4/16.
 */

public class ChartEventBusBean {
    public static final String KEY_EVENT_BUS_TIME_SELECT = "key.event.bus.time.select";
    public static final String KEY_EVENT_BUS_SET_TIME_FORMAT = "key.event.bus.set.time.format";
    public static final String KEY_EVENT_BUS_HIDE_TIME_SELECT = "key.event.bus.hide.time.select";
    private String action;
    private Object object;

    public ChartEventBusBean(String action, Object object) {
        this.action = action;
        this.object = object;
    }

    public ChartEventBusBean(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
