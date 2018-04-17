package com.mobcb.chart.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.http.api.chart.ChartService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class BaseChartActivity extends BaseActivity {

    public static final String KEY_BUNDLE_GROUP_NAME = "BUNDLE_GROUP_NAME";
    public static final String KEY_BUNDLE_ID = "BUNDLE_ID";
    protected String ids;
    protected String groupTitle;
    protected Activity mActivity;
    protected String dateFormat;//时间格式;
    protected boolean[] chooseTimeType = new boolean[6];
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    protected TextView mChartTvTimeStart;//开始时间
    protected TimePickerView startTimePickerView;
    protected TextView mChartTvTimeEnd;//结束时间
    protected TimePickerView endTimePickerView;
    protected Button mChartBtnTimeSelect;//筛选按钮
    protected LinearLayout mChartLlTimeSelect;//时间选择的根布局



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        getArgument();
    }

    private void initStartTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013,0,1);
        Calendar endDate = Calendar.getInstance();
//        endDate.set(2020,1,1);
        startTimePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mChartTvTimeStart.setText(simpleDateFormat.format(date));
            }
        })
                .setType(chooseTimeType)
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setCancelText(getString(R.string.chart_cancel))//取消按钮文字
//                .setSubmitText(getString(R.string.chart_sure))//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText(getString(R.string.chart_time_start))//标题文字
//                .isCyclic(true)//是否循环滚动
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate,endDate)//起始终止年月日设定
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setLabel(getString(R.string.chart_year),
//                        getString(R.string.chart_month),
//                        getString(R.string.chart_day),
//                        getString(R.string.chart_hour),
//                        getString(R.string.chart_min),
//                        getString(R.string.chart_sec))//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
                .build();
    }

    private void initEndTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013,0,1);
        Calendar endDate = Calendar.getInstance();
        endTimePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mChartTvTimeEnd.setText(simpleDateFormat.format(date));
            }
        })
                .setType(chooseTimeType)
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .setCancelText(getString(R.string.chart_cancel))//取消按钮文字
//                .setSubmitText(getString(R.string.chart_sure))//确认按钮文字
//                .setContentTextSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText(getString(R.string.chart_time_end))//标题文字
//                .isCyclic(true)//是否循环滚动
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate,endDate)//起始终止年月日设定
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setLabel(getString(R.string.chart_year),
//                        getString(R.string.chart_month),
//                        getString(R.string.chart_day),
//                        getString(R.string.chart_hour),
//                        getString(R.string.chart_min),
//                        getString(R.string.chart_sec))//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
                .build();
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

    protected void getArgument() {
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
                                            dealWithChartBean(chartBean);
                                        }
                                    }
                                });
                    }
                });
    }

    protected void dealWithChartBean(ChartBean chartBean) {
    }

    /**
     * 设置可筛选时间的
     */
    protected void setSelectable() {
        ToolbarHelper.instance().init(mActivity).setRight(R.drawable.chart_icon_setting, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChartLlTimeSelect != null) {
                    if (mChartLlTimeSelect.getVisibility() == View.VISIBLE) {
                        mChartLlTimeSelect.setVisibility(View.GONE);
                    } else {
                        mChartLlTimeSelect.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        simpleDateFormat.applyPattern(dateFormat);
        mChartTvTimeStart.setText(simpleDateFormat.format(new Date()));
        mChartTvTimeEnd.setText(simpleDateFormat.format(new Date()));
        chooseTimeType[0] = dateFormat.contains("yyyy") || dateFormat.contains("YYYY");
        chooseTimeType[1] = dateFormat.contains("MM");
        chooseTimeType[2] = dateFormat.contains("dd") || dateFormat.contains("DD");
        chooseTimeType[3] = dateFormat.contains("HH") || dateFormat.contains("hh");
        chooseTimeType[4] = dateFormat.contains("mm");
        chooseTimeType[5] = dateFormat.contains("ss") || dateFormat.contains("SS");
        initStartTime();
        initEndTime();
    }

    /**
     * 设置不能筛选时间的
     */
    protected void setUnSelectable() {
        ToolbarHelper.instance().init(mActivity).hideRight();
        if (mChartLlTimeSelect != null) {
            mChartLlTimeSelect.setVisibility(View.GONE);
        }
    }

}
