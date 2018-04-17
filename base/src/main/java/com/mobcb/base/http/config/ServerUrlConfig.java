package com.mobcb.base.http.config;

public interface ServerUrlConfig extends ServerUrlDevelop {

    /**
     * 接口主URL地址
     */
    String HTTP_BASE_URL = MAIN_SCHEMA + MAIN_DOMAIN;

    /**
     * 地图下载服务器
     */
    String MAIN_MAP_PATH = HTTP_BASE_URL + "api/v3/";

    /**
     * 地图导航接口
     */
    String MAP_NAVIGATION_URL = HTTP_BASE_URL + "mapserver-bop/navigation";

    /**
     * 地图定位接口
     */
    String MAP_LOCATE_URL = HTTP_BASE_URL + "mapserver-bop/navigation";

}
