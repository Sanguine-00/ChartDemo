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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mobcb.chart.BarChartFormatter;
import com.mobcb.chart.ChartColorHelper;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartColorName;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;
import com.mobcb.chart.view.BarMarkerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 柱状图碎片
 */
public class NormalBarChartFragment extends BaseNormalFragment implements OnChartValueSelectedListener {
    private static final String TAG = NormalBarChartFragment.class.getSimpleName();
    private BarChart mChart;
    private String childId;//子图的id
    private String format = null;//日期格式,为null,则不支持筛选

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
        if (isVisibleToUser && mRoot != null) {
            EventBus.getDefault().post(new ChartEventBusBean(
                    ChartEventBusBean.KEY_EVENT_BUS_SET_TIME_FORMAT,
                    format
            ));
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
        //是否显示描述
        mChart.getDescription().setEnabled(false);
        //是否支持双指缩放
        mChart.setPinchZoom(true);
        //是否绘制阴影
        mChart.setDrawBarShadow(false);
        //是否绘制网格
        mChart.setDrawGridBackground(false);
        //设置背景色
        mChart.setBackgroundColor(getResources().getColor(R.color.chart_bg));

        //动画时间
        mChart.animateXY(100, 100);

        mChart.setOnChartValueSelectedListener(this);
    }


    protected void dealWithChartBean(List<ChartDetailBean> chartList) {


        //组间 间距
        float groupSpace = 0.f;
        //组中柱子之间的间距
        float barSpace = 0.f;
        //每个柱子的宽度
        float barWidth = 0.2f;

        //柱状图数据
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        //横轴的描述(label)
        List<String> xDesc = new ArrayList<>();

        //柱状图数量
        float chartCount = 0;

        float groupCount = 0;


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

            chartCount = chartList.size();

            for (int j = 0; j < chartList.size(); j++) {
                ChartDetailBean chartDetailBean = chartList.get(j);
                List<BarEntry> entries = new ArrayList<>();
                if (chartDetailBean != null) {
                    List<ChartDataListBean> chartDataList = chartDetailBean.getChartDataList();
                    if (chartDataList != null && !chartDataList.isEmpty()) {
                        groupCount = chartDataList.size();
                        for (int i = 0; i < chartDataList.size(); i++) {
                            ChartDataListBean chartDataListBean = chartDataList.get(i);
                            if (chartDataListBean != null) {
                                if (xDesc != null && xDesc.size() < chartDataList.size()) {
                                    xDesc.add(chartDataListBean.getXDesc());
                                }
                                ChartColorName colorName = new ChartColorName();
                                colorName.setColor(ChartColorHelper.getColorByIndex(getContext(), j));
                                colorName.setName(chartDetailBean.getTitle());
                                entries.add(new BarEntry(chartDataListBean.getXValue(), chartDataListBean.getYValue(), colorName));
                            }
                        }
                    }
                }
                BarDataSet barDataSet = new BarDataSet(entries, chartDetailBean.getTitle());
                barDataSet.setColor(ChartColorHelper.getColorByIndex(getContext(), j));
                barDataSet.setValueTextColor(getResources().getColor(R.color.chart_text));
                barDataSet.setValueTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
                barDataSet.setDrawValues(false);
                barDataSets.add(barDataSet);
            }
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


        //每组中柱子之间的间距
        barSpace = 0.03f;
        //组间
        groupSpace = 0.08f;
        try {
            barWidth = (1 - groupSpace - barSpace * chartCount) / chartCount;
            //设置每个柱子的宽度
            mChart.getBarData().setBarWidth(barWidth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mChart.invalidate();

        // 图例显示设置
        Legend l = mChart.getLegend();
        //设置图例的左边标志为圆点
        l.setForm(Legend.LegendForm.SQUARE);
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
        //设置刻度为1
        //设置x轴label格式
        BarChartFormatter formatter = new BarChartFormatter(xDesc, chartCount);
        xAxis.setValueFormatter(formatter);

        //设置起始值
        xAxis.setAxisMinimum(0f);
        //设置避免第一和最后一个label被遮挡
        xAxis.setAvoidFirstLastClipping(true);
        //设置x轴的位置为底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置不绘制纵线
        xAxis.setDrawGridLines(false);
        //设置旋转角度
        xAxis.setLabelRotationAngle(0);
        //设置字体
        xAxis.setTextColor(getResources().getColor(R.color.chart_text));
        xAxis.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
        float maxX = mChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount;
        xAxis.setAxisMaximum(maxX);
        try {
            if (1 < chartCount) {
                //只有当有两组或两组以上才可以调用这句代码
                mChart.groupBars(0f, groupSpace, barSpace);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        mChart.getAxisRight().setEnabled(false);

        //设置标注
        BarMarkerView mv = new BarMarkerView(mActivity, R.layout.layout_line_chart_marker_view, formatter);
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
