package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/11/30 0030
 * @desc : [运营报表相关]
 */

public interface OperationReportService {

    /**
     * 获取销售额
     *
     * @return
     */
    @GET("api/v3/shanghe/checkout/salesReport")
    Observable<ResponseBean> getSalesReport();

    /**
     * 客流
     *
     * @return
     */
    @GET("api/v3/shanghe/getPlazaPassenger")
    Observable<ResponseBean> getPassengerFlow(@QueryMap Map<String, Object> map);

    /**
     * 车流量
     *
     * @return
     */
    @GET("api/v3/shanghe/parking/getParkingUseCount")
    Observable<ResponseBean> getTraffic(@QueryMap Map<String, Object> params);


}
