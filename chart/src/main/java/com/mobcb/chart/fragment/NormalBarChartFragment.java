package com.mobcb.chart.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.mobcb.base.util.ScreenUtils;
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
 * 柱状图碎片
 */
public class NormalBarChartFragment extends BaseNormalFragment {
    private static final String TAG = NormalBarChartFragment.class.getSimpleName();
    private BarChart mChart;
    private String childId;//子图的id
    private String format;//日期格式,为null,则不支持筛选

    public NormalBarChartFragment() {
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
        mRoot = inflater.inflate(R.layout.fragment_normal_bar_chart, container, false);
        getArg();
        initView(mRoot);
        getChartDetail();
        return mRoot;
    }

    private void initView(View mRoot) {
        mChart = (BarChart) mRoot.findViewById(R.id.chart);
    }


    protected void dealWithChartBean(List<ChartDetailBean> chartList) {
        //是否显示描述
        mChart.getDescription().setEnabled(false);
        //是否支持双指缩放
        mChart.setPinchZoom(true);
        //是否绘制阴影
        mChart.setDrawBarShadow(false);
        //是否绘制网格
        mChart.setDrawGridBackground(false);

        //标注设置
        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        //动画时间
        mChart.animateXY(1000, 1000);

        //组间 间距
        float groupSpace = 0.08f;
        //组中柱子之间的间距
        float barSpace = 0.03f;
        //每个柱子的宽度
        float barWidth = 0.3f;

        //柱状图数据
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        //纵轴的描述(label)
        final List<String> yDesc = new ArrayList<>();
        //横轴的描述(label)
        final List<String> xDesc = new ArrayList<>();

        //柱子总数
        int count = 0;
        //横轴最大值
        int maxX = 0;


        //数据解析
        if (chartList != null && !chartList.isEmpty()) {
            //如果size为1,则有可能是tab下面的
            if (chartList.size() == 1) {
                ChartDetailBean chartDetailBean = chartList.get(0);
                if (chartDetailBean != null) {
                    childId = chartDetailBean.getChildId();
                    format = chartDetailBean.getDateFormat();
                }
            }
            for (int j = 0; j < chartList.size(); j++) {
                ChartDetailBean chartDetailBean = chartList.get(j);
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
            if (count > 0) {
                maxX = count / chartList.size();
            }
        }

        //柱子的宽度根据屏幕宽度,柱子总数计算得来
        float screenWidth = ScreenUtils.getScreenWidth();
        if (count > 0) {
            barWidth = screenWidth / count / 800;
            //每组中柱子之间的间距是柱子宽度的1/10
            barSpace = barWidth / 10;
            //组间 间距是柱子宽度+每组中柱子之间的间距
            groupSpace = barWidth + barSpace;
        }

        //如果已经有值,则重新设置数据
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            for (int i = 0; i < mChart.getData().getDataSetCount(); i++) {
                if (i < barDataSets.size()) {
                    ((BarDataSet) mChart.getData().getDataSetByIndex(i))
                            .setValues(barDataSets.get(i).getValues());
                }
            }
            //通知重新绘制图表
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //之前没有值,
            BarDataSet[] barDataSets1 = new BarDataSet[barDataSets.size()];
            for (int i = 0; i < barDataSets.size(); i++) {
                barDataSets1[i] = barDataSets.get(i);
            }
            BarData data = new BarData(barDataSets1);
            data.setValueTextColor(Color.BLACK);
            mChart.setData(data);
        }


        //设置每个柱子的宽度
        mChart.getBarData().setBarWidth(barWidth);
        //设置每组的间距,组中每个柱子之间的间距
        try {
            //只有当有两组或两组以上才可以调用这句代码
            mChart.groupBars(0, groupSpace, barSpace);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mChart.invalidate();


        // 图例显示设置
        Legend l = mChart.getLegend();
        //设置图例位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        //是否在图中绘制
        l.setDrawInside(true);

        //偏移设置
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //X轴设置
        XAxis xAxis = mChart.getXAxis();
        //设置刻度为1
        xAxis.setGranularity(1f);
        //设置x轴格式
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xDesc));
        //设置起始值
        if (chartList != null && chartList.size() > 1) {
            xAxis.setAxisMinimum(0f);
        } else {
            xAxis.setAxisMinimum(-1.0f);
        }
        //设置label是否居中,不居中则偏左
        if (chartList != null && chartList.size() > 1) {
            xAxis.setCenterAxisLabels(true);
        } else {
            xAxis.setCenterAxisLabels(false);
        }
        //设置X轴最大值
        xAxis.setAxisMaximum(maxX);
        //设置避免第一和最后一个柱子被遮挡
        xAxis.setAvoidFirstLastClipping(true);
        //设置x轴的位置为底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置不绘制纵线
        xAxis.setDrawGridLines(false);
        //设置旋转角度
        xAxis.setLabelRotationAngle(-45);
        //设置label的数量
        xAxis.setLabelCount((int) maxX);

        //左边Y轴设置
        YAxis leftAxis = mChart.getAxisLeft();
        //格式化显示
        leftAxis.setValueFormatter(new LargeValueFormatter());
        //不绘制横线
        leftAxis.setDrawGridLines(false);
        //上部保留空间
        leftAxis.setSpaceTop(35f);
        //y起始值
        leftAxis.setAxisMinimum(0f);
        //隐藏右边Y轴
        mChart.getAxisRight().setEnabled(false);
    }


}
