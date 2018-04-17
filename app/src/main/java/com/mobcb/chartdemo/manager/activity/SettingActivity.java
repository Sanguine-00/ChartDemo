package com.mobcb.chartdemo.manager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.CacheHelper;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.util.GlideUtil;
import com.mobcb.base.view.MyImageView;
import com.mobcb.dialog.DialogTwo;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.presenter.SettingPresenter;
import com.umeng.analytics.MobclickAgent;

public class SettingActivity extends BaseActivity<SettingPresenter> implements BaseMvpView, View.OnClickListener {

    //头像
    private MyImageView mAppSettingIvHeadImg;
    //点击更换头像
    private RelativeLayout mAppSettingRlHeadImg;
    //修改密码
    private TextView mAppSettingTvChangePassword;
    //检测更新
    private TextView mAppSettingTvCheckUpdate;
    //缓存数量
    private TextView mAppSettingTvCache;
    //清除缓存
    private RelativeLayout mAppSettingRlClearCache;
    //退出登录
    private TextView mAppSettingTvExitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        showData();
        showCacheData();
        initTitle(getString(R.string.app_setting_title));
        ToolbarHelper.instance().showShadow();
        mPresenter = new SettingPresenter(this);
    }

    private void initView() {
        mAppSettingIvHeadImg = (MyImageView) findViewById(R.id.app_setting_iv_head_img);
        mAppSettingRlHeadImg = (RelativeLayout) findViewById(R.id.app_setting_rl_head_img);
        mAppSettingRlHeadImg.setOnClickListener(this);
        mAppSettingTvChangePassword = (TextView) findViewById(R.id.app_setting_tv_change_password);
        mAppSettingTvChangePassword.setOnClickListener(this);
        mAppSettingTvCheckUpdate = (TextView) findViewById(R.id.app_setting_tv_check_update);
        mAppSettingTvCheckUpdate.setOnClickListener(this);
        mAppSettingTvCache = (TextView) findViewById(R.id.app_setting_tv_cache);
        mAppSettingRlClearCache = (RelativeLayout) findViewById(R.id.app_setting_rl_clear_cache);
        mAppSettingRlClearCache.setOnClickListener(this);
        mAppSettingTvExitLogin = (TextView) findViewById(R.id.app_setting_tv_exit_login);
        mAppSettingTvExitLogin.setOnClickListener(this);
    }

    private void showData() {
        String managerHeadImg = LoginHelper.getInstance().getManagerHeadImg();
        if (!TextUtils.isEmpty(managerHeadImg)) {
            GlideUtil.loadImage(mActivity, managerHeadImg, mAppSettingIvHeadImg);
        }
    }

    public void showCacheData() {
        String cache = CacheHelper.getTotalCacheSize(mActivity);
        if (!TextUtils.isEmpty(cache)) {
            mAppSettingTvCache.setText(cache);
        } else {
            mAppSettingTvCache.setText("0.00k");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.app_setting_rl_head_img:
                //更换头像
                break;
            case R.id.app_setting_tv_change_password:
                //修改密码
                if (mPresenter != null) {
                    mPresenter.changePwd();
                }
                break;
            case R.id.app_setting_tv_check_update:
                //检查更新
                break;
            case R.id.app_setting_rl_clear_cache:
                //清除缓存
                showClearCacheDialog();
                break;
            case R.id.app_setting_tv_exit_login:
                //退出登录
                showExitLoginDialog();
                break;
        }
    }

    /**
     * 清除缓存提示框
     */
    private void showClearCacheDialog() {
        final DialogTwo clearCache = new DialogTwo(this)
                .setContent(getString(R.string.app_setting_clear_cache_tip))
                .setLeftButton(getString(R.string.app_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                )
                .setRightButton(getString(R.string.app_make_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPresenter != null) {
                            mPresenter.clearCache(mActivity);
                        }
                    }
                });
        clearCache.show();

    }


    private void showExitLoginDialog() {
        final DialogTwo sa = new DialogTwo(this)
                .setContent(getString(R.string.app_setting_exit_login_tip))
                .setLeftButton(getString(R.string.app_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ).setRightButton(getString(R.string.app_make_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPresenter != null) {
                            mPresenter.exitLogin();
                        }
                    }
                });
        sa.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ImmersionBarHelper.setDarkFont(this, null);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
