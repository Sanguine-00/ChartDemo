package com.mobcb.chart.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mobcb.base.util.ScreenUtils;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalCombinedChartFragment extends BaseNormalFragment {

    private CombinedChart mChart;
    private ArrayList<String> xDesc;

    public NormalCombinedChartFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_normal_combined_chart, container, false);
        initView();
        return mRoot;
    }

    private void initView() {
        mChart = (CombinedChart) mRoot.findViewById(R.id.chart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE,
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


    }

    @Override
    protected void dealWithChartBean(List<ChartDetailBean> chartList) {
        if (chartList != null && chartList.size() > 0) {
            List<ChartDetailBean> lineList = new ArrayList<>();
            List<ChartDetailBean> barList = new ArrayList<>();
            for (ChartDetailBean chartDetailBean : chartList) {
                if (chartDetailBean != null) {
                    if (ChartConstants.CHART_TYPE_LINE.equalsIgnoreCase(chartDetailBean.getChartType())) {
                        lineList.add(chartDetailBean);
                    } else if (ChartConstants.CHART_TYPE_BARS.equalsIgnoreCase(chartDetailBean.getChartType())) {
                        barList.add(chartDetailBean);
                    }
                }
            }

            CombinedData data = new CombinedData();
            if (lineList != null && !lineList.isEmpty()) {
                data.setData(generateLineData(lineList));
            }
            if (barList != null && !barList.isEmpty()) {
                data.setData(generateBarData(barList));
            }

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xDesc));
            xAxis.setAxisMaximum(data.getXMax() + 0.25f);

            mChart.setData(data);
            mChart.invalidate();

        }
    }


    private LineData generateLineData(List<ChartDetailBean> lineList) {
        if (lineList != null && !lineList.isEmpty()) {
            List<LineDataSet> sets = new ArrayList<>();
            xDesc = new ArrayList<>(lineList.size());
            float maxY = 0;
            float maxX = 0;
            for (int i = 0; i < lineList.size(); i++) {
                ChartDetailBean chartDetailBean = lineList.get(i);
                if (chartDetailBean != null) {
                    List<Entry> yValues = new ArrayList<>();
                    List<ChartDataListBean> chartDataList = chartDetailBean.getChartDataList();
                    if (chartDataList != null && !chartDataList.isEmpty()) {
                        maxX = chartDataList.size() > maxX ? chartDataList.size() : maxX;
                        for (int j = 0; j < chartDataList.size(); j++) {
                            ChartDataListBean chartDataListBean = chartDataList.get(j);
                            if (chartDataListBean != null) {
                                if (xDesc != null && xDesc.size() < chartDataList.size()) {
                                    xDesc.add(chartDataListBean.getXDesc());
                                }
                                maxY = chartDataListBean.getYValue() > maxY ? chartDataListBean.getYValue() : maxY;
                                yValues.add(new Entry(chartDataListBean.getXValue(),
                                        chartDataListBean.getYValue(),
                                        chartDataListBean.getXDesc()));
                            }
                        }
                        LineDataSet set = new LineDataSet(yValues, chartDetailBean.getTitle());
                        //设置值的文本颜色
                        set.setValueTextColor(Color.BLACK);
                        set.setAxisDependency(YAxis.AxisDependency.LEFT);
                        //设置颜色集
                        set.setColors(getColors(i));
                        //设置圆圈的颜色
                        set.setCircleColor(Color.YELLOW);
                        //设置圆圈是否空心
                        set.setDrawCircleHole(false);
                        //设置线的粗细
                        set.setLineWidth(2f);
                        //设置圆角度数
                        set.setCircleRadius(3f);
                        //设置透明度
                        set.setFillAlpha(65);
                        //设置填充颜色
                        set.setFillColor(ColorTemplate.getHoloBlue());
                        //设置高亮颜色
                        set.setHighLightColor(getColors(i).get(0));
                        //添加数据
                        sets.add(set);
                    }
                }
            }
            LineDataSet[] lineDataSets = new LineDataSet[sets.size()];
            for (int i = 0; i < sets.size(); i++) {
                lineDataSets[i] = sets.get(i);
            }
            LineData data = new LineData(lineDataSets);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            return data;
        }
        return null;
    }

    private BarData generateBarData(List<ChartDetailBean> barList) {
        //柱状图数据
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        //纵轴的描述(label)
        final List<String> yDesc = new ArrayList<>();

        int count = 0;
        //数据解析
        if (barList != null && !barList.isEmpty()) {
            for (int j = 0; j < barList.size(); j++) {
                ChartDetailBean chartDetailBean = barList.get(j);
                List<BarEntry> entries = new ArrayList<>();
                if (chartDetailBean != null) {
                    List<ChartDataListBean> chartDataList = chartDetailBean.getChartDataList();
                    for (int i = 0; i < chartDataList.size(); i++) {
                        count += 1;
                        ChartDataListBean chartDataListBean = chartDataList.get(i);
                        if (chartDataListBean != null) {
                            xDesc.add(i, chartDataListBean.getXDesc());
                            yDesc.add(chartDetailBean.getTitle());
                            entries.add(new BarEntry(chartDataListBean.getXValue(), chartDataListBean.getYValue(), chartDetailBean.getTitle()));
                        }
                    }
                }
                BarDataSet barDataSet = new BarDataSet(entries, chartDetailBean.getTitle());
                barDataSet.setColor(getColors(j).get(0));
                barDataSets.add(barDataSet);
            }

            //柱子的宽度根据屏幕宽度,柱子总数计算得来
            float screenWidth = ScreenUtils.getScreenWidth();
            float barWidth = 0f;
            if (count > 0) {
                barWidth = screenWidth / count / 800;
            }

            BarDataSet[] barDataSets1 = new BarDataSet[barDataSets.size()];
            for (int i = 0; i < barDataSets.size(); i++) {
                barDataSets1[i] = barDataSets.get(i);
            }
            BarData data = new BarData(barDataSets1);
            data.setValueTextColor(Color.BLACK);
            data.setBarWidth(barWidth);
            return data;
        }
        return null;
    }
}
