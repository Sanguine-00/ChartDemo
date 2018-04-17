package com.mobcb.base.helper;

import android.content.Intent;
import android.text.TextUtils;

import com.mobcb.base.R;
import com.mobcb.base.util.ActivityUtils;
import com.mobcb.base.util.DateUtils;
import com.mobcb.base.util.SPUtils;
import com.mobcb.base.util.ToastUtils;

/**
 * Created by lvmenghui
 * on 2018/3/27.
 */

public class LoginHelper {
    //管理员id
    private static String KEY_MANAGER_ID = "key.login.manager.id";
    //管理员名称
    private static String KEY_MANAGER_NAME = "key.login.manager.name";
    //管理员昵称
    private static String KEY_MANAGER_NICK_NAME = "key.login.manager.nick.name";
    //管理员头像
    private static String KEY_MANAGER_HEAD_IMG = "key.login.manager.head.img";
    //管理员手机号
    private static String KEY_MANAGER_PHONE = "key.login.manager.phone";
    //管理员密码
    private static String KEY_MANAGER_PASSWORD = "key.login.manager.pwd";
    //登录会话
    private static String KEY_MANAGER_LOGIN_SESSION = "key.login.manager.session";
    //上次登录时间
    private static String KEY_MANAGER_TIME_LAST = "key.login.manager.last.time";
    //商户id
    private static String KEY_SHOP_ID = "key.shop.id";
    //商户名称
    private static String KEY_SHOP_NAME = "key.shop.name";
    //商户图标
    private static String KEY_SHOP_ICON = "key.shop.icon";
    //商场id
    private static String KEY_MALL_ID = "key.mall.id";
    //部门id
    private static String KEY_MANAGER_DEPT_ID = "key.manager.dept.id";
    //是否是商场
    private static String KEY_IS_MALL = "key.is.mall";

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        return LoginHelperHolder.INSTANCE;
    }


    public String getManagerId() {
        return SPUtils.getInstance().getString(KEY_MANAGER_ID);
    }

    public void saveManagerId(String managerId) {
        SPUtils.getInstance().put(KEY_MANAGER_ID, managerId, true);
    }

    public String getManagerName() {
        return SPUtils.getInstance().getString(KEY_MANAGER_NAME);
    }

    public void saveManagerName(String managerName) {
        SPUtils.getInstance().put(KEY_MANAGER_NAME, managerName, true);
    }

    public String getManagerNickName() {
        return SPUtils.getInstance().getString(KEY_MANAGER_NICK_NAME);
    }

    public void saveManagerNickName(String managerNickName) {
        SPUtils.getInstance().put(KEY_MANAGER_NICK_NAME, managerNickName, true);
    }

    public String getManagerHeadImg() {
        return SPUtils.getInstance().getString(KEY_MANAGER_HEAD_IMG);
    }

    public void saveManagerHeadImg(String managerHeadImg) {
        SPUtils.getInstance().put(KEY_MANAGER_HEAD_IMG, managerHeadImg, true);
    }

    public String getManagerPhone() {
        return SPUtils.getInstance().getString(KEY_MANAGER_PHONE);
    }

    public void saveManagerPhone(String managerPhone) {
        SPUtils.getInstance().put(KEY_MANAGER_PHONE, managerPhone, true);
    }

    public String getManagerPassword() {
        return SPUtils.getInstance().getString(KEY_MANAGER_PASSWORD);
    }

    public void saveManagerPassword(String managerPassword) {
        SPUtils.getInstance().put(KEY_MANAGER_PASSWORD, managerPassword, true);
    }

    public int getManagerLoginSession() {
        return SPUtils.getInstance().getInt(KEY_MANAGER_LOGIN_SESSION);
    }

    public void saveManagerLoginSession(int managerLoginSession) {
        SPUtils.getInstance().put(KEY_MANAGER_LOGIN_SESSION, managerLoginSession, true);
    }

    public long getManagerTimeLast() {
        return SPUtils.getInstance().getLong(KEY_MANAGER_TIME_LAST);
    }

    public void saveManagerTimeLast(long managerTimeLast) {
        SPUtils.getInstance().put(KEY_MANAGER_TIME_LAST, managerTimeLast, true);
    }

    public String getShopId() {
        return SPUtils.getInstance().getString(KEY_SHOP_ID);
    }

    public void saveShopId(String shopId) {
        SPUtils.getInstance().put(KEY_SHOP_ID, shopId, true);
    }

    public String getShopName() {
        return SPUtils.getInstance().getString(KEY_SHOP_NAME);
    }

    public void saveShopName(String shopName) {
        SPUtils.getInstance().put(KEY_SHOP_NAME, shopName, true);
    }

    public String getShopIcon() {
        return SPUtils.getInstance().getString(KEY_SHOP_ICON);
    }

    public void saveShopIcon(String shopIcon) {
        SPUtils.getInstance().put(KEY_SHOP_ICON, shopIcon, true);
    }

    public String getMallId() {
        return SPUtils.getInstance().getString(KEY_MALL_ID);
    }

    public void saveMallId(String mallId) {
        SPUtils.getInstance().put(KEY_MALL_ID, mallId, true);
    }

    public String getManagerDeptId() {
        return SPUtils.getInstance().getString(KEY_MANAGER_DEPT_ID);
    }

    public void saveManagerDeptId(String managerDeptId) {
        SPUtils.getInstance().put(KEY_MANAGER_DEPT_ID, managerDeptId, true);
    }

    public boolean getIsMall() {
        return SPUtils.getInstance().getBoolean(KEY_IS_MALL);
    }

    public void saveIsMall(boolean isMall) {
        SPUtils.getInstance().put(KEY_IS_MALL, isMall, true);
    }


    /**
     * 保存登录之后获取的信息
     *
     * @param managerId
     * @param password
     * @param name
     * @param nickname
     * @param headImg
     * @param shopId
     * @param shopName
     * @param shopIcon
     * @param mallId
     * @param mDeptId
     * @param loginSessionFlag
     * @param isMall
     */
    public void saveUserInfo(String managerId,
                             String password,
                             String name,
                             String nickname,
                             String headImg,
                             String shopId,
                             String shopName,
                             String shopIcon,
                             String mallId,
                             String mDeptId,
                             int loginSessionFlag,
                             boolean isMall) {
        saveManagerId(managerId);
        saveManagerName(name);
        saveManagerNickName(nickname);
        saveManagerHeadImg(headImg);
        saveManagerLoginSession(loginSessionFlag);
        saveManagerPassword(password);
        saveShopId(shopId);
        saveShopIcon(shopIcon);
        saveShopName(shopName);
        saveMallId(mallId);
        saveIsMall(isMall);
        saveManagerDeptId(mDeptId);
        saveManagerTimeLast(getTime(System.currentTimeMillis()));


    }

    /**
     * 是否登录
     *
     * @return
     */
    public Boolean isLogin() {
        String userName = SPUtils.getInstance().getString(KEY_MANAGER_NAME, "");
        String userPassword = SPUtils.getInstance().getString(KEY_MANAGER_PASSWORD, "");
        return !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword);
    }


    /**
     * 处理时间为日期
     */
    public long getTime(long time) {
        return DateUtils.getTimeLong(DateUtils.toDateDD(time / 1000), DateUtils.TYPE_02);
    }

    /**
     * 检测会话是否有效
     *
     * @return true=有效,false=无效
     */
    public boolean checkSession() {
        boolean result = true;
        int loginSessionFlag = SPUtils.getInstance().getInt(KEY_MANAGER_LOGIN_SESSION, 1);
        if (loginSessionFlag == 1) {
            if (getManagerTimeLast() != getTime(System.currentTimeMillis())) {
                ToastUtils.showShort(R.string.base_login_failure_all);
                clearSP();
                Intent intent = new Intent();
                intent.setAction("com.mobcb.chartdemo.manager.activity.LoginActivity");
                //不加下面这行也行，因为intent的这个属性默认值即系Intent.CATEGORY_DEFAULT
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ActivityUtils.startActivity(intent);
                result = false;
            }
        }
        return result;
    }

    public void clearSP() {
        SPUtils.getInstance().clear();
    }

    /**
     * Effective Java 第一版推荐写法
     * 参考：https://www.jianshu.com/p/eb30a388c5fc
     */
    private static class LoginHelperHolder {
        private static final LoginHelper INSTANCE = new LoginHelper();
    }

}
