package com.mobcb.chartdemo.manager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.AppUtils;
import com.mobcb.base.util.GlideUtil;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.ScreenUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.base.util.UnitUtils;
import com.mobcb.base.view.MyImageView;
import com.mobcb.dialog.DialogTwo;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.bean.main.HomePageItem;
import com.mobcb.chartdemo.manager.bean.umeng.AppCustomActionParam;
import com.mobcb.chartdemo.manager.helper.UmengCustomClickHelper;
import com.mobcb.chartdemo.manager.presenter.MainPresenter;
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

public class MainActivity extends BaseActivity<MainPresenter> implements BaseMvpView, OnRefreshListener {

    //头像
    private MyImageView mAppMainHeadImg;
    //姓名
    private TextView mAppMainTvName;

    private LinearLayout mAppMainLlHomeItems;
    private SmartRefreshLayout mAppMainRefreshLayout;
    private RelativeLayout mAppMainUserInfo;
    //模块未读消息数据
    private Map<String, Integer> unReadMap;
    private String mPageName = "MainActivity";
    private TextView mAppMainTvUnreadCount;//未读消息数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showManagerInfo();
        initTitle(getString(R.string.app_name));
        mPresenter = new MainPresenter(this);

    }


    @Override
    protected void initTitle(String titleText) {
        ToolbarHelper.instance().init(mActivity, null)
                .setTitle(titleText)
                .setTitleColor(Color.BLACK)
                .setBackgroundColor(Color.TRANSPARENT)
                .setLeft(R.drawable.app_icon_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityUtils.startActivity(SettingActivity.class);
                    }
                })
                .setRight(R.drawable.app_icon_system_msg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("系统消息界面");
                    }
                });

    }

    private void initView() {
        mAppMainHeadImg = (MyImageView) findViewById(R.id.app_main_head_img);
        mAppMainTvName = (TextView) findViewById(R.id.app_main_tv_name);
        mAppMainLlHomeItems = (LinearLayout) findViewById(R.id.app_main_ll_home_items);
        mAppMainRefreshLayout = (SmartRefreshLayout) findViewById(R.id.app_main_refresh_layout);

        mAppMainRefreshLayout.setEnableLoadMore(false);
        mAppMainRefreshLayout.setOnRefreshListener(this);
        mAppMainUserInfo = (RelativeLayout) findViewById(R.id.app_main_user_info);
        mAppMainTvUnreadCount = (TextView) findViewById(R.id.app_main_tv_unread_count);
    }

    private void showManagerInfo() {
        String header = LoginHelper.getInstance().getManagerHeadImg();
        String name = LoginHelper.getInstance().getManagerName();
        mAppMainTvName.setText(name);
        if (!TextUtils.isEmpty(header)) {
            Glide.with(mActivity).load(header).into(mAppMainHeadImg);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            mPresenter.requestData(false);
        }
    }

    public void endRefresh() {
        if (mAppMainRefreshLayout != null) {
            mAppMainRefreshLayout.finishRefresh();
        }
    }


    public void showData(List<HomePageItem> itemList) {
        if (itemList != null) {
            //为null时说明获取失败，不删除原菜单
            mAppMainLlHomeItems.removeAllViews();
        }
        if (itemList != null && itemList.size() > 0) {
            /**
             * 比對未读消息数据，将未读消息数据赋值到homePageItem中
             */
            for (int i = 0; i < itemList.size(); i++) {
                HomePageItem homePageItem = itemList.get(i);
                if (homePageItem != null && !TextUtils.isEmpty(homePageItem.getUrlParam())) {
                    if (unReadMap != null) {
                        if (unReadMap.get(homePageItem.getUrlParam()) != null) {
                            homePageItem.setUnReadCount(unReadMap.get(homePageItem.getUrlParam()));
                        }
                    }
                }
            }

            Comparator<HomePageItem> c = new Comparator<HomePageItem>() {
                @Override
                public int compare(HomePageItem item1, HomePageItem item2) {
                    Integer order1 = item1.getOrderNo();
                    Integer order2 = item2.getOrderNo();
                    return order1.compareTo(order2);
                }
            };
            Collections.sort(itemList, c);

            Map<String, List<HomePageItem>> homePageItemMap = new TreeMap<>(
                    new Comparator<String>() {
                        public int compare(String key1, String key2) {
                            // 升序排序
                            return key1.compareTo(key2);
                        }
                    });
            for (HomePageItem homePageItem : itemList) {
                if (homePageItem == null ||
                        "4".equalsIgnoreCase(homePageItem.getUrlType())) {//4是图表,不显示在首页中
                    continue;
                }
                String groupId = homePageItem.getGroupId();
                List<HomePageItem> mapItemList = homePageItemMap.get(groupId);
                if (mapItemList != null && mapItemList.size() <= 3) {
                    mapItemList.add(homePageItem);
                } else {
                    List<HomePageItem> newMapItemList = new ArrayList<>();
                    newMapItemList.add(homePageItem);
                    homePageItemMap.put(groupId, newMapItemList);
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
            float topFormHeight = mAppMainUserInfo.getHeight();
            float itemHeight = (screenHeight - statusBarHeight - topFormHeight) / 3;   //一屏幕显示三行,多余的上滑显示
            RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) itemHeight);

            while (iter.hasNext()) {
                String key = iter.next();
                List<HomePageItem> dataList = homePageItemMap.get(key);
                int itemCount = dataList.size();
                View groupForm = null;
                if (itemCount == 1) {
                    LinearLayout.LayoutParams itemLayoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) itemHeight);
                    groupForm = this.getLayoutInflater().inflate(R.layout.app_layout_home_page_group1, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams1);

                    //按钮1
                    HomePageItem item1 = dataList.get(0);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType1), urlParam1);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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
                    groupForm = this.getLayoutInflater().inflate(R.layout.app_layout_home_page_group2, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams);

                    //按钮1
                    HomePageItem item1 = dataList.get(0);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType1), urlParam1);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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
                    HomePageItem item2 = dataList.get(1);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType2), urlParam2);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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
                    groupForm = this.getLayoutInflater().inflate(R.layout.app_layout_home_page_group3, null);
                    LinearLayout ll_group = (LinearLayout) groupForm.findViewById(R.id.ll_group);
                    ll_group.setLayoutParams(itemLayoutParams);
                    //按钮1
                    HomePageItem item1 = dataList.get(0);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType1), urlParam1);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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
                    HomePageItem item2 = dataList.get(1);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType2), urlParam2);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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
                    HomePageItem item3 = dataList.get(2);
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
                                AppCustomActionParam customModel = new AppCustomActionParam(Integer.valueOf(urlType3), urlParam3);
                                new UmengCustomClickHelper().dealWithCustomAction(MainActivity.this, customModel);
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


    public void showUnReadCount(Integer count) {
        if (count != null) {
            if (0 < count) {
                String countStr = "";
                if (count > 99) {
                    countStr = "99+";
                } else {
                    countStr = String.valueOf(count);
                }
                mAppMainTvUnreadCount.setVisibility(View.VISIBLE);
                mAppMainTvUnreadCount.setText(countStr);

            } else {
                mAppMainTvUnreadCount.setVisibility(View.GONE);
            }
        } else {
            mAppMainTvUnreadCount.setVisibility(View.GONE);
        }
    }

    private void showExitAppDialog() {
        final DialogTwo exitDialog = new DialogTwo(mActivity);
        exitDialog.setContent(getString(R.string.app_exit_tip));
        exitDialog.setLeftButton(getString(R.string.app_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }
        );
        exitDialog.setRightButton(getString(R.string.app_make_sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.exitApp();
            }
        });
        exitDialog.setCancelable(true);
        exitDialog.setCanceledOnTouchOutside(true);
        exitDialog.show();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitAppDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
