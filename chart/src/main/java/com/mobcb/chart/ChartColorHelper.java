package com.mobcb.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Created by Sanguine on 2018/4/19.
 */

public class ChartColorHelper {

    public static @ColorInt
    int getColorByIndex(Context context, int index) {
        if (context != null) {
            int[] colors = context.getResources().getIntArray(R.array.chart_content);
            if (colors != null && index >= 0 && colors.length > index) {
                return colors[index];
            }
        }
        return Color.parseColor("#00000000");
    }
}
