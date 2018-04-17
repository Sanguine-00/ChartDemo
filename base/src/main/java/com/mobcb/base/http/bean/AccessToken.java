package com.mobcb.base.http.bean;

/**
 * Created by MyStudio on 2017/4/19.
 */

public class AccessToken {
    private String workKey;
    private String accessToken;
    private long registerTime;
    private long sessionValiditySeconds;

    public String getWorkKey() {
        return workKey;
    }

    public void setWorkKey(String workKey) {
        this.workKey = workKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getSessionValiditySeconds() {
        return sessionValiditySeconds;
    }

    public void setSessionValiditySeconds(long sessionValiditySeconds) {
        this.sessionValiditySeconds = sessionValiditySeconds;
    }
}
