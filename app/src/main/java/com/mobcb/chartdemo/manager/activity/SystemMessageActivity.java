package com.mobcb.chartdemo.manager.activity;

import android.os.Bundle;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.presenter.SystemMessagePresenter;
import com.umeng.analytics.MobclickAgent;

public class SystemMessageActivity extends BaseActivity<SystemMessagePresenter> implements BaseMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        ImmersionBarHelper.setDarkFont(this, null);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
