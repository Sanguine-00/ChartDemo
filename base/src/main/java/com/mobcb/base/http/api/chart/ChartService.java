package com.mobcb.base.http.api.chart;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Sanguine on 2018/4/3.
 */

public interface ChartService {

    /**
     * 获取可视化报表列表
     *
     * @return
     */
    @GET("api/v3/report/getSettingReport/getList")//http://192.168.1.94:10888/
    Observable<ResponseBean> getChartList(@Query("moduleId") String moduleId);

    /**
     * 获取可视化报表列表
     *
     * @return
     */
    @POST("api/v3/report/getSettingReport/getData")//http://192.168.1.94:10888/
    Observable<ResponseBean> getChart(@Body Map<String, Object> map);
}
