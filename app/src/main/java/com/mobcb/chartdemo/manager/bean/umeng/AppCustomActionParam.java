package com.mobcb.chartdemo.manager.bean.umeng;


import java.io.Serializable;
import java.util.List;

public class AppCustomActionParam implements Serializable {
    private int type;
    private Object param;
    private List<AppKeyValue> extra;
    public AppCustomActionParam(int type, Object param) {
        this.type = type;
        this.param = param;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public List<AppKeyValue> getExtra() {
        return extra;
    }

    public void setExtra(List<AppKeyValue> extra) {
        this.extra = extra;
    }
}
