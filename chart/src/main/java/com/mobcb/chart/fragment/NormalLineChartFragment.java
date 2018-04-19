package com.mobcb.chart.fragment;


import android.app.Activity;
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
import com.mobcb.chart.ChartColorHelper;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartColorName;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;
import com.mobcb.chart.view.LineMarkerView;

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
        mChart.setBackgroundColor(getResources().getColor(R.color.chart_bg));
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
                                ChartColorName colorName = new ChartColorName();
                                colorName.setColor(ChartColorHelper.getColorByIndex(mActivity, i));
                                colorName.setName(chartDetailBean.getName());
                                yValues.add(new Entry(chartDataListBean.getXValue(),
                                        chartDataListBean.getYValue(),
                                        colorName));
                            }
                        }
                        LineDataSet set = new LineDataSet(yValues, chartDetailBean.getTitle());
                        //设置此线条的颜色
                        set.setColors(ChartColorHelper.getColorByIndex(mActivity, i));
                        //是否绘制圆圈
                        set.setDrawCircles(false);
                        //设置线条的粗细
                        set.setLineWidth(2f);

                        set.setHighLightColor(ChartColorHelper.getColorByIndex(mActivity, i));
                        set.setDrawValues(false);
                        //设置线条模式
                        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
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
            data.setValueTextColor(getResources().getColor(R.color.chart_text));
            data.setValueTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
            // set data
            mChart.setData(data);
            setStyle(maxX, maxY);
        }

    }

    private void setStyle(float maxX, float maxY) {
        mChart.animateX(1500);

        //图例设置
        Legend l = mChart.getLegend();

        //设置图例的左边标志为圆点
        l.setForm(Legend.LegendForm.CIRCLE);
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
        //设置最大值
        xAxis.setAxisMaximum(maxX);
        //设置label的数量
//        xAxis.setLabelCount(maxX);
        //设置label格式
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(xDesc);
        xAxis.setValueFormatter(formatter);
        //设置旋转角度
        xAxis.setLabelRotationAngle(0f);
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

        //隐藏右边Y轴
        mChart.getAxisRight().setEnabled(false);


        //设置标注
        LineMarkerView mv = new LineMarkerView(mActivity, R.layout.layout_line_chart_marker_view, formatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
    }
}
