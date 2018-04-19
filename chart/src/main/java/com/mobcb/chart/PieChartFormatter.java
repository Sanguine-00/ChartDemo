package com.mobcb.chart;

import android.content.Context;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mobcb.base.util.LogUtils;

/**
 * Created by Sanguine on 2018/4/19.
 */

public class PieChartFormatter extends PercentFormatter {
    private Context context;
    private float yValueSum;

    public PieChartFormatter(Context context, float yValueSum) {
        super();
        this.yValueSum = yValueSum;
        this.context = context;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        LogUtils.e(value);
        float percent = value / yValueSum * 100f;
        String result = String.format(context.getString(R.string.chart_pie_value_formatter),
                mFormat.format(value), mFormat.format(percent));
        return result;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        LogUtils.e(value);
        float percent = value / yValueSum * 100f;
        String result = String.format(context.getString(R.string.chart_pie_value_formatter),
                mFormat.format(value), mFormat.format(percent));
        return result;
    }
}
