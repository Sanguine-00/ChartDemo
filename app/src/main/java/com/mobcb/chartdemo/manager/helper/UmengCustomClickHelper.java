package com.mobcb.chartdemo.manager.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mobcb.base.helper.LoginHelper;
import com.mobcb.chart.activity.ChartMainActivity;
import com.mobcb.chartdemo.manager.activity.MainActivity;
import com.mobcb.chartdemo.manager.bean.umeng.AppCustomActionParam;
import com.mobcb.chartdemo.manager.bean.umeng.AppKeyValue;
import com.mobcb.chartdemo.manager.callback.HandleUmengCustomCallback;

import java.util.List;

public class UmengCustomClickHelper {
    public static final String EXTRA_NAME = "custom";
    final String TAG = "UmengCustomClickHelper";
    Context context;
    AppCustomActionParam customModel;
    HandleUmengCustomCallback callback;
    private boolean isFromMessage = false;

    public void dealWithCustomAction(Context context,
                                     AppCustomActionParam customModel) {
        dealWithCustomAction(context, customModel, null, false);
    }

    public void dealWithCustomAction(Context context,
                                     AppCustomActionParam customModel, HandleUmengCustomCallback callback) {
        dealWithCustomAction(context, customModel, callback, false);
    }

    public void dealWithCustomAction(Context context,
                                     AppCustomActionParam customModel, HandleUmengCustomCallback callback, boolean isFromMessage) {
        this.context = context;
        this.customModel = customModel;
        this.isFromMessage = isFromMessage;
        this.callback = callback;
        if (this.callback != null) {
            this.callback.handleEnd();
        }
        try {
            if (customModel != null) {
                int type = customModel.getType();
                Object param = customModel.getParam();
                List<AppKeyValue> keyValues = customModel.getExtra();
                switch (type) {
                    case 1:
                        launchApp();
                        break;
                    case 2:
                        launchAppBrowser(param);
                        break;
                    case 3:
                        launchAppActivity(param, keyValues);
                        break;
                    case 4:
                        // do nothing
                        break;

                    default:
                        launchApp();
                        break;
                }
            } else {
                launchApp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开APP首页
     */
    private void launchApp() {
        if (!isFromMessage) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    /**
     * 打开APP内嵌浏览器
     *
     * @param obj
     */
    private void launchAppBrowser(Object obj) {
        if (obj != null) {
            String url = obj.toString();//context, BrowserActivity.class
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("url", url);
            intent.putExtra("canchangetitle", true);
            context.startActivity(intent);
        } else {
            launchApp();
        }
    }

    /**
     * 打开APP页面
     *
     * @param obj
     */
    private void launchAppActivity(Object obj, List<AppKeyValue> keyValues) {
        if (obj != null) {
            try {
                int activityType = (int) Math.round(Float.valueOf(obj.toString()));
                Log.i(TAG, "launchAppActivity:" + activityType);
                Intent intent = new Intent();
                switch (activityType) {
                    case 201:
                        //上班签到
                        context.startActivity(intent);
                        break;
                    case 202:
                        //物业报修
                        intent.setClass(context, ChartMainActivity.class);
                        context.startActivity(intent);
                        break;
                    case 203:
                        //通知公告
                        context.startActivity(intent);
                        break;
                    case 204:
                        //现场管理
                        context.startActivity(intent);
                        break;
                    case 205:
                        //实时监控
                        context.startActivity(intent);
                        break;
                    case 206:
                        //运营报表
                        //  ToastHelper.showToastShort(context, "运营报表");
                        context.startActivity(intent);
                        break;
                    case 207:
                        //流程审批
                        break;
                    case 208:
                        //商管培训
                        context.startActivity(intent);
                        break;
                    case 209:
                        //在岗查询
                        context.startActivity(intent);
                        break;
                    case 210:
                        //预约服务

                        break;
                    case 211:
                        //投诉处理
                        context.startActivity(intent);
                        break;
                    case 212:
                        //在线咨询
                        context.startActivity(intent);
                        break;
                    case 213:
                        //系统消息
                        context.startActivity(intent);
                        break;
                    case 214:
                        //有单退货授权
                        context.startActivity(intent);
                        break;
                    case 215:
                        //无单退货授权
                        context.startActivity(intent);
                        break;
                    case 216:
                        //小票重印授权
                        context.startActivity(intent);
                        break;
                    case 217:
                        //后场巡更
                        context.startActivity(intent);
                        break;
                    case 218:
                        //在岗问答
                        context.startActivity(intent);
                        break;
                    case 225:
                        //提货核销记录
                        context.startActivity(intent);
                        break;
                    case 224:
                        //提货核销
                        context.startActivity(intent);
                        break;
                    case 223:
                        //优惠券核销记录
                        context.startActivity(intent);
                        break;
                    case 222:
                        //优惠券核销
                        context.startActivity(intent);
                        break;
                    case 221:
                        //优惠券发放
                        context.startActivity(intent);
                        break;
                    case 220:
                        //停车券发放记录
                        context.startActivity(intent);
                        break;
                    case 219:
                        //停车券发放
                        context.startActivity(intent);
                        break;
                    case 226:
                        //小票重印授权
                        context.startActivity(intent);
                        break;
                    case 227:
                        //圈子
                        context.startActivity(intent);
                        break;
                    case 228:
                        //kpi报表
                        context.startActivity(intent);
                        break;

                    /**
                     * 2016-12-05 张高强增加 设备配置授权
                     */
                    case 229:
                        //设备配置授权
                        context.startActivity(intent);
                        break;
                    case 230:
                        //直播
                        break;
                    case 231:
                        //小蜜蜂
                        context.startActivity(intent);
                        break;
                    case 232:
                        //优惠券发放记录
                        context.startActivity(intent);

                        break;

                    case 240:
                        //图表首页
                        intent.setClass(context, ChartMainActivity.class);
                        context.startActivity(intent);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                launchApp();
            }
        } else {
            launchApp();
        }
    }

    public boolean isLogin() {
        LoginHelper mLoginHelper = LoginHelper.getInstance();
        if (mLoginHelper.isLogin()) {
            return true;
        } else {
            if (this.callback != null) {
                this.callback.needLogin(customModel);
            }
            return false;
        }
    }


}
