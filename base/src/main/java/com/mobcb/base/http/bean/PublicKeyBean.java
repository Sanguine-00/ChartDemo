package com.mobcb.base.http.bean;

/**
 * Created by MyStudio on 2017/4/18.
 */

public class PublicKeyBean extends BaseResponseBean {
    private PublicKey body;

    public PublicKey getBody() {
        return body;
    }

    public void setBody(PublicKey body) {
        this.body = body;
    }
}
