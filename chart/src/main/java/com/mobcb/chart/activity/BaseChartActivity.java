package com.mobcb.chart.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mobcb.base.activity.BaseActivity;
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
    protected ToolbarHelper mToolbarHelper;//标题控制器


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        getArgument();
    }

    private void initStartTime() {
        if (startTimePickerView != null) {
            return;
        }
        Calendar chosen = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 1);
        Calendar endDate = Calendar.getInstance();
        startTimePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mChartTvTimeStart.setText(simpleDateFormat.format(date));
            }
        })
                .setType(chooseTimeType)
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(chosen)
                .setTitleBgColor(getResources().getColor(R.color.chart_title_bg))
                .setCancelColor(getResources().getColor(R.color.chart_text))
                .setSubmitColor(getResources().getColor(R.color.chart_text))
                .setBgColor(getResources().getColor(R.color.chart_bg))
                .setTextColorCenter(getResources().getColor(R.color.chart_text))
                .setTitleColor(getResources().getColor(R.color.chart_text))
                .setTitleText(getResources().getString(R.string.chart_time_start))
                .setTitleSize(12)
                .setContentTextSize(12)
                .setSubCalSize(12)
                .setLineSpacingMultiplier(2f)
                .build();
    }

    private void initEndTime() {
        if (endTimePickerView != null) {
            return;
        }
        Calendar chosen = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endTimePickerView = new TimePickerBuilder(mActivity, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mChartTvTimeEnd.setText(simpleDateFormat.format(date));
            }
        })
                .setType(chooseTimeType)
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(chosen)
                .setTitleBgColor(getResources().getColor(R.color.chart_title_bg))
                .setCancelColor(getResources().getColor(R.color.chart_text))
                .setSubmitColor(getResources().getColor(R.color.chart_text))
                .setBgColor(getResources().getColor(R.color.chart_bg))
                .setTextColorCenter(getResources().getColor(R.color.chart_text))
                .setTitleColor(getResources().getColor(R.color.chart_text))
                .setTitleText(getResources().getString(R.string.chart_time_end))
                .setTitleSize(12)
                .setContentTextSize(12)
                .setSubCalSize(12)
                .build();
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
                                        if (ApiUtils.checkCode(responseBean)) {
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

    protected void checkSelectable() {
        if (!TextUtils.isEmpty(dateFormat)) {
            setSelectable();
        } else {
            setUnSelectable();
        }
    }

    /**
     * 设置可筛选时间的
     */
    protected void setSelectable() {
        if (mToolbarHelper != null) {
            mToolbarHelper.setRight(R.drawable.chart_icon_title_setting, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChartLlTimeSelect != null) {
                        if (mChartLlTimeSelect.getVisibility() == View.VISIBLE) {
                            mChartLlTimeSelect.setVisibility(View.GONE);
                            if (mToolbarHelper != null) {
                                mToolbarHelper.hideShadow();
                            }
                        } else {
                            if (mToolbarHelper != null) {
                                mToolbarHelper.showShadow();
                            }
                            mChartLlTimeSelect.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

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
        if (mToolbarHelper != null) {
            mToolbarHelper.hideRight();
        }
        if (mChartLlTimeSelect != null) {
            mChartLlTimeSelect.setVisibility(View.GONE);
        }
    }

}
