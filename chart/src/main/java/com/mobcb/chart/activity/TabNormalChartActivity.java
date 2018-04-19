package com.mobcb.chart.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.base.view.PagerSlidingTabStrip;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartBean;
import com.mobcb.chart.bean.ChartDetailBean;
import com.mobcb.chart.bean.ChartEventBusBean;
import com.mobcb.chart.fragment.BaseNormalFragment;
import com.mobcb.chart.fragment.NormalBarChartFragment;
import com.mobcb.chart.fragment.NormalLineChartFragment;
import com.mobcb.chart.fragment.NormalPieChartFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选项卡的方式显示多张图
 */
public class TabNormalChartActivity extends BaseChartActivity implements View.OnClickListener {
    private static final String TAG = TabNormalChartActivity.class.getSimpleName();
    private PagerSlidingTabStrip mIndicator;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_normal_chart);
        initView();
        initTitle("");
        getChartDetail(null, null);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ChartEventBusBean chartEventBusBean) {
        if (chartEventBusBean != null) {
            if(ChartEventBusBean.KEY_EVENT_BUS_SET_TIME_FORMAT.equalsIgnoreCase(chartEventBusBean.getAction())){
                if(chartEventBusBean.getObject()!=null){
                    setSelectable();
                }else{
                    setUnSelectable();
                }
            }
        }
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
    protected void onResume() {
        super.onResume();
        ImmersionBarHelper.setDarkFont(mActivity, null);
    }

    @Override
    protected void getArgument() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            ids = getIntent().getExtras().getString(KEY_BUNDLE_ID, "");
            groupTitle = getIntent().getExtras().getString(KEY_BUNDLE_GROUP_NAME, "");
        }
    }

    private void initView() {
        mIndicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mChartTvTimeStart = (TextView) findViewById(R.id.chart_tv_time_start);
        mChartTvTimeEnd = (TextView) findViewById(R.id.chart_tv_time_end);
        mChartBtnTimeSelect = (Button) findViewById(R.id.chart_btn_time_select);
        mChartBtnTimeSelect.setOnClickListener(this);
        mChartTvTimeStart.setOnClickListener(this);
        mChartTvTimeEnd.setOnClickListener(this);
        mChartLlTimeSelect = (LinearLayout) findViewById(R.id.chart_ll_time_select);
    }

    @Override
    protected void dealWithChartBean(ChartBean chartBean) {
        if (chartBean != null) {
            List<ChartDetailBean> chartList = chartBean.getChartList();
            if (chartList != null && !chartList.isEmpty()) {
                List<Fragment> fragments = new ArrayList<>();
                List<String> title = new ArrayList<>();
                for (ChartDetailBean chartDetailBean : chartList) {
                    if (chartDetailBean != null) {
                        title.add(chartDetailBean.getTitle());
                        if (chartList.size() == 1) {
                            groupTitle = chartDetailBean.getTitle();
                        }
                        if (ChartConstants.CHART_TYPE_BARS.equals(chartDetailBean.getChartType())) {
                            //柱状图
                            ArrayList<ChartDetailBean> list = new ArrayList<>(1);
                            list.add(chartDetailBean);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                            NormalBarChartFragment normalBarChartFragment = new NormalBarChartFragment();
                            normalBarChartFragment.setArguments(bundle);
                            fragments.add(normalBarChartFragment);
                        } else if (ChartConstants.CHART_TYPE_LINE.equals(chartDetailBean.getChartType())) {
                            ArrayList<ChartDetailBean> list = new ArrayList<>(1);
                            list.add(chartDetailBean);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                            NormalLineChartFragment lineChartFragment = new NormalLineChartFragment();
                            lineChartFragment.setArguments(bundle);
                            fragments.add(lineChartFragment);
                        } else if (ChartConstants.CHART_TYPE_PIE.equals(chartDetailBean.getChartType())) {
                            ArrayList<ChartDetailBean> list = new ArrayList<>(1);
                            list.add(chartDetailBean);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList(BaseNormalFragment.KEY_BUNDLE_CHART_LIST, list);
                            NormalPieChartFragment pieChartFragment = new NormalPieChartFragment();
                            pieChartFragment.setArguments(bundle);
                            fragments.add(pieChartFragment);
                        }
                    }
                }
                mViewPager.setAdapter(new MineViewPagerAdapter(getSupportFragmentManager(), fragments, title));
                mIndicator.setViewPager(mViewPager);
                mViewPager.setCurrentItem(0);
                initTitle(groupTitle);
                if (chartList.size() == 1) {
                    mIndicator.setVisibility(View.GONE);
                }

            }
        }
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
                EventBus.getDefault().post(new ChartEventBusBean(
                        ChartEventBusBean.KEY_EVENT_BUS_TIME_SELECT, list
                ));
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

    static class MineViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;
        List<String> title;

        public MineViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
            super(fm);
            this.fragments = fragments;
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title == null || position < 0 || position > title.size() ? "" : title.get(position);
        }
    }
}
