package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author lvmenghui
 * @version [V1.0.0]
 * @date 2017/7/19 0019
 * @desc [地图下载相关接口]
 */
public interface MapInfoService {

    /**
     * 获取地图列表
     *
     * @param mallId
     * @return
     */
    @GET("api/v3/mall/{mallId}/map/floors")
    Observable<ResponseBean> getMapFloors(@Path("mallId") String mallId);


    /**
     * 获取地图文件下载列表
     *
     * @param mallId
     * @param params
     * @return
     */
    @GET("api/v3/mall/{mallId}/map/svgs")
    //?floorId={floorId} floorId为可选参数，此处不使用
    Observable<ResponseBean> getMapFileList(@Path("mallId") String mallId, @QueryMap Map<String, String> params);

}