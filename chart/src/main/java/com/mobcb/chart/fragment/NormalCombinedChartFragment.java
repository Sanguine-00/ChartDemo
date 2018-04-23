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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mobcb.chart.ChartColorHelper;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;
import com.mobcb.chart.bean.DataProperty;
import com.mobcb.chart.view.NormalMarkerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalCombinedChartFragment extends BaseNormalFragment implements OnChartValueSelectedListener {

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
        getChartDetail();
        return mRoot;
    }

    private void initView() {
        mChart = (CombinedChart) mRoot.findViewById(R.id.chart);

        //隐藏描述
        mChart.getDescription().setEnabled(false);
        //设置是否可点击
        mChart.setTouchEnabled(true);
        //设置摩擦系数
        mChart.setDragDecelerationFrictionCoef(0.9f);
        //设置是否支持拖拽
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //设置是否绘制网络背景
        mChart.setDrawGridBackground(false);
        //设置动画时间
        mChart.animateXY(100, 100);
        //设置是否支持双指缩放
        mChart.setPinchZoom(true);
        //设置背景色
        mChart.setBackgroundColor(getResources().getColor(R.color.chart_bg));

        mChart.setOnChartValueSelectedListener(this);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE,
        });

    }

    @Override
    protected void dealWithChartBean(List<ChartDetailBean> chartList) {
        if (chartList != null && chartList.size() > 0) {
            List<ChartDetailBean> lineList = new ArrayList<>();
            List<ChartDetailBean> barList = new ArrayList<>();

            barList.add(chartList.get(0));
            chartList.remove(0);
            if (!chartList.isEmpty()) {
                lineList.addAll(chartList);
            }

//            for (ChartDetailBean chartDetailBean : chartList) {
//                if (chartDetailBean != null) {
//                    if (ChartConstants.CHART_TYPE_LINE.equalsIgnoreCase(chartDetailBean.getChartType())) {
//                        lineList.add(chartDetailBean);
//                    } else if (ChartConstants.CHART_TYPE_BARS.equalsIgnoreCase(chartDetailBean.getChartType())) {
//                        barList.add(chartDetailBean);
//                    }
//                }
//            }

            CombinedData data = new CombinedData();
            if (lineList != null && !lineList.isEmpty()) {
                data.setData(generateLineData(lineList));
            }
            if (barList != null && !barList.isEmpty()) {
                data.setData(generateBarData(barList));
            }

            mChart.setData(data);
            mChart.invalidate();

            setStyle();

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
                                DataProperty dataProperty = new DataProperty();
                                dataProperty.setColor(ChartColorHelper.getColorByIndex(mActivity, i + 1));
                                dataProperty.setName(chartDetailBean.getTitle());
                                dataProperty.setChartType(ChartConstants.CHART_TYPE_LINE);
                                yValues.add(new Entry(chartDataListBean.getXValue(),
                                        chartDataListBean.getYValue(),
                                        dataProperty));
                            }
                        }
                        LineDataSet set = new LineDataSet(yValues, chartDetailBean.getTitle());
                        //设置此线条的颜色
                        set.setColors(ChartColorHelper.getColorByIndex(mActivity, i + 1));
                        //是否绘制圆圈
                        set.setDrawCircles(false);
                        //设置线条的粗细
                        set.setLineWidth(2f);
                        set.setHighLightColor(ChartColorHelper.getColorByIndex(mActivity, i + 1));
                        set.setDrawValues(false);
                        //设置线条模式
                        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
                        //添加数据
                        sets.add(set);
                        set.setForm(Legend.LegendForm.CIRCLE);
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

        //数据解析
        if (barList != null && !barList.isEmpty()) {
            for (int j = 0; j < barList.size(); j++) {
                ChartDetailBean chartDetailBean = barList.get(j);
                List<BarEntry> entries = new ArrayList<>();
                if (chartDetailBean != null) {
                    List<ChartDataListBean> chartDataList = chartDetailBean.getChartDataList();
                    for (int i = 0; i < chartDataList.size(); i++) {
                        ChartDataListBean chartDataListBean = chartDataList.get(i);
                        if (chartDataListBean != null) {
                            DataProperty dataProperty = new DataProperty();
                            dataProperty.setColor(ChartColorHelper.getColorByIndex(getContext(), j));
                            dataProperty.setName(chartDetailBean.getTitle());
                            dataProperty.setChartType(ChartConstants.CHART_TYPE_BARS);
                            entries.add(new BarEntry(chartDataListBean.getXValue(), chartDataListBean.getYValue(), dataProperty));
                        }
                    }
                }
                BarDataSet barDataSet = new BarDataSet(entries, chartDetailBean.getTitle());
                barDataSet.setColor(ChartColorHelper.getColorByIndex(getContext(), j));
                barDataSet.setValueTextColor(getResources().getColor(R.color.chart_text));
                barDataSet.setValueTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
                barDataSet.setDrawValues(false);
                barDataSets.add(barDataSet);
                barDataSet.setForm(Legend.LegendForm.SQUARE);
            }


            BarDataSet[] barDataSets1 = new BarDataSet[barDataSets.size()];
            for (int i = 0; i < barDataSets.size(); i++) {
                barDataSets1[i] = barDataSets.get(i);
            }
            BarData data = new BarData(barDataSets1);
            data.setValueTextColor(Color.BLACK);
            return data;
        }
        return null;
    }

    private void setStyle() {
        //图例设置
        Legend l = mChart.getLegend();

        //设置图例的左边标志为圆点
        l.setForm(Legend.LegendForm.DEFAULT);
        //设置图例中文本字体大小
        l.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
        //设置图例中文本颜色
        l.setTextColor(getResources().getColor(R.color.chart_text));
        //设置图例位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置是否在图表中绘制
        l.setDrawInside(false);

        //偏移设置
        l.setYOffset(5f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);

        //X轴设置
        XAxis xAxis = mChart.getXAxis();
        //X轴中文本字体大小(dp)
        xAxis.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
        //X轴中文本字体颜色
        xAxis.setTextColor(getResources().getColor(R.color.chart_text));
        //X轴位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否绘制描述/标签
        xAxis.setDrawLabels(true);
        //是否绘制纵线
        xAxis.setDrawGridLines(false);
        //设置刻度
        xAxis.setGranularity(1f);
        //设置X轴最小值
        xAxis.setAxisMinimum(0f);
        //设置label格式
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(xDesc);
        xAxis.setValueFormatter(formatter);
        //设置旋转角度
        xAxis.setLabelRotationAngle(0f);
        //高度偏移
        xAxis.setYOffset(10);
        //避免首尾显示不全
        xAxis.setAvoidFirstLastClipping(true);
        //居中
        xAxis.setCenterAxisLabels(false);
        //设置网格颜色
        xAxis.setGridColor(getResources().getColor(R.color.chart_grid_color));


        //设置Y轴参数
        YAxis leftAxis = mChart.getAxisLeft();
        //设置Y轴字体颜色
        leftAxis.setTextColor(getResources().getColor(R.color.chart_text));
        //设置Y轴字体大小
        leftAxis.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
        //设置是否绘制横线
        leftAxis.setDrawGridLines(true);
        //设置是否绘制纵坐标的轴
        leftAxis.setDrawAxisLine(false);
        //设置纵坐标最小值
        leftAxis.setAxisMinimum(0f);
        //设置value的格式
        leftAxis.setValueFormatter(new LargeValueFormatter());

        //隐藏右边Y轴
        mChart.getAxisRight().setEnabled(false);


        //设置标注
        NormalMarkerView mv = new NormalMarkerView(mActivity, R.layout.layout_line_chart_marker_view, formatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        //发送EventBus,通知Activity关闭时间选择的弹窗
        EventBus.getDefault().post(new ChartEventBusBean(ChartEventBusBean.KEY_EVENT_BUS_HIDE_TIME_SELECT));
    }

    @Override
    public void onNothingSelected() {

    }
}
