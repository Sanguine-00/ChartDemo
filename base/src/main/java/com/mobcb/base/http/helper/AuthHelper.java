package com.mobcb.base.http.helper;

import com.mobcb.base.R;
import com.mobcb.base.helper.DataHelper;
import com.mobcb.base.http.Config;
import com.mobcb.base.http.ErrorCode;
import com.mobcb.base.http.api.AuthService;
import com.mobcb.base.http.bean.AccessToken;
import com.mobcb.base.http.bean.AccessTokenBean;
import com.mobcb.base.http.bean.ApiBody;
import com.mobcb.base.http.bean.PublicKey;
import com.mobcb.base.http.bean.PublicKeyBean;
import com.mobcb.base.http.bean.request.DeviceRegisterRequest;
import com.mobcb.base.http.callback.AuthCallback;
import com.mobcb.base.http.factory.ApiFactory;
import com.mobcb.base.http.security.RSA;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.http.util.CommonUtil;
import com.mobcb.base.http.util.JsonUtils;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.ToastUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lvmenghui
 * on 2017/4/19.
 */

public class AuthHelper {

    private AuthHelper() {
    }

    private static class AuthServiceHolder {
        private static final AuthService AUTH_SERVICE = ApiFactory.createAuth();
    }

    private static Observable<PublicKeyBean> publicKeyObservable() {
        // 获取publicKey的被观察者
        AuthService service = AuthServiceHolder.AUTH_SERVICE;
        String deviceId = DataHelper.getInstance().getDeviceId();
        Observable<PublicKeyBean> observable = service.getPubKey(deviceId, Config.APP_KEY_MALLHELPER_ANDROID, CommonUtil.uuid());
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private static Observable<AccessTokenBean> accessTokenObservable() {
        // 获取accessToken的被观察者
        AuthService service = AuthServiceHolder.AUTH_SERVICE;
        String deviceId = DataHelper.getInstance().getDeviceId();
        DeviceRegisterRequest deviceRegisterRequest = new DeviceRegisterRequest();
        deviceRegisterRequest.setClientPublicKey(RSA.getPublicKey());
        deviceRegisterRequest.setKeySeed(CommonUtil.uuid());

        ApiBody requestBody = ApiUtils.createRequestBodyKey(deviceRegisterRequest);
        return service.getAccessToken(deviceId, Config.APP_KEY_MALLHELPER_ANDROID, CommonUtil.uuid(), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class AuthTransformer implements Observable.Transformer<PublicKeyBean, AccessTokenBean> {
        @Override
        public Observable<AccessTokenBean> call(Observable<PublicKeyBean> observable) {
            return observable.flatMap(new Func1<PublicKeyBean, Observable<AccessTokenBean>>() {
                @Override
                public Observable<AccessTokenBean> call(PublicKeyBean publicKeyBean) {
                    if (ErrorCode.SUCCESS.equals(publicKeyBean.getErrorCode())) {
                        PublicKey publicKey = publicKeyBean.getBody();
                        DataHelper.getInstance().savePublicKey(publicKey.getPublicKey());
                        return AuthHelper.accessTokenObservable();
                    } else {
                        LogUtils.json(JsonUtils.toJson(publicKeyBean));
                        ToastUtils.showShort(R.string.string_publickey_failure);
                        return null;
                    }
                }
            }).onExceptionResumeNext(AuthHelper.accessTokenObservable());
        }
    }

    public static <T> Observable<T> auth(final Class<T> clazz) {
        long curTm = System.currentTimeMillis() / 1000L;
        long invalidTime = DataHelper.getInstance().getInvalidTime();
        long invalidSeconds = invalidTime - curTm;
        if (invalidSeconds <= 5) {
            return publicKeyObservable().flatMap(new Func1<PublicKeyBean, Observable<AccessTokenBean>>() {
                @Override
                public Observable<AccessTokenBean> call(PublicKeyBean publicKeyBean) {
                    if (ErrorCode.SUCCESS.equals(publicKeyBean.getErrorCode())) {
                        PublicKey publicKey = publicKeyBean.getBody();
                        DataHelper.getInstance().savePublicKey(publicKey.getPublicKey());
                        return AuthHelper.accessTokenObservable();
                    } else {
                        ToastUtils.showShort(R.string.string_publickey_failure);
                        return null;
                    }
                }
            }).flatMap(new Func1<AccessTokenBean, Observable<T>>() {
                @Override
                public Observable<T> call(AccessTokenBean accessTokenBean) {
                    resolveAccessToken(accessTokenBean);
                    return Observable.just(ApiFactory.create(clazz));
                }
            }).subscribeOn(Schedulers.io())
                    .onExceptionResumeNext(Observable.just(ApiFactory.create(clazz)));
        } else {
            return Observable.just(ApiFactory.create(clazz));
        }
    }

    private static void resolveAccessToken(AccessTokenBean accessTokenBean) {
        if (ErrorCode.SUCCESS.equals(accessTokenBean.getErrorCode())) {
            String json = accessTokenBean.getBody();
            if (accessTokenBean.getEncrypted()) {
                try {
                    json = RSA.decryptByPrivateKey(json, RSA.getPrivateKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            AccessToken accessToken = JsonUtils.fromJson(json, AccessToken.class);
            if (accessToken != null) {
                DataHelper.getInstance().saveAccessToken(accessToken.getAccessToken());
                DataHelper.getInstance().saveWorkKey(accessToken.getWorkKey());
                long invalidTime = accessToken.getRegisterTime() + accessToken.getSessionValiditySeconds();
                DataHelper.getInstance().saveInvalidTime(invalidTime);
            }
        }
    }

    public static void initAuth(final int errorCount, final AuthCallback authCallback) {
        Observable.Transformer liftAll = new AuthTransformer();
        AuthHelper.publicKeyObservable().compose(liftAll).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<AccessTokenBean>() {
                    @Override
                    public void onNext(AccessTokenBean accessTokenBean) {
                        AuthHelper.resolveAccessToken(accessTokenBean);
                        authCallback.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtils.e("设备注册失败" + errorCount);
                        if (errorCount < Config.MAX_ERROR) {
                            int newErrorCount = errorCount + 1;
                            initAuth(newErrorCount, authCallback);
                        } else {
                            authCallback.onFailure();
                        }
                    }
                });
    }

}
