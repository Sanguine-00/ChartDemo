package com.mobcb.chart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.http.api.chart.ChartService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chart.ChartConstants;
import com.mobcb.chart.R;
import com.mobcb.chart.adapter.ChartListAdapter;
import com.mobcb.chart.bean.ChartListBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class NormalChartListActivity extends BaseActivity implements OnRefreshListener, AdapterView.OnItemClickListener {
    public static final String KEY_BUNDLE_MODULE_ID = "key.bundle.module.id";
    public static final String KEY_BUNDLE_TITLE = "key.bundle.title";
    private ListView mListView;
    private SmartRefreshLayout mRefreshLayout;
    private Activity mActivity;
    private ChartListAdapter adapter;
    private String moduleId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_normal_chart_list);
        initView();
        getModuleId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersionBarHelper.setDarkFont(mActivity, null);
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        mListView.setOnItemClickListener(this);
    }

    private void getModuleId() {
        if (getIntent() != null) {
            moduleId = getIntent().getStringExtra(KEY_BUNDLE_MODULE_ID);
            String title = getIntent().getStringExtra(KEY_BUNDLE_TITLE);
            if (title != null) {
                initTitle(title);
            } else {
                initTitle(getString(R.string.chart_list));
            }
            if (moduleId != null) {
                getData(true, moduleId);
            }
        }
    }

    private void stopRefresh() {
        mRefreshLayout.finishRefresh();//结束刷新
        mRefreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
    }

    private void getData(final boolean needLoading, final String moduleId) {
        if (needLoading) {
            showLoading();
        }
        AuthHelper.auth(ChartService.class)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ChartService>() {
                    @Override
                    public void call(ChartService chartService) {
                        Observable<ResponseBean> observable = chartService.getChartList(moduleId);// Ay6nDpVXq2tOdn3f
                        observable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<ResponseBean>() {
                                    @Override
                                    public void onCompleted() {
                                        hideLoading();

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        hideLoading();
                                        LogUtils.i(e);
                                        ToastUtils.showShort(R.string.chart_server_error);
                                        stopRefresh();
                                    }

                                    @Override
                                    public void onNext(ResponseBean responseBean) {
                                        hideLoading();
                                        stopRefresh();
                                        if (responseBean == null) {
                                            ToastUtils.showShort(R.string.chart_server_error);
                                            return;
                                        }
                                        if (ApiUtils.checkCode(mActivity, responseBean)) {
                                            List<ChartListBean> chartListBeans = ApiUtils.getDataList(responseBean, ChartListBean.class);
                                            mListView.setAdapter(adapter = new ChartListAdapter(chartListBeans, mActivity));
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData(false, moduleId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChartListBean chartListBean = adapter.getItem(position);
        if (chartListBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString(BaseChartActivity.KEY_BUNDLE_GROUP_NAME, chartListBean.getGroupTitle());
            bundle.putString(BaseChartActivity.KEY_BUNDLE_ID, chartListBean.getId());
            if (ChartConstants.CHART_DRAW_TYPE_OVERLAY.equalsIgnoreCase(chartListBean.getDisplayType())) {
                //用层叠的方式显示多张图,即一张图中绘制多个图的数据
                Intent intent = new Intent(mActivity, OverlayNormalChartActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (ChartConstants.CHART_DRAW_TYPE_TAB.equalsIgnoreCase(chartListBean.getDisplayType())) {
                //采用选项卡的方式显示多张图
                Intent intent = new Intent(mActivity, TabNormalChartActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
