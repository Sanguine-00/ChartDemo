package com.mobcb.chart.bean.umeng;


import java.io.Serializable;
import java.util.List;

public class ChartCustomActionParam implements Serializable {
    private int type;
    private Object param;
    private List<ChartKeyValue> extra;
    public ChartCustomActionParam(int type, Object param) {
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

    public List<ChartKeyValue> getExtra() {
        return extra;
    }

    public void setExtra(List<ChartKeyValue> extra) {
        this.extra = extra;
    }
}
