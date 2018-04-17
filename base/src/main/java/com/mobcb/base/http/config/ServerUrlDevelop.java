package com.mobcb.base.http.config;

/**
 * Created by lvmenghui
 * on 2018/03/29.
 * ↓↓↓↓↓ 开发环境
 */
public interface ServerUrlDevelop extends ServerUrlBase {

    /**
     * 接口SCHEMA
     */
    String MAIN_SCHEMA = SCHEMA_HTTP;

    //↓↓↓↓↓ 测试环境
    String MAIN_DOMAIN = "192.168.0.51:11111/";

}
