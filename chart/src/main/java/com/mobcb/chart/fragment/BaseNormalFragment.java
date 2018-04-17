package com.mobcb.chart.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mobcb.base.http.api.chart.ChartService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartBean;
import com.mobcb.chart.bean.ChartDetailBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Sanguine on 2018/4/4.
 */

public class BaseNormalFragment extends Fragment {
    public static final String KEY_BUNDLE_CHART_LIST = "BUNDLE_CHART_LIST";
    private static final String TAG = BaseNormalFragment.class.getSimpleName();
    protected View mRoot;
    protected String ids = "";
    protected Activity mActivity;
    protected boolean isVisibleToUser = false;


    protected KProgressHUD mLoadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingDialog = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                //.setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    protected void getArg() {
        if (getArguments() != null) {
            ids = getArguments().getString("ids", "");
        }
    }

    /**
     * @param rules   时间筛选,如果为空,则不筛选
     * @param childId 子图的id,如果是tab,单独刷新,则需要用子图id
     */
    protected void getChartDetail(@Nullable final List<Map<String, String>> rules, @Nullable final String childId) {
        showLoading();
        AuthHelper.auth(ChartService.class)
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<ChartService>() {
                    @Override
                    public void call(ChartService chartService) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", ids);
                        if (rules != null) {
                            map.put("rules", rules);
                        }
                        if (childId != null) {
                            map.put("childId", childId);
                        }
                        Observable<ResponseBean> observable = chartService.getChart(map);
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
                                        e.printStackTrace();
                                        ToastUtils.showShort(R.string.chart_server_error);
                                    }

                                    @Override
                                    public void onNext(ResponseBean responseBean) {
                                        hideLoading();
                                        if (responseBean == null) {
                                            ToastUtils.showShort(R.string.chart_server_error);
                                            return;
                                        }
                                        if (ApiUtils.checkCode(mActivity, responseBean)) {
                                            ChartBean chartBean = ApiUtils.getData(responseBean, ChartBean.class);
                                            if (chartBean != null) {
                                                List<ChartDetailBean> chartList = chartBean.getChartList();
                                                dealWithChartBean(chartList);
                                            }
                                        }
                                    }
                                });
                    }
                });
    }

    protected void getChartDetail() {
        if (getArguments() != null) {
            ArrayList<ChartDetailBean> chartList = getArguments().getParcelableArrayList(KEY_BUNDLE_CHART_LIST);
            dealWithChartBean(chartList);
        }
    }

    protected void dealWithChartBean(List<ChartDetailBean> chartList) {

    }

    private void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    private void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }


    protected ArrayList<Integer> getColors(int index) {

        ArrayList<Integer> colors = new ArrayList<Integer>();

        switch (index) {
            case 0:
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);

                break;
            case 1:
                for (int c : ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);

                break;
            case 2:
                for (int c : ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);

                break;
            case 3:
                for (int c : ColorTemplate.COLORFUL_COLORS)
                    colors.add(c);

                break;
            case 4:
                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);
                break;
            case 5:
                colors.add(ColorTemplate.getHoloBlue());
                break;
            default:
                for (int c : ColorTemplate.MATERIAL_COLORS) {
                    colors.add(c);
                }
                break;
        }

        return colors;
    }
}
