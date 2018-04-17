package com.mobcb.base.http;

/**
 * Created by lvmenghui
 * on 2018/3/29.
 */
public interface Config {

    boolean encryptRsa = true;  // 获取accessToken是否加密
    boolean encryptBody = false; // 业务接口是否加密
    /**
     * 网络加密请求的appkey
     */
    String APP_KEY_MALLHELPER_ANDROID = "8PNH1EQ33LJX";  //商管助手ANDROID

    /**
     * 网络加密请求的secret
     */
    String[] APP_SECRET_KEY_MALLHELPER_ANDROID = new String[]{
            "7YH2F0ERNNLJOQIJBID7APWJS", "2CM8YZGOG1JBFWLPIRDLN5R20",
            "99O4KX2UD5UUFLESEC5PQYFDI", "EOQL1OD8RD04KNABAFK4FWMUV",
            "89KFTI2W7RXYTI0PLL1FMAQ5U", "DUTA4DI5CE348ANOZYYZ4OXO0",
            "8LCJXPDHI9FKK3DSU6HJB3I2E", "4WZJXRTI2I3Y2MCUAC7T0GFV0",
            "5YCV39VTA5YS8GXR6OPTZ9JDD", "BF5MG4HFCN8ERCRFSGDVYQEX6",
    };

    /**
     * 编码
     */
    String API_APP_ENCODING = "UTF-8";

    /**
     * HttpUtils里clientAppName
     */
    String CLIENT_APP_NAME = "mallhelper";

    /**
     * 最大错误次数
     */
    int MAX_ERROR = 3;
}
