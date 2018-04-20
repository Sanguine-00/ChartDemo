package com.mobcb.base.helper;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobcb.base.R;

/**
 * Created by lvmenghui
 * on 2018/3/16.
 * 标题栏辅助类
 */

public class ToolbarHelper {
    private static ToolbarHelper mToolbarHelper;

    private View top_view;
    private LinearLayout title_view;
    private TextView tv_toolbar_title;
    private ImageView iv_toolbar_left_icon;
    private ImageView iv_toolbar_right_icon;
    private LinearLayout ll_toolbar_shadow;

    public static ToolbarHelper instance() {
        if (mToolbarHelper == null) {
            mToolbarHelper = new ToolbarHelper();
        }
        return mToolbarHelper;
    }


    public ToolbarHelper init(Activity activity) {
        return init(activity, null);
    }

    public ToolbarHelper init(Activity activity, View view) {
        if (view == null) {
            top_view = (View) activity.findViewById(R.id.top_view);
            title_view = (LinearLayout) activity.findViewById(R.id.title_view);
            tv_toolbar_title = (TextView) activity.findViewById(R.id.tv_toolbar_title);
            iv_toolbar_left_icon = (ImageView) activity.findViewById(R.id.iv_toolbar_left_icon);
            iv_toolbar_right_icon = (ImageView) activity.findViewById(R.id.iv_toolbar_right_icon);
            ll_toolbar_shadow = (LinearLayout) activity.findViewById(R.id.ll_toolbar_shadow);
        } else {
            top_view = (View) view.findViewById(R.id.top_view);
            title_view = (LinearLayout) view.findViewById(R.id.title_view);
            tv_toolbar_title = (TextView) view.findViewById(R.id.tv_toolbar_title);
            iv_toolbar_left_icon = (ImageView) view.findViewById(R.id.iv_toolbar_left_icon);
            iv_toolbar_right_icon = (ImageView) view.findViewById(R.id.iv_toolbar_right_icon);
            ll_toolbar_shadow = (LinearLayout) view.findViewById(R.id.ll_toolbar_shadow);
        }
        //设置状态栏间距
        if (top_view != null) {
            int statusBarHeight = StatusBarHelper.getStatusbarHeight(activity);
            top_view.getLayoutParams().height = statusBarHeight;
        }
        hideRight();
        hideShadow();
        return mToolbarHelper;
    }

    /**
     * 设置标题文字及颜色
     *
     * @param title
     * @param color
     * @return
     */
    public ToolbarHelper setTitleAndTitleColor(String title, @ColorInt int color) {
        if (tv_toolbar_title != null) {
            tv_toolbar_title.setText(title);
            tv_toolbar_title.setTextColor(color);
        }
        return mToolbarHelper;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public ToolbarHelper setTitleColor(@ColorInt int color) {
        if (tv_toolbar_title != null) {
            tv_toolbar_title.setTextColor(color);
        }
        return mToolbarHelper;
    }

    /**
     * 设置标题
     *
     * @param titleText
     * @return
     */
    public ToolbarHelper setTitle(String titleText) {
        if (tv_toolbar_title != null) {
            tv_toolbar_title.setText(titleText);
        }
        return mToolbarHelper;
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param color
     * @return
     */
    public ToolbarHelper setBackgroundColor(@ColorInt int color) {
        if (title_view != null) {
            title_view.setBackgroundColor(color);
        }
        return mToolbarHelper;
    }

    /**
     * 设置左边图标及点击事件
     *
     * @param resId
     * @param listener
     * @return
     */
    public ToolbarHelper setLeft(int resId, View.OnClickListener listener) {
        if (iv_toolbar_left_icon != null) {
            iv_toolbar_left_icon.setImageResource(resId);
            iv_toolbar_left_icon.setOnClickListener(listener);
        }
        return mToolbarHelper;
    }


    /**
     * 设置右边图标及点击事件
     *
     * @param resId
     * @param listener
     * @return
     */
    public ToolbarHelper setRight(int resId, View.OnClickListener listener) {
        if (iv_toolbar_right_icon != null) {
            iv_toolbar_right_icon.setImageResource(resId);
            iv_toolbar_right_icon.setOnClickListener(listener);
            iv_toolbar_right_icon.setVisibility(View.VISIBLE);
        }
        return mToolbarHelper;
    }

    /**
     * 隐藏右边图标
     *
     * @return
     */
    public ToolbarHelper hideRight() {
        if (iv_toolbar_right_icon != null) {
            iv_toolbar_right_icon.setVisibility(View.GONE);
        }
        return mToolbarHelper;
    }

    /**
     * 显示底部阴影
     *
     * @return
     */
    public ToolbarHelper showShadow() {
        if (ll_toolbar_shadow != null) {
            ll_toolbar_shadow.setVisibility(View.VISIBLE);
        }
        return mToolbarHelper;
    }

    /**
     * 隐藏底部阴影
     *
     * @return
     */
    public ToolbarHelper hideShadow() {
        if (ll_toolbar_shadow != null) {
            ll_toolbar_shadow.setVisibility(View.GONE);
        }
        return mToolbarHelper;
    }


    /**
     * 隐藏topView
     *
     * @return
     */
    public ToolbarHelper hideTopView() {
        if (top_view != null) {
            top_view.setVisibility(View.GONE);
        }
        return mToolbarHelper;
    }
}
