package com.mobcb.base.http.bean;

/**
 * Created by MyStudio on 2017/4/24.
 */

public class ResponseBean<T> extends BaseResponseBean {
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
