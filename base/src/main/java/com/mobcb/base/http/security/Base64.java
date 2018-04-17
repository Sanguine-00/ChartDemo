package com.mobcb.base.http.security;


import com.mobcb.base.http.Config;

import java.io.UnsupportedEncodingException;

/**
 * Created by lvmenghui
 * on 2017/4/20.
 */

public class Base64 {

    /**
     * BASE64字符串解码为二进制数据
     *
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
        try {
            if (str != null) {
                return decode(str.getBytes(Config.API_APP_ENCODING));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decode(byte[] bytes) {
        return android.util.Base64.decode(bytes, android.util.Base64.NO_WRAP);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        if (bytes != null) {
            return android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP);
        } else {
            return null;
        }
    }

}
