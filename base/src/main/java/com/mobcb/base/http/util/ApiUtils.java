package com.mobcb.base.http.util;

import android.content.Context;

import com.mobcb.base.helper.DataHelper;
import com.mobcb.base.http.Config;
import com.mobcb.base.http.ErrorCode;
import com.mobcb.base.http.bean.ApiBody;
import com.mobcb.base.http.bean.ResponseBean;
import com.mobcb.base.http.callback.AuthCallback;
import com.mobcb.base.http.helper.AuthHelper;
import com.mobcb.base.http.security.RSA;
import com.mobcb.base.http.security.ThreeDES;
import com.mobcb.base.util.LogUtils;
import com.mobcb.base.util.StrUtils;
import com.mobcb.base.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lvmenghui
 * on 2017/4/19.
 */

public class ApiUtils {

    /**
     * 返回获取工作秘钥的消息体
     *
     * @param data
     * @return
     */
    public static ApiBody createRequestBodyKey(Object data) {
        try {
            String json = JsonUtils.toJson(data);
            if (Config.encryptRsa) {
                String publicKey = DataHelper.getInstance().getPublicKey();
                String body = RSA.encryptByPublicKey(json.getBytes(Config.API_APP_ENCODING), publicKey);
                return new ApiBody(body);
            } else {
                return new ApiBody(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回业务接口请求的消息体
     *
     * @param data
     * @return
     */
    public static ApiBody createRequestBodyApi(Object data) {
        try {
            String json = JsonUtils.toJson(data);
            if (Config.encryptBody) {
                String workKey = DataHelper.getInstance().getWorkKey();
                String body = ThreeDES.encryptMode(workKey, json);
                return new ApiBody(body);
            } else {
                return new ApiBody(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据body的json字符串生成请求消息体
     *
     * @param json
     * @return
     */
    public static ApiBody createRequestBodyApi(String json, long tm) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String iv = sdf.format(tm * 1000L);
            if (Config.encryptBody) {
                String workKey = DataHelper.getInstance().getWorkKey();
                String body = ThreeDES.encryptMode(workKey, json, iv);
                return new ApiBody(body);
            } else {
                return new ApiBody(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getData(ResponseBean responseBean, Class<T> clazz) {
        if (responseBean != null) {
            if (ApiUtils.checkCode(responseBean)) {
                Object body = responseBean.getBody();
                if (body != null) {
                    String json = null;
                    if (responseBean.getEncrypted()) {
                        long tm = responseBean.getReqTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String iv = sdf.format(tm * 1000L);
                        String workKey = DataHelper.getInstance().getWorkKey();
                        json = ThreeDES.decryptMode(workKey, String.valueOf(body), iv);
                    } else {
                        json = JsonUtils.toJson(responseBean.getBody());
                    }
                    LogUtils.json(json);
                    return JsonUtils.toJavaObject(json, clazz);
                }
            } else {
                LogUtils.json(JsonUtils.toJson(responseBean));
            }
        }
        return null;
    }

    public static <T> List<T> getDataList(ResponseBean responseBean, Class<T> clazz) {
        if (responseBean != null) {
            if (ApiUtils.checkCode(responseBean)) {
                Object body = responseBean.getBody();
                if (body != null) {
                    String json = null;
                    if (responseBean.getEncrypted()) {
                        long tm = responseBean.getReqTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        String iv = sdf.format(tm * 1000L);
                        String workKey = DataHelper.getInstance().getWorkKey();
                        json = ThreeDES.decryptMode(workKey, String.valueOf(body), iv);
                    } else {
                        json = JsonUtils.toJson(responseBean.getBody());
                    }
                    LogUtils.json(json);
                    return JsonUtils.toList(json, clazz);
                }
            } else {
                LogUtils.json(JsonUtils.toJson(responseBean));
            }
        }
        return null;
    }

    public static boolean checkCode(Context context, ResponseBean bean) {
        if (bean != null) {
            if (ErrorCode.SUCCESS.equals(bean.getErrorCode())) {
                return true;
            } else {
                if (bean.getDisplay() && StrUtils.isNotEmpty(bean.getErrorMessage())) {
                    ToastUtils.showShort(bean.getErrorMessage());
                }
            }
        }
        return false;
    }

    /**
     * 如果接口报会话无效,则重新注册
     *
     * @param bean
     * @return
     */
    public static boolean checkCode(ResponseBean bean) {
        if (bean != null) {
            if (ErrorCode.SUCCESS.equals(bean.getErrorCode())) {
                return true;
            } else if (ErrorCode.ACCESS_TOKEN_ERROR.equalsIgnoreCase(bean.getErrorCode())) {
                AuthHelper.initAuth(3, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        //注册成功
                    }

                    @Override
                    public void onFailure() {
                        //注册失败
                    }
                });
            }
        }
        return false;
    }
}
