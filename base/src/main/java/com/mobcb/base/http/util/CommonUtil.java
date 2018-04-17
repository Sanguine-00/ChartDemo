package com.mobcb.base.http.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class CommonUtil {

    public static Boolean isCrossWolkInited = false;//用来判断crosswalk是否解压完成
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * checkPermissions
     *
     * @param context
     * @param permission
     * @return true or false
     */
    public static boolean checkPermissions(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(permission,
                context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

//    /**
//     * Determine the current networking is WIFI
//     *
//     * @param context
//     * @return
//     */
//    public static boolean currentNoteworkTypeIsWIFI(Context context) {
//        try {
//            ConnectivityManager connectionManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            return connectionManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * Judge wifi is available
//     *
//     * @param inContext
//     * @return
//     */
//    public static boolean isWiFiActive(Context inContext) {
//        if (checkPermissions(inContext, "android.permission.ACCESS_WIFI_STATE")) {
//            Context context = inContext.getApplicationContext();
//            ConnectivityManager connectivity = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            if (connectivity != null) {
//                NetworkInfo[] info = connectivity.getAllNetworkInfo();
//                if (info != null) {
//                    for (int i = 0; i < info.length; i++) {
//                        if (info[i].getTypeName().equals("WIFI")
//                                && info[i].isConnected()) {
//                            return true;
//                        }
//                    }
//                }
//            }
//            return false;
//        } else {
//            Log.e("lost permission",
//                    "lost--->android.permission.ACCESS_WIFI_STATE");
//
//            return false;
//        }
//    }
//
//    /**
//     * Testing equipment networking and networking WIFI
//     *
//     * @param context
//     * @return true or false
//     */
//    public static boolean isNetworkAvailable(Context context) {
//        if (checkPermissions(context, "android.permission.INTERNET")) {
//            ConnectivityManager cManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo info = cManager.getActiveNetworkInfo();
//            if (info != null && info.isAvailable()) {
//                return true;
//            } else {
//                Log.e("error", "Network error");
//
//                return false;
//            }
//
//        } else {
//            Log.e(" lost  permission", "lost----> android.permission.INTERNET");
//
//            return false;
//        }
//
//    }
//
//    /**
//     * Get the current time format yyyy-MM-dd HH:mm:ss
//     *
//     * @return
//     */
//    public static String getTime() {
//        Date date = new Date();
//        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");
//        return localSimpleDateFormat.format(date);
//    }
//
//    /**
//     * getUmengChannel
//     *
//     * @param context
//     * @return appkey
//     */
//    public static String getUmengChannel(Context paramContext) {
//        String umsAppkey;
//        try {
//            PackageManager localPackageManager = paramContext
//                    .getPackageManager();
//            ApplicationInfo localApplicationInfo = localPackageManager
//                    .getApplicationInfo(paramContext.getPackageName(), 128);
//            if (localApplicationInfo != null) {
//                String str = localApplicationInfo.metaData
//                        .getString("UMENG_CHANNEL");
//                if (str != null) {
//                    umsAppkey = str;
//                    return umsAppkey.toString();
//                }
//                Log.e("UmsAgent",
//                        "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
//            }
//        } catch (Exception localException) {
//            Log.e("UmsAgent",
//                    "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
//            localException.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * get Meta-data Value
//     *
//     * @param context
//     * @return appkey
//     */
//    public static String getMetaValue(Context paramContext, String key) {
//        String val;
//        try {
//            PackageManager localPackageManager = paramContext
//                    .getPackageManager();
//            ApplicationInfo localApplicationInfo = localPackageManager
//                    .getApplicationInfo(paramContext.getPackageName(), 128);
//            if (localApplicationInfo != null) {
//                String str = localApplicationInfo.metaData
//                        .getString(key);
//                if (str != null) {
//                    val = str;
//                    return val.toString();
//                }
//                Log.e("UmsAgent",
//                        "Could not read " + key + " meta-data from AndroidManifest.xml.");
//            }
//        } catch (Exception localException) {
//            Log.e("UmsAgent",
//                    "Could not read " + key + " meta-data from AndroidManifest.xml.");
//            localException.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * get currnet activity's name
//     *
//     * @param context
//     * @return
//     */
//    public static String getActivityName(Context context) {
//        ActivityManager am = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        if (checkPermissions(context, "android.permission.GET_TASKS")) {
//            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//            return cn.getShortClassName();
//        } else {
//            Log.e("lost permission", "android.permission.GET_TASKS");
//
//            return null;
//        }
//
//    }
//
//    /**
//     * get PackageName
//     *
//     * @param context
//     * @return
//     */
//    public static String getPackageName(Context context) {
//        ActivityManager am = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//
//        if (checkPermissions(context, "android.permission.GET_TASKS")) {
//            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//            return cn.getPackageName();
//        } else {
//            Log.e("lost permission", "android.permission.GET_TASKS");
//
//            return null;
//        }
//
//    }
//
//    /**
//     * get OS number
//     *
//     * @param context
//     * @return
//     */
//    public static String getOsVersion(Context context) {
//        String osVersion = "";
//        if (checkPhoneState(context)) {
//            osVersion = Build.VERSION.RELEASE;
//            printLog("android_osVersion", "OsVerson" + osVersion);
//
//            return osVersion;
//        } else {
//            Log.e("android_osVersion", "OsVerson get failed");
//
//            return null;
//        }
//    }
//
//    /**
//     * get deviceid
//     *
//     * @param context add <uses-permission android:name="READ_PHONE_STATE" />
//     * @return
//     */
//    public static String getDeviceID(Context context) {
//        if (checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
//            String deviceId = "";
//            if (checkPhoneState(context)) {
//                TelephonyManager tm = (TelephonyManager) context
//                        .getSystemService(Context.TELEPHONY_SERVICE);
//                deviceId = tm.getDeviceId();
//            }
//            if (deviceId != null) {
//                printLog("commonUtil", "deviceId:" + deviceId);
//
//                return deviceId;
//            } else {
//                Log.e("commonUtil", "deviceId is null");
//
//                return null;
//            }
//        } else {
//            Log.e("lost permissioin",
//                    "lost----->android.permission.READ_PHONE_STATE");
//
//            return "";
//        }
//    }
//
//    /**
//     * check phone _state is readied ;
//     *
//     * @param context
//     * @return
//     */
//    public static boolean checkPhoneState(Context context) {
//        PackageManager packageManager = context.getPackageManager();
//        if (packageManager
//                .checkPermission("android.permission.READ_PHONE_STATE",
//                        context.getPackageName()) != 0) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * get sdk number
//     *
//     * @param paramContext
//     * @return
//     */
//    public static String getSdkVersion(Context paramContext) {
//        String osVersion = "";
//        if (!checkPhoneState(paramContext)) {
//            osVersion = Build.VERSION.RELEASE;
//            Log.e("android_osVersion", "OsVerson" + osVersion);
//
//            return osVersion;
//        } else {
//            Log.e("android_osVersion", "OsVerson get failed");
//
//            return null;
//        }
//    }
//
//    /**
//     * Get the version number of the current program
//     *
//     * @param context
//     * @return
//     */
//
//    public static String getCurVersion(Context context) {
//        String curversion = "";
//        try {
//            // ---get the package info---
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            curversion = pi.versionName;
//            if (curversion == null || curversion.length() <= 0) {
//                return "";
//            }
//        } catch (Exception e) {
//            Log.e("VersionInfo", "Exception", e);
//
//        }
//        return curversion;
//    }
//
//    /**
//     * Get the current send model
//     *
//     * @param context
//     * @return
//     */
//    public static int getReportPolicyMode(Context context) {
//        String str = context.getPackageName();
//        SharedPreferences localSharedPreferences = context
//                .getSharedPreferences("ums_agent_online_setting_" + str, 0);
//        int type = localSharedPreferences.getInt("ums_local_report_policy", 0);
//        return type;
//    }
//
//    /**
//     * To determine whether it contains a gyroscope
//     *
//     * @return
//     */
//    public static boolean isHaveGravity(Context context) {
//        SensorManager manager = (SensorManager) context
//                .getSystemService(Context.SENSOR_SERVICE);
//        if (manager == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Get the current networking
//     *
//     * @param context
//     * @return WIFI or MOBILE
//     */
//    public static String getNetworkType(Context context) {
//        TelephonyManager manager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        int type = manager.getNetworkType();
//        String typeString = "UNKNOWN";
//        if (type == TelephonyManager.NETWORK_TYPE_CDMA) {
//            typeString = "CDMA";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
//            typeString = "EDGE";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_EVDO_0) {
//            typeString = "EVDO_0";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
//            typeString = "EVDO_A";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_GPRS) {
//            typeString = "GPRS";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_HSDPA) {
//            typeString = "HSDPA";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_HSPA) {
//            typeString = "HSPA";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_HSUPA) {
//            typeString = "HSUPA";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_UMTS) {
//            typeString = "UMTS";
//        }
//        if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
//            typeString = "UNKNOWN";
//        }
//
//        return typeString;
//    }
//
//    /**
//     * Determine the current network type
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isNetworkTypeWifi(Context context) {
//        // TODO Auto-generated method stub
//
//        if (checkPermissions(context, "android.permission.INTERNET")) {
//            ConnectivityManager cManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo info = cManager.getActiveNetworkInfo();
//
//            if (info != null && info.isAvailable()
//                    && info.getTypeName().equals("WIFI")) {
//                return true;
//            } else {
//                Log.e("error", "Network not wifi");
//                return false;
//            }
//        } else {
//            Log.e(" lost  permission", "lost----> android.permission.INTERNET");
//            return false;
//        }
//
//    }
//
//    /**
//     * Get the current application version number
//     *
//     * @param context
//     * @return
//     */
//    public static String getVersion(Context context) {
//        String versionName = "";
//        try {
//            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            versionName = pi.versionName;
//            if (versionName == null || versionName.length() <= 0) {
//                return "";
//            }
//        } catch (Exception e) {
//            Log.e("UmsAgent", "Exception", e);
//
//        }
//        return versionName;
//    }
//
//    /**
//     * Get the current application version code
//     *
//     * @param context
//     * @return
//     */
//    public static int getVersionCode(Context context) {
//        PackageManager packageManager = context.getPackageManager();
//        PackageInfo packInfo;
//        try {
//            packInfo = packageManager.getPackageInfo(context.getPackageName(),
//                    0);
//            int version = packInfo.versionCode;
//            return version;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * Set the output log
//     *
//     * @param tag
//     * @param log
//     */
//
//    public static void printLog(String tag, String log) {
//        Log.d(tag, log);
//    }
//
//    //============================================获取MAC===========================================
//
//    /**
//     * 获取MAC
//     *
//     * @return
//     */
//    public static String getLocalMacAddress(Context context) {
//        String mac = "";
//        try {
//            WifiManager wifi = (WifiManager) context
//                    .getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            mac = info.getMacAddress();
//            if (mac == null || "".equals(mac)) {
//                mac = getMacFromFile(context);
//            }
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (mac == null || "".equals(mac) || "02-00-00-00-00-00".equals(mac) || "02:00:00:00:00:00".equals(mac)) {
//                    mac = getMacInMarshmallow();
//                }
//                if (mac == null || "".equals(mac) || "02-00-00-00-00-00".equals(mac) || "02:00:00:00:00:00".equals(mac)) {
//                    mac = getMachineHardwareAddress();
//                }
//            }
//            if (mac != null) {
//                mac = mac.replace(":", "-").toUpperCase();
//            } else {
//                mac = "";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            mac = "";
//        }
//        return mac;
//    }
//
//    /**
//     * 获取设备HardwareAddress地址
//     *
//     * @return
//     */
//    private static String getMachineHardwareAddress() {
//        Enumeration<NetworkInterface> interfaces = null;
//        try {
//            interfaces = NetworkInterface.getNetworkInterfaces();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        String hardWareAddress = null;
//        NetworkInterface iF = null;
//        while (interfaces.hasMoreElements()) {
//            iF = interfaces.nextElement();
//            try {
//                hardWareAddress = bytesToString(iF.getHardwareAddress());
//                if (hardWareAddress == null) continue;
//            } catch (SocketException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return hardWareAddress;
//    }
//
//    /***
//     * byte转为String
//     *
//     * @param bytes
//     * @return
//     */
//    private static String bytesToString(byte[] bytes) {
//        if (bytes == null || bytes.length == 0) {
//            return null;
//        }
//        StringBuilder buf = new StringBuilder();
//        for (byte b : bytes) {
//            buf.append(String.format("%02X:", b));
//        }
//        if (buf.length() > 0) {
//            buf.deleteCharAt(buf.length() - 1);
//        }
//        return buf.toString();
//    }
//
//
//    /**
//     * 获取MAC的另一种方法
//     */
//    private static String getMacFromFile(Context context) {
//        String mIP = getIpAddress(context);
//        List<String> mResult = readFileLines("/proc/net/arp");
//        if (mResult != null && mResult.size() > 1) {
//            for (int j = 1; j < mResult.size(); ++j) {
//                List<String> mList = new ArrayList<String>();
//                String[] mType = mResult.get(j).split(" ");
//                for (int i = 0; i < mType.length; ++i) {
//                    if (mType[i] != null && mType[i].length() > 0)
//                        mList.add(mType[i]);
//                }
//                if (mList != null && mList.size() > 4 && mList.get(0).equalsIgnoreCase(mIP)) {
//                    String result = "";
//                    String[] tmp = mList.get(3).split(":");
//                    for (int i = 0; i < tmp.length; ++i) {
//                        result += tmp[i];
//                    }
//                    result = result.toUpperCase();
//                    return result;
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 以行为单位读取文件，常用于读面向行的格式化文件
//     */
//    private static List<String> readFileLines(String fileName) {
//        File file = new File(fileName);
//        BufferedReader reader = null;
//        String tempString = "";
//        List<String> mResult = new ArrayList<String>();
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            while ((tempString = reader.readLine()) != null) {
//                mResult.add(tempString);
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//        return mResult;
//    }
//
//    /**
//     * 6.0棉花糖系统获取手机的MAC地址
//     *
//     * @return
//     */
//    public static String getMacInMarshmallow() {
//        String str = "";
//        String macSerial = "";
//        try {
//            Process pp = Runtime.getRuntime().exec(
//                    "cat /sys/class/net/wlan0/address ");
//            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            for (; null != str; ) {
//                str = input.readLine();
//                if (str != null) {
//                    macSerial = str.trim();// 去空格
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        try {
//            if (macSerial == null || "".equals(macSerial)) {
//                try {
//                    return loadFileAsString("/sys/class/net/eth0/address")
//                            .toUpperCase().substring(0, 17);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return macSerial;
//    }
//
//    private static String loadFileAsString(String fileName) throws Exception {
//        FileReader reader = new FileReader(fileName);
//        String text = loadReaderAsString(reader);
//        reader.close();
//        return text;
//    }
//
//    private static String loadReaderAsString(Reader reader) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        char[] buffer = new char[4096];
//        int readLength = reader.read(buffer);
//        while (readLength >= 0) {
//            builder.append(buffer, 0, readLength);
//            readLength = reader.read(buffer);
//        }
//        return builder.toString();
//    }
//    //============================================获取MAC===========================================
//
//    /**
//     * IP
//     *
//     * @return
//     */
//    public static String getIpAddress(Context context) {
//        try {
//            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            int ipAddress = info.getIpAddress();
//            String ip = intToIp(ipAddress);
//            if (ip == null || ip.equals("") || ip.equals("0.0.0.0")) {
//                ip = getLocalIpAddress();
//            }
//            return ip;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    /**
//     * 获取本地IP的方法
//     *
//     * @return
//     */
//    private static String getLocalIpAddress() {
//        try {
//            String ipv4;
//            List<NetworkInterface> nilist = Collections.list(NetworkInterface
//                    .getNetworkInterfaces());
//            for (NetworkInterface ni : nilist) {
//                List<InetAddress> ialist = Collections.list(ni
//                        .getInetAddresses());
//                for (InetAddress address : ialist) {
//                    if (!address.isLoopbackAddress()
//                            && (address instanceof Inet4Address)) {
//                        ipv4 = address.getHostAddress();
//                        return ipv4;
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 格式化ip地址
//     *
//     * @param i
//     * @return
//     */
//    private static String intToIp(int i) {
//        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
//                + "." + (i >> 24 & 0xFF);
//    }
//
//    /**
//     * 检查当前终端Brand是否是generic
//     *
//     * @return
//     */
//    public static boolean checkBrandGeneric() {
//        if ("generic".equalsIgnoreCase(Build.BRAND)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 获取手机型号
//     *
//     * @return
//     */
//    public static String getBuildModel() {
//        String model = "";
//        try {
//            model = Build.MODEL;//手机型号
//            return model;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return model;
//    }

    public static String[] getCPUInfo() {
        String[] abis;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }

        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }

        return abis;
    }

    public static boolean supportArm() {
        try {
            String[] abis = getCPUInfo();
            for (String cpuType : abis) {
                if ("armeabi-v7a".equals(cpuType) || "armeabi".equals(cpuType)) {
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
