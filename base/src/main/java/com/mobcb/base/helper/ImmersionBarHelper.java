package com.mobcb.base.helper;

import android.app.Activity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by lvmenghui
 * on 17/6/1.
 * 沉浸式状态栏辅助类
 */
public class ImmersionBarHelper {
    public static void setDarkFont(Activity mActivity, View statusView) {
        try {
            ImmersionBar immersionBar = ImmersionBar.with(mActivity);
            if (statusView != null) {
                immersionBar.statusBarView(statusView);
            }
            if (ImmersionBar.isSupportStatusBarDarkFont()) { //判断当前设备支不支持状态栏字体变色
                immersionBar.statusBarDarkFont(true);   //状态栏字体是深色，不写默认为亮色
            } else {
                // 设备不支持状态栏深色字体
                // 处理状态栏有透明度
                immersionBar.statusBarAlpha(0.5f);
            }
            immersionBar.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLightFont(Activity mActivity, View statusView) {
        try {
            ImmersionBar immersionBar = ImmersionBar.with(mActivity);
            if (statusView != null) {
                immersionBar.statusBarView(statusView);
            }
            immersionBar.statusBarDarkFont(false);
            immersionBar.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(Activity mActivity) {
        try {
            ImmersionBar.with(mActivity).transparentStatusBar().init();//初始化，默认透明状态栏和黑色导航栏
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destory(Activity mActivity) {
        try {
            ImmersionBar.with(mActivity).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
