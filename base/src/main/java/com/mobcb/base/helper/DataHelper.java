package com.mobcb.base.helper;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.mobcb.base.constant.ConfigCommon;
import com.mobcb.base.http.ErrorCode;
import com.mobcb.base.http.api.UploadService;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.subscriber.MySubscriber;
import com.mobcb.base.http.util.ApiUtils;
import com.mobcb.base.http.util.CommonUtil;
import com.mobcb.base.http.util.JsonUtils;
import com.mobcb.base.util.AppUtils;
import com.mobcb.base.util.DeviceUtils;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.PhoneUtils;
import com.mobcb.base.util.SPUtils;
import com.mobcb.base.util.StrUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class DataHelper {
    private final String KEY_DEVICE_ID = "key.deviceId";
    private final String KEY_MALLID = "key.mallid";
    private final String KEY_APPID = "key.appid";
    private final String KEY_INSTALLFIRST = "key.installFirst";
    private final String KEY_DEVICETOKEN = "key.devicetoken";
    private final String KEY_PUBLIC_KEY = "key.publicKey";
    private final String KEY_ACCESS_TOKEN = "key.accessToken";
    private final String KEY_WORK_KEY = "key.workKey";
    private final String KEY_INVALID_TIME = "key.invalidTime";

    private static class DataHelperHolder {
        private static final DataHelper INSTANCE = new DataHelper();
    }

    private DataHelper() {
    }

    public static DataHelper getInstance() {
        return DataHelperHolder.INSTANCE;
    }

    /**
     * 保存deviceId
     *
     * @param deviceId
     */
    public void saveDeviceId(String deviceId) {
        SPUtils.getInstance().put(KEY_DEVICE_ID, deviceId, true);
    }

    /**
     * 获取deviceId
     *
     * @return
     */
    public String getDeviceId() {
        String deviceId = SPUtils.getInstance().getString(KEY_DEVICE_ID);
        if (StrUtils.isEmpty(deviceId)) {
            deviceId = CommonUtil.uuid();
            saveDeviceId(deviceId);
        }
        LogUtils.i("DeviceId:" + deviceId);
        return deviceId;
    }

    /**
     * 保存mallId
     *
     * @param mallId
     */
    public void saveMallId(String mallId) {
        SPUtils.getInstance().put(KEY_MALLID, mallId, true);
    }

    /**
     * 获取mallId
     *
     * @return
     */
    public String getMallId() {
        return SPUtils.getInstance().getString(KEY_MALLID, "1");
    }

    /**
     * 保存InstallFirst
     *
     * @param installFirst
     */
    public void saveInstallFirst(Boolean installFirst) {
        SPUtils.getInstance().put(KEY_INSTALLFIRST, installFirst, true);
    }

    /**
     * 获取InstallFirst，默认为true
     *
     * @return
     */
    public Boolean getInstallFirst() {
        return SPUtils.getInstance().getBoolean(KEY_INSTALLFIRST, true);
    }

    /**
     * 保存appid
     *
     * @param appId
     */
    public void saveAppid(String appId) {
        SPUtils.getInstance().put(KEY_APPID, appId, true);
    }

    /**
     * 获取appid
     *
     * @return
     */
    public String getAppid() {
        return SPUtils.getInstance().getString(KEY_APPID, "");
    }

    /**
     * 保存DeviceToken
     *
     * @param token
     */
    public void saveDeviceToken(String token) {
        if (token != null && !token.equals("")) {
            SPUtils.getInstance().put(KEY_DEVICETOKEN, token, true);
        }
    }

    /**
     * 保存DeviceToken,userid
     *
     * @param token
     * @param userid
     * @param phone
     */
    public void saveDeviceToken(String token, String userid, String phone) {
        if (token != null && !token.equals("")) {
            SPUtils.getInstance().put(KEY_DEVICETOKEN, token, true);
            postToServer(token, userid, phone);
        }
    }



    /**
     * 获取DeviceToken
     *
     * @return
     */
//    public String getDeviceToken() {
//        String regId = MiPushClient.getRegId(context);
//        if (StringUtils.isEmpty(regId)) {
//            //如果是空，从缓存读取
//            regId = sp.getString(KEY_DEVICETOKEN, "");
//            MiPushManager.registerMiPush(context);
//        }
//        return regId;
//    }
    public void savePublicKey(String publicKey) {
        SPUtils.getInstance().put(KEY_PUBLIC_KEY, publicKey, true);
    }

    public void saveAccessToken(String accessToken) {
        SPUtils.getInstance().put(KEY_ACCESS_TOKEN, accessToken, true);
    }

    public void saveWorkKey(String workKey) {
        SPUtils.getInstance().put(KEY_WORK_KEY, workKey, true);
    }

    public void saveInvalidTime(long invalidTime) {
        SPUtils.getInstance().put(KEY_INVALID_TIME, invalidTime, true);
    }

    public String getPublicKey() {
        return SPUtils.getInstance().getString(KEY_PUBLIC_KEY);
    }

    public String getAccessToken() {
        return SPUtils.getInstance().getString(KEY_ACCESS_TOKEN);
    }


    public String getWorkKey() {
        return SPUtils.getInstance().getString(KEY_WORK_KEY);
    }


    public long getInvalidTime() {
        return SPUtils.getInstance().getLong(KEY_INVALID_TIME, 0L);
    }


    /**
     * app更新设备信息接口
     *
     * @param token
     * @param userid
     * @param phone
     */
    private void postToServer(final String token, final String userid, final String phone) {
        AuthHelper.auth(UploadService.class).subscribe(new Action1<UploadService>() {
            @Override
            public void call(UploadService uploadService) {
                JsonObject updateObject = postParams(token, userid, phone);
                Observable<ResponseBean> observable = uploadService.updateDeviceInfo(updateObject);
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MySubscriber<ResponseBean>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                LogUtils.vTag("uploadPicture-onError");
                            }

                            @Override
                            public void onNext(ResponseBean responseBean) {
                                if (responseBean == null) {
                                    // failed
                                    return;
                                }
                                if (ApiUtils.checkCode(responseBean)) {
                                    Object object = ApiUtils.getData(responseBean, Object.class);
                                    System.out.println(JsonUtils.toJson(object));
                                }
                            }
                        });
            }
        });
    }

    /**
     * 提交deviceToken到服务器的参数
     *
     * @param token
     * @param memberId
     * @param phone
     * @return
     */
    private JsonObject postParams(String token, String memberId, String phone) {
        try {
            JsonObject paramObject = new JsonObject();
            paramObject.addProperty("deviceToken", token);
            paramObject.addProperty("appType", "android");
            paramObject.addProperty("appName", ConfigCommon.API_APP_NAME);
            paramObject.addProperty("appVersion", AppUtils.getAppVersionName());
            paramObject.addProperty("appId", getAppid());
            paramObject
                    .addProperty("deviceId", ConfigCommon.PAKAGENAME + "_" + DataHelper.getInstance().getDeviceId());
            paramObject.addProperty("mac",
                    DeviceUtils.getMacAddress());
            paramObject.addProperty("appOs", "Android");
            paramObject.addProperty("appOsVersion",
                    DeviceUtils.getSDKVersionName());
            paramObject.addProperty("tokenType", "mipush"); //推送类型为小米推送，2016-08-25修改
            if (!TextUtils.isEmpty(memberId)) {
                paramObject.addProperty("memberId", memberId);
            }
            try {

                String phoneNum = PhoneUtils.getPhoneNumber();
                if (phoneNum != null && !phoneNum.equals("")) {
                    paramObject.addProperty("phone", phoneNum);
                } else {
                    if (phone != null && !phone.equals("")) {
                        paramObject.addProperty("phone", phone);
                    } else {
                        try {
                            paramObject.addProperty("phone", LoginHelper.getInstance().getManagerPhone());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return paramObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
