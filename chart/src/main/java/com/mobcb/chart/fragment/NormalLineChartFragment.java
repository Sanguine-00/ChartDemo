package com.mobcb.chart.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;
import com.mobcb.chart.view.MyMarkerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 折线图碎片
 */
public class NormalLineChartFragment extends BaseNormalFragment {
    private static final String TAG = NormalLineChartFragment.class.getSimpleName();
    private LineChart mChart;
    private List<String> xDesc = null;
    private String childId;//子图的id
    private String format;

    public NormalLineChartFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ChartEventBusBean chartEventBusBean) {
        if (chartEventBusBean != null && isVisibleToUser) {
            if (ChartEventBusBean.KEY_EVENT_BUS_TIME_SELECT.equalsIgnoreCase(chartEventBusBean.getAction())) {
                if (chartEventBusBean.getObject() != null) {
                    List<Map<String, String>> list = (List<Map<String, String>>) chartEventBusBean.getObject();
                    if (list != null && !TextUtils.isEmpty(childId)) {
                        getChartDetail(list, childId);
                    }
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            if (!TextUtils.isEmpty(format)) {
                EventBus.getDefault().post(new ChartEventBusBean(
                        ChartEventBusBean.KEY_EVENT_BUS_SET_TIME_FORMAT,
                        format
                ));
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_normal_line_chart, container, false);
        getArg();
        initView(mRoot);
        getChartDetail();
        return mRoot;
    }

    private void initView(View mView) {
        mChart = (LineChart) mView.findViewById(R.id.chart);
    }

    @Override
    protected void dealWithChartBean(List<ChartDetailBean> chartList) {

        //设置标注
        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        //隐藏描述
        mChart.getDescription().setEnabled(false);
        mChart.getDescription().setText("");
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
        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        if (chartList != null && !chartList.isEmpty()) {
            //如果size为1,则有可能是tab下面的
            if (chartList.size() == 1) {
                ChartDetailBean chartDetailBean = chartList.get(0);
                if (chartDetailBean != null) {
                    childId = chartDetailBean.getChildId();
                    format = chartDetailBean.getDateFormat();
                }
            }
            List<LineDataSet> sets = new ArrayList<>();
            xDesc = new ArrayList<>(chartList.size());
            float maxY = 0;
            float maxX = 0;
            for (int i = 0; i < chartList.size(); i++) {
                ChartDetailBean chartDetailBean = chartList.get(i);
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
            // set data
            mChart.setData(data);
            setStyle(maxX, maxY);
        }

    }

    private void setStyle(float maxX, float maxY) {
        mChart.animateX(1500);

        //图例设置
        Legend l = mChart.getLegend();

        //设置图例的左边标志为横线
        l.setForm(Legend.LegendForm.LINE);
        //设置图例中文本字体大小
        l.setTextSize(11f);
        //设置图例中文本颜色
        l.setTextColor(Color.BLACK);
        //设置图例位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置是否在图表中绘制
        l.setDrawInside(false);

        //X轴设置
        XAxis xAxis = mChart.getXAxis();
        //X轴中文本字体大小
        xAxis.setTextSize(11f);
        //X轴中文本字体颜色
        xAxis.setTextColor(Color.BLACK);
        //X轴位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否绘制描述/标签
        xAxis.setDrawLabels(true);
        //是否绘制纵线
        xAxis.setDrawGridLines(false);
        //设置刻度
        xAxis.setGranularity(1f);
        //设置X轴最小值
        xAxis.setAxisMinimum(-0.5f);
        //设置最大值
        xAxis.setAxisMaximum(maxX);
        //设置label的数量
//        xAxis.setLabelCount(maxX);
        //设置格式
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xDesc));
        //设置旋转角度
        xAxis.setLabelRotationAngle(-45);
        //不居中
        xAxis.setCenterAxisLabels(false);


        //设置Y轴参数
        YAxis leftAxis = mChart.getAxisLeft();
        //设置Y轴字体颜色
        leftAxis.setTextColor(Color.BLACK);
        //设置是否绘制横线
        leftAxis.setDrawGridLines(true);

        //隐藏右边Y轴
        mChart.getAxisRight().setEnabled(false);
    }
}
