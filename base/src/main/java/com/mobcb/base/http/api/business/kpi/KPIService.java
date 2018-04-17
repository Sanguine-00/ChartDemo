package com.mobcb.base.http.api.business.kpi;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/8/24 0024
 * @desc : [相关类/方法]
 */

public interface KPIService {

    /**
     * 获取KPI列表
     * @param queryParams
     * @return
     */
    @GET("api/v3/admin/mall/manager/kpi/data")
    Observable<ResponseBean> getKPIList(@QueryMap Map<String,Object> queryParams);

    /**
     * 获取KPI明细
     * @return
     */
    @GET("api/v3/admin/mall/manager/kpi/data/detail")
    Observable<ResponseBean> getKPIDetail(@QueryMap Map<String,Object> queryParams);
}
