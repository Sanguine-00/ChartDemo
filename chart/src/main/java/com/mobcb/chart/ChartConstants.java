package com.mobcb.chart;

/**
 * Created by Sanguine on 2018/4/3.
 */

public @interface ChartConstants {
    String CHART_TYPE_BARS = "bar";//1柱状图
    String CHART_TYPE_LINE = "line";//2折线图
    String CHART_TYPE_PIE = "pie";//3饼图
    String CHART_TYPE_COMBINED = "combined";//柱状图和折线图在一张图里

    String CHART_DRAW_TYPE_OVERLAY = "overlay";
    String CHART_DRAW_TYPE_TAB = "tab";

    float CHART_TEXT_SIZE_DP = 10F;

}
