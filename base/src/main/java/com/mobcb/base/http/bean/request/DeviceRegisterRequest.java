package com.mobcb.base.http.bean.request;

/**
 * Created by lvmenghui
 * on 2017/4/19.
 */

public class DeviceRegisterRequest {
    private String keySeed;     //客户端随机生成，保证唯一	密钥种子	是
    private String mac;         //客户端获取	mac地址	否
    private String ip;          //客户端获取	ip地址	否
    private String clientPublicKey; //由客户端生成	客户端RSA加密公钥，base64编码	是
    //POS使用的参数
    private String terminalSn;  //客户端获取	终端硬件序号（pso机客户端必选）	否
    private String password;    //客户端输入	终端密码（pos机客户端必选），提交时使用sha256哈希后发送	否
    private String posCode;     //客户端输入	pos编码（pos机客户端必选）	否

    public String getKeySeed() {
        return keySeed;
    }

    public void setKeySeed(String keySeed) {
        this.keySeed = keySeed;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public String getTerminalSn() {
        return terminalSn;
    }

    public void setTerminalSn(String terminalSn) {
        this.terminalSn = terminalSn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }
}
