package com.mobcb.base.http.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/11/28 0028
 * @desc : [相关类/方法]
 */

public interface WeatherService {
    /**
     * 获取对应城市的天气
     *
     * @param queryMap
     * @return
     * https://api.mobcb.com/weatherapi/api
     * http://192.168.1.78:8080/weatherapi/api
     */
    @GET("https://api.mobcb.com/weatherapi/api")
    Observable<Object> getCityWeather(@QueryMap Map<String, String> queryMap);

    /**
     * 利用百度地图服务获取当前城市信息
     * @return
     * http://api.map.baidu.com/geocoder?output=json&location=23.131427,113.379763&ak=esNPFDwwsXWtsQfw4NMNmur1
     */
    @GET("http://api.map.baidu.com/geocoder")
    Observable<Object> getCityByLocation(@QueryMap Map<String,Object> map);
}
