package com.mobcb.base.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

/**
 * 状态栏辅助类
 * Created by lvmenghui
 * on 2017/6/1.
 */

public class StatusBarHelper {

//    /**
//     * 设置状态栏透明
//     *
//     * @param activity
//     */
//    public static void setStatusBarTransparent(Activity activity) {
//        Window window = activity.getWindow();
//        window.requestFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//            //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //window.setStatusBarColor(Color.TRANSPARENT);
//            //window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    /**
//     * 设置状态栏颜色
//     *
//     * @param activity
//     */
//    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setStatusBarColor(color);
//        }
//    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusbarHeight(Context context) {
        int sbar = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                sbar = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sbar;
    }

    /**
     * 将View移动到顶端
     */
    public static void changeMargin(Activity activity, View view, int moveX) {

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams) layoutParams;
            int x = linearLayoutParams.topMargin;
            linearLayoutParams.topMargin = x + moveX;
        } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relativeLayoutParams = (RelativeLayout.LayoutParams) layoutParams;
            int x = relativeLayoutParams.topMargin;
            relativeLayoutParams.topMargin = x + moveX;
        } else {
            //暂时用的最多的是上面这两种布局，其它的待补充
        }
    }
}