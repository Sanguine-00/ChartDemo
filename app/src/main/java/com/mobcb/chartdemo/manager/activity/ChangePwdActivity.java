package com.mobcb.chartdemo.manager.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.util.ScreenUtils;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.presenter.ChangePwdPresenter;
import com.umeng.analytics.MobclickAgent;

public class ChangePwdActivity extends BaseActivity<ChangePwdPresenter> implements BaseMvpView, View.OnClickListener {


    //提交按钮
    private Button mAppChangeBtnSubmit;
    //原密码:
    private EditText mAppChangePwdEtOld;
    //新密码:
    private EditText mAppChangePwdEtNewOne;
    //确认密码:
    private EditText mAppChangePwdEtNewTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11) {
            setFinishOnTouchOutside(true);
        }
        Window w = getWindow();
        w.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_pwd);
        WindowManager.LayoutParams params = w.getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth() * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(params);
        initView();
        mPresenter = new ChangePwdPresenter(this);
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

    private void initView() {
        mAppChangePwdEtOld = (EditText) findViewById(R.id.app_change_pwd_et_old);
        mAppChangePwdEtNewOne = (EditText) findViewById(R.id.app_change_pwd_et_new_one);
        mAppChangePwdEtNewTwo = (EditText) findViewById(R.id.app_change_pwd_et_new_two);
        mAppChangeBtnSubmit = (Button) findViewById(R.id.app_change_btn_submit);
        mAppChangeBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.app_change_btn_submit:
                //提交修改密码
                String oldPwd = mAppChangePwdEtOld.toString();
                String newPwdOne = mAppChangePwdEtNewOne.toString();
                String newPwdTwo = mAppChangePwdEtNewTwo.toString();

                break;
        }
    }


}
