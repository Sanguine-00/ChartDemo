package com.mobcb.chart.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.util.FragmentUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.fragment.BaseNormalFragment;
import com.mobcb.chart.fragment.NormalBarChartFragment;
import com.mobcb.chart.fragment.NormalLineChartFragment;
import com.mobcb.chart.fragment.NormalPieChartFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 层叠显示多张图
 */
public class OverlayNormalChartActivity extends BaseChartActivity implements View.OnClickListener {
    private static final String TAG = OverlayNormalChartActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_normal_chart);
        initView();
        initTitle(groupTitle);
        getChartDetail(null, null);
    }

    protected void initTitle(String titleText) {
        ToolbarHelper.instance().init(mActivity, null)
                .setTitle(titleText)
                .setTitleColor(getResources().getColor(R.color.chart_title_text))
                .setBackgroundColor(getResources().getColor(R.color.chart_title_bg))
                .setLeft(com.mobcb.base.R.drawable.base_ic_black_back, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mChartLlTimeSelect != null && View.VISIBLE == mChartLlTimeSelect.getVisibility()) {
                            mChartLlTimeSelect.setVisibility(View.GONE);
                            return;
                        }
                        mActivity.finish();
                    }
                });
    }

    @Override
    protected void getArgument() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            ids = getIntent().getExtras().getString(KEY_BUNDLE_ID, "");
            groupTitle = getIntent().getExtras().getString(KEY_BUNDLE_GROUP_NAME, "");
        }
    }

    @Override
    protected void dealWithChartBean(ChartBean chartBean) {
        if (chartBean != null) {
            List<ChartDetailBean> chartList = chartBean.getChartList();
            if (chartList != null && !chartList.isEmpty()) {
                ChartDetailBean chartDetailBean = chartList.get(0);
                if (chartDetailBean != null) {
                    //判断dateFormat,如果不为null,则需要支持筛选
                    dateFormat = chartDetailBean.getDateFormat();
                    if (dateFormat != null) {
                        setSelectable();
                    } else {
                        setUnSelectable();
                    }

                    ArrayList<ChartDetailBean> list = new ArrayList<>(chartList.size());
                    list.addAll(chartList);
                    if (ChartConstants.CHART_TYPE_BARS.equals(chartDetailBean.getChartType())) {
                        //柱状图
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                        NormalBarChartFragment normalBarChartFragment = new NormalBarChartFragment();
                        normalBarChartFragment.setArguments(bundle);
                        FragmentUtils.add(getSupportFragmentManager(), normalBarChartFragment, R.id.content);
                        FragmentUtils.show(normalBarChartFragment);
                    } else if (ChartConstants.CHART_TYPE_LINE.equals(chartDetailBean.getChartType())) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                        NormalLineChartFragment lineChartFragment = new NormalLineChartFragment();
                        lineChartFragment.setArguments(bundle);
                        FragmentUtils.add(getSupportFragmentManager(), lineChartFragment, R.id.content);
                        FragmentUtils.show(lineChartFragment);

                    } else if (ChartConstants.CHART_TYPE_PIE.equals(chartDetailBean.getChartType())) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                        NormalPieChartFragment pieChartFragment = new NormalPieChartFragment();
                        pieChartFragment.setArguments(bundle);
                        FragmentUtils.add(getSupportFragmentManager(), pieChartFragment, R.id.content);
                        FragmentUtils.show(pieChartFragment);

                    }
                }

            }
        }
    }

    protected void initView() {
        mChartTvTimeStart = (TextView) findViewById(R.id.chart_tv_time_start);
        mChartTvTimeEnd = (TextView) findViewById(R.id.chart_tv_time_end);
        mChartBtnTimeSelect = (Button) findViewById(R.id.chart_btn_time_select);
        mChartBtnTimeSelect.setOnClickListener(this);
        mChartTvTimeStart.setOnClickListener(this);
        mChartTvTimeEnd.setOnClickListener(this);
        mChartLlTimeSelect = (LinearLayout) findViewById(R.id.chart_ll_time_select);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chart_btn_time_select) {
            //开始筛选
            String startTime = mChartTvTimeStart.getText().toString().trim();
            String endTime = mChartTvTimeEnd.getText().toString().trim();
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                List<Map<String, String>> list = new ArrayList<>();
                Map<String, String> startMap = new HashMap<>();
                startMap.put("rule", "GEQ");
                startMap.put("values", startTime);
                list.add(startMap);
                Map<String, String> endMap = new HashMap<>();
                endMap.put("rule", "LEQ");
                endMap.put("values", endTime);
                list.add(endMap);
                getChartDetail(list, null);
            } else {
                ToastUtils.showShort(R.string.chart_time_error);
            }
            if (mChartLlTimeSelect != null) {
                mChartLlTimeSelect.setVisibility(View.GONE);
            }
        } else if (i == R.id.chart_tv_time_start) {
            //选择开始时间
            startTimePickerView.show();
        } else if (i == R.id.chart_tv_time_end) {
            //选择结束时间
            endTimePickerView.show();
        } else {

        }
    }
}
