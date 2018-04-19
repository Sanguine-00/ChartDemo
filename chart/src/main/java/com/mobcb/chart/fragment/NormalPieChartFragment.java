package com.mobcb.chart.fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.mobcb.chart.ChartColorHelper;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.PieChartFormatter;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartDataListBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 饼图碎片
 */
public class NormalPieChartFragment extends BaseNormalFragment implements OnChartValueSelectedListener {
    private PieChart mChart;
    private String childId;//子图的id
    private String format;
    private PercentFormatter formatter = new PercentFormatter();

    public NormalPieChartFragment() {
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
        mRoot = inflater.inflate(R.layout.fragment_normal_pie_chart, container, false);
        getArg();
        initView(mRoot);
        getChartDetail();
        return mRoot;
    }

    private void initView(View mRoot) {
        mChart = (PieChart) mRoot.findViewById(R.id.chart);

        //使用百分比
        mChart.setUsePercentValues(false);

        //隐藏描述
        mChart.getDescription().setEnabled(false);
        //设置图表偏移
        mChart.setExtraOffsets(5, 5, 5, 5);
        //设置摩擦系数
        mChart.setDragDecelerationFrictionCoef(0.95f);
        //设置是否中间空间
        mChart.setDrawHoleEnabled(true);

        //设置中间空间的颜色
        mChart.setHoleColor(Color.TRANSPARENT);
        //设置中间空间占整个图的百分比
        mChart.setHoleRadius(68f);

        //设置是否绘制中间文本
        mChart.setDrawCenterText(true);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setCenterTextSizePixels(getResources().getDimension(R.dimen.font20));
        mChart.setCenterTextColor(getResources().getColor(R.color.chart_text));
        mChart.setCenterTextRadiusPercent(50f);

        //设置旋转度数
        mChart.setRotationAngle(0);
        //设置是否旋转
        mChart.setRotationEnabled(true);
        //设置选中是否高亮
        mChart.setHighlightPerTapEnabled(true);

        mChart.setBackgroundColor(getResources().getColor(R.color.chart_bg));

        //设置动画时间
        mChart.animateY(1000, Easing.EasingOption.EaseInOutQuad);
    }

    @Override
    protected void dealWithChartBean(List<ChartDetailBean> chartList) {
        if (chartList != null && !chartList.isEmpty()) {
            ChartDetailBean chartDetailBean = chartList.get(0);
            if (chartDetailBean != null) {
                childId = chartDetailBean.getChildId();
                format = chartDetailBean.getDateFormat();
                List<ChartDataListBean> chartDataList = chartDetailBean.getChartDataList();
                if (chartDataList != null && !chartDataList.isEmpty()) {
                    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
                    for (ChartDataListBean chartDataListBean : chartDataList) {
                        if (chartDataListBean != null) {
                            entries.add(new PieEntry(chartDataListBean.getYValue(), chartDataListBean.getXDesc()));
                        }
                    }

                    PieDataSet dataSet = new PieDataSet(entries, "");//chartDetailBean.getTitle()
                    //设置是否绘制图标
                    dataSet.setDrawIcons(false);
                    //设置颜色集
                    dataSet.setColors(ChartColorHelper.getColors(getContext()));
                    //设置饼之间的间距
                    dataSet.setSliceSpace(0f);
                    //设置选中突出的偏移
                    dataSet.setSelectionShift(10f);

                    //设置y文本在外部
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    //设置x文本在外部
                    dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    //设置指示线中第一段线的长度
                    dataSet.setValueLinePart1Length(0.2f);
                    //设置指示线的颜色
                    dataSet.setValueLineColor(getResources().getColor(R.color.chart_text));
                    PieData data = new PieData(dataSet);
                    //设置值的格式为百分比;
                    data.setValueFormatter(new PieChartFormatter(getContext(), data.getYValueSum()));
                    //设置文本字体大小
                    data.setValueTextSize(ChartConstants.CHART_TEXT_SIZE_DP);
                    //设置文本颜色
                    data.setValueTextColor(getResources().getColor(R.color.chart_text));

                    //设置数据
                    mChart.setData(data);

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

                    //偏移设置
                    l.setYOffset(5f);
                    l.setXOffset(0f);
                    l.setYEntrySpace(0f);
                    l.setTextSize(ChartConstants.CHART_TEXT_SIZE_DP);

                    //设置label的样式
                    mChart.setEntryLabelColor(getResources().getColor(R.color.chart_text));
                    mChart.setEntryLabelTextSize(ChartConstants.CHART_TEXT_SIZE_DP);

                    // undo all highlights
                    mChart.highlightValues(null);

                    mChart.invalidate();
                }
            }
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e != null) {
            float value = e.getY() / mChart.getData().getYValueSum() * 100f;
            mChart.setCenterText(formatter.getFormattedValue(value, e, 0, null));
        }
    }

    @Override
    public void onNothingSelected() {
        mChart.setCenterText("");
    }
}
