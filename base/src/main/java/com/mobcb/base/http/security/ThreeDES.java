package com.mobcb.base.http.security;


import com.mobcb.base.http.Config;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDES {

    private static final String Algorithm = "DESede"; // 定义

    public static String encryptMode(String key, String src) {
        try {
            byte[] keybyte = Base64.decode(key);
            byte[] srcbyte = src.getBytes(Charset.forName(Config.API_APP_ENCODING));
            byte[] result = encryptMode(keybyte, srcbyte);
            return Base64.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 标准加密
     */
    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 标准解密
     */
    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static String decryptMode(String key, String base64Src) {
        // 生成密钥
        byte[] keybyte = new byte[0];
        byte[] src = new byte[0];
        try {
            keybyte = Base64.decode(key);
            src = Base64.decode(base64Src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] result = decryptMode(keybyte, src);
        if (result != null) {
            return new String(result, Charset.forName(Config.API_APP_ENCODING));
        } else {
            return null;
        }
    }

    /**
     * 标准解密
     */
    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // 转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    // 加密算法,可用
    // DES,DESede,Blowfish

    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    public static String encryptMode(String key, String src, String iv) {
        try {
            byte[] keyBytes = Base64.decode(key);
            byte[] ivBytes = iv.getBytes(Config.API_APP_ENCODING);
            byte[] srcBytes = src.getBytes(Config.API_APP_ENCODING);

            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);

            IvParameterSpec r = new IvParameterSpec(ivBytes);

            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm + "/CBC/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, deskey, r);
            return Base64.encode(c1.doFinal(srcBytes));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src, byte[] iv) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            IvParameterSpec r = new IvParameterSpec(iv);

            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm + "/CBC/PKCS5Padding");
            c1.init(Cipher.DECRYPT_MODE, deskey, r);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 标准解密
     */
    public static String decryptMode(String key, String base64Src, String iv) {
        // 生成密钥
        try {
            byte[] keybyte = Base64.decode(key);
            byte[] src = Base64.decode(base64Src);
            byte[] ivbyte = iv.getBytes(Charset.forName(Config.API_APP_ENCODING));
            byte[] result = decryptMode(keybyte, src, ivbyte);
            if (result != null) {
                return new String(result, Charset.forName(Config.API_APP_ENCODING));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
