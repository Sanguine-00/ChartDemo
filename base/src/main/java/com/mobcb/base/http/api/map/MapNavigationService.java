package com.mobcb.base.http.api.map;


import com.mobcb.base.http.config.ServerUrlConfig;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author lvmenghui
 * @version [V1.0.0]
 * @date 2017/8/29 0001
 * @desc [地图导航数据接口]
 */
public interface MapNavigationService {

    /**
     * 地图导航接口地址
     *
     * @param params
     * @return
     */
    @GET(ServerUrlConfig.MAP_NAVIGATION_URL)
    Observable<ResponseBody> getNavigationLine(@QueryMap Map<String, String> params);

    /**
     * 地图导航接口地址
     *
     * @param params
     * @return
     */
    @GET(ServerUrlConfig.MAP_LOCATE_URL)
    Observable<ResponseBody> getLocateData(@QueryMap Map<String, String> params);


}