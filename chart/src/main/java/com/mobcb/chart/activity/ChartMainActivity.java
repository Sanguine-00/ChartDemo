package com.mobcb.chart.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.GlideUtil;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.ScreenUtils;
import com.mobcb.base.util.UnitUtils;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartHomePageItem;
import com.mobcb.chart.presenter.ChartMainPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ChartMainActivity extends BaseActivity<ChartMainPresenter> implements BaseMvpView, OnRefreshListener {

    private LinearLayout mAppMainLlHomeItems;
    private SmartRefreshLayout mAppMainRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_main);
        initView();
        initTitle(getString(R.string.chart_title));
        mPresenter = new ChartMainPresenter(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        ImmersionBarHelper.setDarkFont(this, null);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void initView() {
        mAppMainLlHomeItems = (LinearLayout) findViewById(R.id.app_main_ll_home_items);
        mAppMainRefreshLayout = (SmartRefreshLayout) findViewById(R.id.app_main_refresh_layout);

        mAppMainRefreshLayout.setEnableLoadMore(false);
        mAppMainRefreshLayout.setOnRefreshListener(this);
    }

    public void endRefresh() {
        if (mAppMainRefreshLayout != null) {
            mAppMainRefreshLayout.finishRefresh();
        }
    }

    public void showData(List<ChartHomePageItem> itemList) {
        if (itemList != null) {
            //为null时说明获取失败，不删除原菜单
            mAppMainLlHomeItems.removeAllViews();
        }
        if (itemList != null && itemList.size() > 0) {
            Comparator<ChartHomePageItem> c = new Comparator<ChartHomePageItem>() {
                @Override
                public int compare(ChartHomePageItem item1, ChartHomePageItem item2) {
                    Integer order1 = item1.getOrderNo();
                    Integer order2 = item2.getOrderNo();
                    return order1.compareTo(order2);
                }
            };
            Collections.sort(itemList, c);

            Map<String, List<ChartHomePageItem>> homePageItemMap = new TreeMap<>(
                    new Comparator<String>() {
                        public int compare(String key1, String key2) {
                            // 升序排序
                            return key1.compareTo(key2);
                        }
                    });
            for (ChartHomePageItem homePageItem : itemList) {
                //只有4需要显示,其他不显示
                if (homePageItem != null && "4".equalsIgnoreCase(homePageItem.getUrlType())) {
                    String groupId = homePageItem.getGroupId();
                    List<ChartHomePageItem> mapItemList = homePageItemMap.get(groupId);
                    if (mapItemList != null && mapItemList.size() <= 3) {
                        mapItemList.add(homePageItem);
                    } else {
                        List<ChartHomePageItem> newMapItemList = new ArrayList<>();
                        newMapItemList.add(homePageItem);
                        homePageItemMap.put(groupId, newMapItemList);
                    }
                }
            }

            Set<String> keySet = homePageItemMap.keySet();
            Iterator<String> iter = keySet.iterator();

            int statusBarHeight = ScreenUtils.getStatusBarHeight();
            if (statusBarHeight == 0) {
                //如果没获取到，默认为25dp
                statusBarHeight = UnitUtils.dip2px(this, 25);
            }
            float screenHeight = ScreenUtils.getScreenHeight() - UnitUtils.dip2px(this, 45);
            float itemHeight = (screenHeight - statusBarHeight) / 3;   //一屏幕显示三行,多余的上滑显示
            RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) itemHeight);

            while (iter.hasNext()) {
                String key = iter.next();
                List<ChartHomePageItem> dataList = homePageItemMap.get(key);
                int itemCount = dataList.size();
                View groupForm = null;
                if (itemCount == 1) {
                    LinearLayout.LayoutParams itemLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) itemHeight);
                    groupForm = this.getLayoutInflater().inflate(R.layout.chart_layout_home_page_group1, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams1);

                    //按钮1
                    final ChartHomePageItem item1 = dataList.get(0);
                    RelativeLayout rl_item1 = (RelativeLayout) groupForm.findViewById(R.id.rl_item1);
                    String backgroud1 = item1.getBackground().replace(" ", "");
                    try {
                        rl_item1.setBackgroundColor(Color.parseColor(backgroud1));
                    } catch (Exception e) {
                        LogUtils.e("服务端配置的背景色错误");
                    }
                    ImageView iv_icon1 = (ImageView) groupForm.findViewById(R.id.iv_icon1);
                    GlideUtil.loadImage(this, item1.getModuleIcon(), iv_icon1);
                    TextView tv_name1 = (TextView) groupForm.findViewById(R.id.tv_name1);
                    tv_name1.setText(item1.getModuleName());
                    //点击事件
                    final String urlType1 = item1.getUrlType();
                    final String urlParam1 = item1.getUrlParam();
                    rl_item1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType1 != null && urlParam1 != null) {
                                openChartList(urlParam1, item1.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType1), urlParam1);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item1未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount = item1.getUnReadCount();
                    if (unReadCount != null && unReadCount > 0) {
                        RelativeLayout rl_unread_count_ = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count);
                        TextView tv_unread_count_ = (TextView) groupForm.findViewById(R.id.tv_unread_count);
                        tv_unread_count_.setText(String.valueOf(unReadCount));
                        rl_unread_count_.setVisibility(View.VISIBLE);
                    }
                } else if (itemCount == 2) {
                    groupForm = this.getLayoutInflater().inflate(R.layout.chart_layout_home_page_group2, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams);

                    //按钮1
                    final ChartHomePageItem item1 = dataList.get(0);
                    RelativeLayout rl_item1 = (RelativeLayout) groupForm.findViewById(R.id.rl_item1);
                    String backgroud1 = item1.getBackground().replace(" ", "");
                    try {
                        rl_item1.setBackgroundColor(Color.parseColor(backgroud1));
                    } catch (Exception e) {
                        LogUtils.e("服务端配置的背景色错误");
                    }
                    ImageView iv_icon1 = (ImageView) groupForm.findViewById(R.id.iv_icon1);
                    GlideUtil.loadImage(this, item1.getModuleIcon(), iv_icon1);
                    TextView tv_name1 = (TextView) groupForm.findViewById(R.id.tv_name1);
                    tv_name1.setText(item1.getModuleName());
                    //点击事件
                    final String urlType1 = item1.getUrlType();
                    final String urlParam1 = item1.getUrlParam();
                    rl_item1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType1 != null && urlParam1 != null) {
                                openChartList(urlParam1, item1.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType1), urlParam1);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item1未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount1 = item1.getUnReadCount();
                    if (unReadCount1 != null && unReadCount1 > 0) {
                        RelativeLayout rl_unread_count_1 = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count_1);
                        TextView tv_unread_count_1 = (TextView) groupForm.findViewById(R.id.tv_unread_count_1);
                        tv_unread_count_1.setText(String.valueOf(unReadCount1));
                        rl_unread_count_1.setVisibility(View.VISIBLE);
                    }

                    //按钮2
                    final ChartHomePageItem item2 = dataList.get(1);
                    RelativeLayout rl_item2 = (RelativeLayout) groupForm.findViewById(R.id.rl_item2);
                    String backgroud2 = item2.getBackground().replace(" ", "");
                    try {
                        rl_item2.setBackgroundColor(Color.parseColor(backgroud2));
                    } catch (Exception e) {
                        LogUtils.e("服务端配置的背景色错误");
                    }
                    ImageView iv_icon2 = (ImageView) groupForm.findViewById(R.id.iv_icon2);
                    GlideUtil.loadImage(this, item2.getModuleIcon(), iv_icon2);
                    TextView tv_name2 = (TextView) groupForm.findViewById(R.id.tv_name2);
                    tv_name2.setText(item2.getModuleName());
                    //点击事件
                    final String urlType2 = item2.getUrlType();
                    final String urlParam2 = item2.getUrlParam();
                    rl_item2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType2 != null && urlParam2 != null) {
                                openChartList(urlParam2, item2.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType2), urlParam2);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item2未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount2 = item2.getUnReadCount();
                    if (unReadCount2 != null && unReadCount2 > 0) {
                        RelativeLayout rl_unread_count_2 = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count_2);
                        TextView tv_unread_count_2 = (TextView) groupForm.findViewById(R.id.tv_unread_count_2);
                        tv_unread_count_2.setText(String.valueOf(unReadCount2));
                        rl_unread_count_2.setVisibility(View.VISIBLE);
                    }
                } else {    //最多只显示三项，配多了显示不出来
                    groupForm = this.getLayoutInflater().inflate(R.layout.chart_layout_home_page_group3, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams);
                    //按钮1
                    final ChartHomePageItem item1 = dataList.get(0);
                    RelativeLayout rl_item1 = (RelativeLayout) groupForm.findViewById(R.id.rl_item1);
                    String backgroud1 = item1.getBackground().replace(" ", "");
                    try {
                        rl_item1.setBackgroundColor(Color.parseColor(backgroud1));
                    } catch (Exception e) {
                        LogUtils.e("服务端配置的背景色错误");
                    }
                    ImageView iv_icon1 = (ImageView) groupForm.findViewById(R.id.iv_icon1);
                    GlideUtil.loadImage(this, item1.getModuleIcon(), iv_icon1);
                    TextView tv_name1 = (TextView) groupForm.findViewById(R.id.tv_name1);
                    tv_name1.setText(item1.getModuleName());
                    //点击事件
                    final String urlType1 = item1.getUrlType();
                    final String urlParam1 = item1.getUrlParam();
                    rl_item1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType1 != null && urlParam1 != null) {
                                openChartList(urlParam1, item1.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType1), urlParam1);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item1未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount1 = item1.getUnReadCount();
                    if (unReadCount1 != null && unReadCount1 > 0) {
                        RelativeLayout rl_unread_count_1 = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count_1);
                        TextView tv_unread_count_1 = (TextView) groupForm.findViewById(R.id.tv_unread_count_1);
                        tv_unread_count_1.setText(String.valueOf(unReadCount1));
                        rl_unread_count_1.setVisibility(View.VISIBLE);
                    }

                    //按钮2
                    final ChartHomePageItem item2 = dataList.get(1);
                    RelativeLayout rl_item2 = (RelativeLayout) groupForm.findViewById(R.id.rl_item2);
                    String backgroud2 = item2.getBackground().replace(" ", "");
                    if (backgroud2 != null) {
                        try {
                            rl_item2.setBackgroundColor(Color.parseColor(backgroud2));
                        } catch (Exception e) {
                            LogUtils.e("服务端配置的背景色错误");
                        }
                    }
                    ImageView iv_icon2 = (ImageView) groupForm.findViewById(R.id.iv_icon2);
                    GlideUtil.loadImage(this, item2.getModuleIcon(), iv_icon2);
                    TextView tv_name2 = (TextView) groupForm.findViewById(R.id.tv_name2);
                    tv_name2.setText(item2.getModuleName());
                    //点击事件
                    final String urlType2 = item2.getUrlType();
                    final String urlParam2 = item2.getUrlParam();
                    rl_item2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType2 != null && urlParam2 != null) {
                                openChartList(urlParam2, item2.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType2), urlParam2);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item2未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount2 = item2.getUnReadCount();
                    if (unReadCount2 != null && unReadCount2 > 0) {
                        RelativeLayout rl_unread_count_2 = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count_2);
                        TextView tv_unread_count_2 = (TextView) groupForm.findViewById(R.id.tv_unread_count_2);
                        tv_unread_count_2.setText(String.valueOf(unReadCount2));
                        rl_unread_count_2.setVisibility(View.VISIBLE);
                    }

                    //按钮3
                    final ChartHomePageItem item3 = dataList.get(2);
                    RelativeLayout rl_item3 = (RelativeLayout) groupForm.findViewById(R.id.rl_item3);
                    String backgroud3 = item3.getBackground().replace(" ", "");
                    try {
                        rl_item3.setBackgroundColor(Color.parseColor(backgroud3));
                    } catch (Exception e) {
                        LogUtils.e("服务端配置的背景色错误");
                    }
                    ImageView iv_icon3 = (ImageView) groupForm.findViewById(R.id.iv_icon3);
                    GlideUtil.loadImage(this, item3.getModuleIcon(), iv_icon3);
                    TextView tv_name3 = (TextView) groupForm.findViewById(R.id.tv_name3);
                    tv_name3.setText(item3.getModuleName());
                    //点击事件
                    final String urlType3 = item3.getUrlType();
                    final String urlParam3 = item3.getUrlParam();
                    rl_item3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (urlType3 != null && urlParam3 != null) {
                                openChartList(urlParam3, item3.getModuleName());
//                                ChartCustomActionParam customModel = new ChartCustomActionParam(Integer.valueOf(urlType3), urlParam3);
//                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
                            }
                        }
                    });
                    /**
                     * item3未读消息数量展示
                     * 20161117 by lvmenghui
                     */
                    Integer unReadCount3 = item3.getUnReadCount();
                    if (unReadCount3 != null && unReadCount3 > 0) {
                        RelativeLayout rl_unread_count_3 = (RelativeLayout) groupForm.findViewById(R.id.rl_unread_count_3);
                        TextView tv_unread_count_3 = (TextView) groupForm.findViewById(R.id.tv_unread_count_3);
                        tv_unread_count_3.setText(String.valueOf(unReadCount3));
                        rl_unread_count_3.setVisibility(View.VISIBLE);
                    }
                }
                mAppMainLlHomeItems.addView(groupForm);
            }
        }
    }

    private void openChartList(String moduleId, String title) {
        if (moduleId != null) {
            Intent intent = new Intent(mActivity, NormalChartListActivity.class);
            intent.putExtra(NormalChartListActivity.KEY_BUNDLE_MODULE_ID, moduleId);
            intent.putExtra(NormalChartListActivity.KEY_BUNDLE_TITLE, title);
            ActivityUtils.startActivity(intent);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.requestData(false);
        }
    }
}
