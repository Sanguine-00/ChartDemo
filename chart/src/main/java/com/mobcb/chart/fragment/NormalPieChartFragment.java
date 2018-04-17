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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
 * 饼图碎片
 */
public class NormalPieChartFragment extends BaseNormalFragment {
    private PieChart mChart;
    private String childId;//子图的id
    private String format;

    public NormalPieChartFragment() {
        // Required empty public constructor
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
        mRoot = inflater.inflate(R.layout.fragment_normal_pie_chart, container, false);
        getArg();
        initView(mRoot);
        getChartDetail();
        return mRoot;
    }

    private void initView(View mRoot) {
        mChart = (PieChart) mRoot.findViewById(R.id.chart);

        //使用百分比
        mChart.setUsePercentValues(true);
        //隐藏描述
        mChart.getDescription().setEnabled(false);
        //设置图表偏移
        mChart.setExtraOffsets(5, 10, 5, 5);
        //设置摩擦系数
        mChart.setDragDecelerationFrictionCoef(0.95f);
        //设置是否中间空间
        mChart.setDrawHoleEnabled(false);
        //设置中间空间的颜色
//        mChart.setHoleColor(Color.WHITE);
        //设置中间空间占整个图的百分比
        mChart.setHoleRadius(58f);

        //设置透明圆的颜色
        mChart.setTransparentCircleColor(Color.WHITE);
        //设置透明圆的透明度
        mChart.setTransparentCircleAlpha(110);
        //设置透明圆所占这个图的百分比
        mChart.setTransparentCircleRadius(61f);

        //设置是否绘制中间文本
        mChart.setDrawCenterText(true);


        //设置旋转度数
        mChart.setRotationAngle(0);
        //设置是否旋转
        mChart.setRotationEnabled(true);
        //设置选中是否高亮
        mChart.setHighlightPerTapEnabled(true);

        //设置动画时间
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //设置标注
        MyMarkerView mv = new MyMarkerView(mActivity, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart


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
                    dataSet.setColors(getColors(3));
                    //设置饼之间的间距
                    dataSet.setSliceSpace(3f);
                    //设置选中突出的偏移
                    dataSet.setSelectionShift(5f);
                    //设置文本在外部
                    dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                    //设置文本中第一段线的长度
                    dataSet.setValueLinePart1Length(1f);
                    PieData data = new PieData(dataSet);
                    //设置值的格式为百分比
                    data.setValueFormatter(new PercentFormatter());
                    //设置文本字体大小
                    data.setValueTextSize(11f);
                    //设置文本颜色
                    data.setValueTextColor(Color.BLACK);
                    mChart.setData(data);
                    // undo all highlights
                    mChart.highlightValues(null);
                    mChart.invalidate();
                }
            }
        }
    }
}
