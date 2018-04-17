package com.mobcb.chart.view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.mobcb.chart.R;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContentY;
    private TextView tvContentX;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContentY = (TextView) findViewById(R.id.contentY);
        tvContentX = (TextView) findViewById(R.id.contentX);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContentY.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContentY.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }

        try {
            tvContentX.setText(e.getData().toString()+":");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight()-40);
    }
}
