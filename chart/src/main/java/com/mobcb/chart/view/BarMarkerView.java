package com.mobcb.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.UnitUtils;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartColorName;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class BarMarkerView extends MarkerView {

    private TextView mTvChartYValue;
    private TextView mTvChartLineName;
    private TextView mTvChartXValue;
    private IAxisValueFormatter formatter;

    public BarMarkerView(Context context, int layoutResource, IAxisValueFormatter formatter) {
        super(context, layoutResource);
        this.formatter = formatter;
        mTvChartYValue = (TextView) findViewById(R.id.chart_y_value);
        mTvChartLineName = (TextView) findViewById(R.id.chart_line_name);
        mTvChartXValue = (TextView) findViewById(R.id.chart_x_label);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            mTvChartYValue.setText(String.format("%s",
                    Utils.formatNumber(ce.getHigh(), 0, true, ',')));
        } else {
            mTvChartYValue.setText(String.format("%s",
                    Utils.formatNumber(e.getY(), 0, true, ',')));
        }
        float x = e.getX();
        if (formatter != null) {
            mTvChartXValue.setText(formatter.getFormattedValue(x, null));
        }

        Object data = e.getData();
        if (data != null && data instanceof ChartColorName) {
            ChartColorName colorName = (ChartColorName) data;
            mTvChartLineName.setText(String.format(" %s: ", colorName.getName()));
            final int color = colorName.getColor();
            Shape shape = new Shape() {
                @Override
                public void draw(Canvas canvas, Paint paint) {
                    //设置颜色
                    paint.setColor(color);
                    //设置抗锯齿
                    paint.setAntiAlias(true);
                    //设置画笔粗细
                    paint.setStrokeWidth(2);
                    //设置是否为空心
                    paint.setStyle(Paint.Style.FILL);
                    Rect rect = new Rect(0,
                            0,
                            UnitUtils.dip2px(getContext(), 4),
                            UnitUtils.dip2px(getContext(), 4));
                    canvas.drawRect(rect, paint);
                }
            };
            shape.resize(UnitUtils.dip2px(getContext(), 5), UnitUtils.dip2px(getContext(), 5));
            ShapeDrawable drawable = new ShapeDrawable(shape);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mTvChartLineName.setCompoundDrawables(drawable, null, null, null);
            mTvChartLineName.setCompoundDrawablePadding(UnitUtils.dip2px(getContext(), 6));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 40);
    }
}