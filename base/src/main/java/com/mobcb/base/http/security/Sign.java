package com.mobcb.base.http.security;

import android.text.TextUtils;

import com.mobcb.base.helper.DataHelper;
import com.mobcb.base.helper.LoginHelper;
import com.mobcb.base.http.Config;
import com.mobcb.base.http.util.CommonUtil;
import com.mobcb.base.util.AppUtils;
import com.mobcb.base.util.DeviceUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lvmenghui
 * on 2017/4/18.
 */

public class Sign {
    public static final String BODY = "body";

    /**
     * 给请求map加签名
     *
     * @param requestMap
     * @return
     */
    public static Map<String, String> sign(Map<String, String> requestMap, long tm) {
        // 添加额外参数=======================================
        /*1. clientType，客户端类型，业务处理使用*/
        requestMap.put("clientType", "android");
        /*2. version 版本号，业务处理使用，访问统计使用*/
        requestMap.put("appVersion", AppUtils.getAppVersionName());
        /*3. uid 会员Id，做访问统计用*/
        String memberUid = LoginHelper.getInstance().getManagerId();
        if (!TextUtils.isEmpty(memberUid)) {
            requestMap.put("appUid", memberUid);
        }
        /*4. osVersion 操作系统版本号，做访问统计用*/
        requestMap.put("osVersion", DeviceUtils.getSDKVersionName());
        /*5. model 机型，做访问统计用*/
        String model = DeviceUtils.getModel();
        if (model != null && !model.equals("")) {
            try {
                model = URLEncoder.encode(model, Config.API_APP_ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        requestMap.put("model", model);
        /*6. clientAppName应用端类型*/
        requestMap.put("clientAppName", Config.CLIENT_APP_NAME);
        /*7. rnd，随机数，客户端生成，标识该次请求，在服务端该标识一次有效，如果两次请求带了相同的rnd，则第二次请求做重复请求返回*/
        requestMap.put("rnd", CommonUtil.uuid());
        /*8. 添加accessToken*/
        String accessToken = DataHelper.getInstance().getAccessToken();
        requestMap.put("accessToken", accessToken);
        /*9. 添加tm参数*/
        requestMap.put("timestamp", String.valueOf(tm));
        /*10. 固定的APPKEY*/
        requestMap.put("appKey", Config.APP_KEY_MALLHELPER_ANDROID);
        /*11. 加上mallId参数*/
        String mallId = DataHelper.getInstance().getMallId();
        if (!TextUtils.isEmpty(mallId)) {
            requestMap.put("mallId", mallId);
        }

        // 生成并添加签名=======================================
        // 排序map，根据参数名升序
        requestMap = sortMapByKey(requestMap);
        // 将排序后的map转换为字符串，参数之间以&符号连接
        String urlOrdered = getUrlParamsByMap(requestMap);
        // 取模，取出来的值0-9
        long modOftm = tm % 10;
        // 根据模值，获得不同的key
        String key = Config.APP_SECRET_KEY_MALLHELPER_ANDROID[(int) modOftm];
        // 将排序后的参数字符串加上key，进行256sha加密，并将加密后的字符串小写
        String sign = SHA.encryptToSHA(urlOrdered + key).toLowerCase();
        // 添加签名
        requestMap.put("sign", sign);
        // 添加签名类型
        requestMap.put("signType", "sha");

        if (requestMap.containsKey(BODY)) {
            requestMap.remove(BODY);
        }

        return requestMap;
    }

    /**
     * 将map按key进行排序
     *
     * @param map
     * @return
     */
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String str1, String str2) {
                return str1.compareTo(str2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 参数之间以&符号连接
     *
     * @param map
     * @return
     */
    private static String getUrlParamsByMap(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String decodeValue = null;
            try {
                // Url参数值不编码，要使用不编码的参数进行加密校验
                if (entry.getKey() != null && entry.getValue() != null && !entry.getKey().equals(BODY)) {
                    decodeValue = URLDecoder.decode(entry.getValue(), Config.API_APP_ENCODING);
                } else {
                    decodeValue = entry.getValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sb.append(entry.getKey() + "=" + decodeValue);
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

}
