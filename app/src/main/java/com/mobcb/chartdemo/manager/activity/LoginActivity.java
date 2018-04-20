package com.mobcb.chartdemo.manager.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.helper.AndroidBug5497Workaround;
import com.mobcb.base.helper.ImmersionBarHelper;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.presenter.LoginPresenter;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends BaseActivity<LoginPresenter> implements BaseMvpView, View.OnClickListener {

    private final String mPageName = "LoginActivity";
    /**
     * 请输入您的账号
     */
    private EditText mAppEtLoginUserName;
    /**
     * 请输入您的密码
     */
    private EditText mAppEtLoginUserPwd;
    /**
     * 请输入验证码
     */
    private EditText mAppEtLoginCode;
    private ImageView mAppIvCode;
    /**
     * 登录
     */
    private Button mAppLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mPresenter = new LoginPresenter(this);
        AndroidBug5497Workaround.assistActivity(this);
    }

    private void initView() {
        mAppEtLoginUserName = (EditText) findViewById(R.id.app_et_login_user_name);
        mAppEtLoginUserPwd = (EditText) findViewById(R.id.app_et_login_user_pwd);
        mAppEtLoginCode = (EditText) findViewById(R.id.app_et_login_code);
        mAppIvCode = (ImageView) findViewById(R.id.app_iv_code);
        mAppLoginBtn = (Button) findViewById(R.id.app_login_btn);
        mAppLoginBtn.setOnClickListener(this);
        mAppIvCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.app_login_btn:
                if (validateInput()) {
                    if (mPresenter != null) {
                        String textUsr = mAppEtLoginUserName.getText().toString();
                        String textPwd = mAppEtLoginUserPwd.getText().toString();
                        String textCode = mAppEtLoginCode.getText().toString();
                        mPresenter.loginRequest(textUsr, textPwd, textCode);
                    }
                }


                break;
            case R.id.app_iv_code:
                if (mPresenter != null) {
                    mPresenter.getCaptcha();
                }
                break;
        }
    }


    /**
     * 校验
     *
     * @return
     */
    private Boolean validateInput() {
        Boolean ret = false;
        String textUsr = mAppEtLoginUserName.getText().toString();
        String textPwd = mAppEtLoginUserPwd.getText().toString();
        String textCode = mAppEtLoginCode.getText().toString();
        if (TextUtils.isEmpty(textUsr)) {
            ret = false;
            ToastUtils.showShort(R.string.app_login_need_name);
            mAppEtLoginUserName.requestFocus();
        } else if (TextUtils.isEmpty(textPwd)) {
            ret = false;
            ToastUtils.showShort(R.string.app_login_need_password);
            mAppEtLoginUserPwd.requestFocus();
        } else if (TextUtils.isEmpty(textCode)) {
            ret = false;
            ToastUtils.showShort(R.string.app_login_need_code);
            mAppEtLoginCode.requestFocus();
        } else {
            ret = true;
        }
        return ret;
    }

    public void setImageResource(Drawable resource) {
        mAppEtLoginCode.setText("");
        if (resource == null) {
            ToastUtils.showShort(R.string.app_login_get_captcha_failed);
        } else {
            mAppIvCode.setImageDrawable(resource);
        }
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
