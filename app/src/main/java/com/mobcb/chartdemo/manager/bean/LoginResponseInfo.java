package com.mobcb.chartdemo.manager.bean;

/**
 * Created by wanggh on 2015/12/8.
 */
public class LoginResponseInfo {


    /**
     * id : 22
     * username : test1
     * nickname : 王丹丹
     * phone : 8618655896051
     * group : {"id":4,"name":"商场管理员","description":"商场管理员","remark":"商管"}
     * shopId : 340
     * mallId : 1
     * lastLoginTime : 1457927779
     * accessToken : test11wHQxR53
     * redirectTo : http://192.168.0.13:8080/mportal-admin/admin/assistant/dining/list
     * shopIcon : http://192.168.0.13:4869/04c192894d46657748be0026ae76e08d?f=JPEG
     * isMall : false
     * shopName : TEST
     * mallName : 新百汇
     * deptId : 3
     */

    private String id;
    private String username;
    private String nickname;
    private String phone;
    /**
     * id : 4
     * name : 商场管理员
     * description : 商场管理员
     * remark : 商管
     */

    private GroupEntity group;
    private String shopId;
    private String mallId;
    private int lastLoginTime;
    private String accessToken;
    private String redirectTo;
    private String shopIcon;
    private boolean isMall;
    private String shopName;
    private String mallName;
    private String deptId;
    private String headImg;
    private int loginSessionFlag;

    public int getLoginSessionFlag() {
        return loginSessionFlag;
    }

    public void setLoginSessionFlag(int loginSessionFlag) {
        this.loginSessionFlag = loginSessionFlag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }

    public void setLastLoginTime(int lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public void setIsMall(boolean isMall) {
        this.isMall = isMall;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public String getShopId() {
        return shopId;
    }

    public String getMallId() {
        return mallId;
    }

    public int getLastLoginTime() {
        return lastLoginTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public String getShopIcon() {
        return shopIcon;
    }

    public boolean isIsMall() {
        return isMall;
    }

    public String getShopName() {
        return shopName;
    }

    public String getMallName() {
        return mallName;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public static class GroupEntity {
        private String id;
        private String name;
        private String description;
        private String remark;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getRemark() {
            return remark;
        }
    }
}
