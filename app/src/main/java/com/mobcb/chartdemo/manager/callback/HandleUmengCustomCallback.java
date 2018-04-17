package com.mobcb.chartdemo.manager.callback;


import com.mobcb.chartdemo.manager.bean.umeng.AppCustomActionParam;

/**
 * Created by wanggh on 2015/7/14.
 */
public interface HandleUmengCustomCallback {
	public void handleEnd();
	public void needLogin(AppCustomActionParam customModel);
}
