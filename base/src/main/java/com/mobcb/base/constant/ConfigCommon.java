package com.mobcb.base.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by MyStudio on 2016/3/4.
 */
public interface ConfigCommon {
    /**
     * 在线升级等功能需要的参数
     */
    String API_APP_NAME = "zhenghongcheng_manager_android";
    /**
     * 当前程序包名
     */
    String PAKAGENAME = "com.mobcb.zhenghongcheng.manager";
    /**
     * 调试keystore的签名(MD5)
     * MD5: DA:26:FD:8B:48:5D:40:F3:33:A0:19:E4:ED:31:43:83
     * SHA1: 29:FC:F7:60:1A:ED:B4:B4:FB:B5:E6:DF:8B:7F:A3:7C:A2:55:46:9E
     * SHA256: 01:BB:EB:29:A1:A5:6B:9E:AF:4B:60:91:5F:82:7C:19:08:1D:55:BE:8F:A2:4E:9D:ED:91:86:25:81:7A:9E:B0
     */
    String SIGN_DEBUG = "da26fd8b485d40f333a019e4ed314383";
    /**
     * 发布包keystore的签名(MD5)
     * MD5: 3B:4C:5B:7C:28:59:68:44:8E:F2:96:2F:D1:37:B5:AC
     * SHA1: 19:42:EB:F4:77:6C:5A:D0:2B:2A:8C:F5:39:FA:2C:DC:06:F9:7F:F2
     * SHA256: 90:60:91:2C:F0:93:73:0D:E5:A9:D6:50:FE:04:A7:7F:39:66:25:B5:D3:DA:58:38:2F:B1:35:B7:E2:73:6E:62
     */
    String SIGN_ONLINE = "3b4c5b7c285968448ef2962fd137b5ac";
    /**
     * 文件保存到SDCARD中文件夹名
     */
    String SDCARD_DIR_NAME = "." + PAKAGENAME;
    /**
     * 文件保存到SDCARD路径
     */
    String SDCARD_PATH = //Environment.getExternalFilesDir()
            Environment.getExternalStorageDirectory()
                    + File.separator + SDCARD_DIR_NAME;
    /**
     * 商场内WIFI  SSID配置
     */
    String WIFI_SSID = "WiredSSID";
    /**
     * 字符编码
     */
    String ENCODING = "UTF-8";
    /**
     * webview自适应加的前缀代码
     */
    String WEBVIEW_FRONT_CODE = "<style type='text/css'>img{max-width:100% !important} video{max-width:100% !important}</style>";
    /**
     * HttpUtils里clientAppName
     */
    String CLIENT_APP_NAME = "mallhelper";

    /**
     * 加密key
     */
    String ENCRYPT_KEY = "MOBCB123";
}
