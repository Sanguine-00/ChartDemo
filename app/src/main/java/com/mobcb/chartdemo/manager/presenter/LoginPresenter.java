package com.mobcb.chartdemo.manager.presenter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mobcb.base.constant.ConfigCommon;
import com.mobcb.base.helper.DataHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.api.CaptchaService;
import com.mobcb.base.http.security.SHA;
import com.mobcb.base.mvp.BaseListener;
import com.mobcb.base.mvp.BasePresenter;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.EncryptUtils;
import com.mobcb.base.util.NetworkUtils;
import com.mobcb.base.util.ToastUtils;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.activity.LoginActivity;
import com.mobcb.chartdemo.manager.activity.MainActivity;
import com.mobcb.chartdemo.manager.bean.LoginResponseInfo;
import com.mobcb.chartdemo.manager.model.LoginModel;

import java.util.UUID;

/**
 * Created by Sanguine on 2018/4/9.
 */

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel> implements BaseListener<LoginResponseInfo> {


    private String captchaId;
    private Activity mActivity;
    private String password;


    public LoginPresenter(LoginActivity mvpView) {
        this.mvpView = mvpView;
        this.mActivity = mvpView;
        this.mvpModel = new LoginModel();
        captchaId = EncryptUtils.encryptMD5ToString(UUID.randomUUID().toString() + System.currentTimeMillis());
        getCaptcha();
    }

    /**
     * 获取验证码
     */
    public void getCaptcha() {
        String rnd = UUID.randomUUID().toString().replace("-", "");
        //生成随机数(具有要20位随机码，但是貌似超过20位也是可以的。)
        String captchaURL = String.format(CaptchaService.CAPTCHA_URL, captchaId, rnd);
        Glide.with(mActivity).load(captchaURL).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (mvpView != null) {
                    mvpView.setImageResource(resource);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (mvpView != null) {
                    mvpView.setImageResource(null);
                }
            }
        });
    }

    public void loginRequest(final String username, final String password, final String captcha) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort(R.string.app_no_network);
            return;
        }
        this.password = SHA.encryptToDES(password, ConfigCommon.ENCRYPT_KEY);
        if (mvpView != null) {
            mvpView.showLoading();
        }
        if (mvpModel != null) {
            mvpModel.loginRequest(username,
                    this.password,
                    captcha,
                    captchaId,
                    ConfigCommon.PAKAGENAME + "_" + DataHelper.getInstance().getDeviceId(),
                    this);
        }
    }

    @Override
    public void onSuccess(LoginResponseInfo loginResponseInfo) {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
        if (loginResponseInfo != null) {
            LoginHelper loginHelper = LoginHelper.getInstance();
            String managerId = loginResponseInfo.getId();
            String managerName = loginResponseInfo.getUsername();
            String managerNickName = loginResponseInfo.getNickname();
            String managerHeaderImg = loginResponseInfo.getHeadImg();
            String shopId = loginResponseInfo.getShopId();
            String shopName = loginResponseInfo.getShopName();
            String shopIcon = loginResponseInfo.getShopIcon();
            String mallId = loginResponseInfo.getMallId();
            String deptId = loginResponseInfo.getDeptId();
            int loginSession = loginResponseInfo.getLoginSessionFlag();
            boolean isMall = loginResponseInfo.isIsMall();
            loginHelper.saveUserInfo(managerId, password, managerName, managerNickName,
                    managerHeaderImg, shopId, shopName, shopIcon,
                    mallId, deptId, loginSession, isMall);

            // TODO: 2018/4/10 登录成功,要再次提交deviceToken
//            String accessToken = DataHelper.getInstance().getAccessToken();

            ActivityUtils.startActivity(MainActivity.class);
            if (mvpView != null) {
                mvpView.finish();
            }
        }


    }

    @Override
    public void onFailure(String message) {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
        ToastUtils.showShort(message);
        getCaptcha();
    }

    @Override
    public void onFailure(int resId) {
        if (mvpView != null) {
            mvpView.hideLoading();
        }
        ToastUtils.showShort(resId);
        getCaptcha();
    }
}
