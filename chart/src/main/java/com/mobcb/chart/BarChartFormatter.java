package com.mobcb.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mobcb.base.util.LogUtils;

import java.util.Collection;

/**
 * Created by Sanguine on 2018/4/19.
 */

public class BarChartFormatter implements IAxisValueFormatter {

    private String[] mValues = new String[]{};
    private int mValueCount = 0;
    private float chartCount = 0.f;

    public BarChartFormatter() {
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public BarChartFormatter(String[] values) {
        if (values != null)
            setValues(values);
    }

    /**
     * Constructor that specifies axis labels.
     *
     * @param values The values string array
     */
    public BarChartFormatter(Collection<String> values, float chartCount) {
        this.chartCount = chartCount;
        if (values != null)
            setValues(values.toArray(new String[values.size()]));
    }

    public String getFormattedValue(float value, AxisBase axis) {
        int index = Math.round(value);

        if (index < 0 || index >= mValueCount || (int) value >= mValueCount) {
            return "";
        }

        if (index != (int) value) {
            if (chartCount > 1) {
                return mValues[(int) value];
            }
        }
        return mValues[index];
    }

    public String[] getValues() {
        return mValues;
    }

    public void setValues(String[] values) {
        if (values == null)
            values = new String[]{};

        this.mValues = values;
        this.mValueCount = values.length;
    }
}
