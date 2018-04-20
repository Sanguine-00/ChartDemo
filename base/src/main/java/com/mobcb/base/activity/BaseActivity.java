package com.mobcb.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mobcb.base.R;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.helper.ToolbarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.util.KeyboardUtils;

/**
 * Created by lvmenghui
 * on 2018/3/16.
 * 基础activity
 */

public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseMvpView {

    protected AppCompatActivity mActivity;
    protected KProgressHUD mLoadingDialog;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = BaseActivity.this;
        ImmersionBarHelper.init(mActivity);
        mLoadingDialog = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                //.setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtils.hideKeyboard(getWindow().getDecorView());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBarHelper.destory(mActivity);
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
    }

    protected void initTitle(String titleText) {
        ToolbarHelper.instance().init(mActivity, null)
                .setTitle(titleText)
                .setTitleColor(Color.BLACK)
                .setBackgroundColor(Color.TRANSPARENT)
                .setLeft(R.drawable.base_ic_black_back, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mActivity.finish();
                    }
                });
    }

    public void showLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

}
