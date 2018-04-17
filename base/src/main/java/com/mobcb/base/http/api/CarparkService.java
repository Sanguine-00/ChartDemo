package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/5/25 0025
 * @desc [停车场]
 */

public interface CarparkService {

    /**
     * 停车须知
     * @param map
     * @return
     */
    @GET("api/v3/app/text")
    Observable<ResponseBean> getParkingNotice(@QueryMap Map<String, String> map);

    /**
     * 空的停车位数量查询
     * @param map
     * @return
     */
    @GET("api/v3/parking/empty/count")
    Observable<ResponseBean> checkEmptyParkingSpaceCount(@QueryMap Map<String, String> map);

    /**
     * 空的停车位查询
     * @param map
     * @return
     */
    @GET("api/v3/parking/empty")
    Observable<ResponseBean> checkEmptyParkingSpace(@QueryMap Map<String, String> map);

    /**
     * 根据车牌号查询停车位置
     * @param plateNumber
     * @return
     */
    @GET("api/v3/parking/car/{plateNumber}/location")
    Observable<ResponseBean> findParkingPlace(@Path("plateNumber") String plateNumber);

    /**
     * 停车券
     * @param memberId
     * @param map
     * @return
     */
    @GET("api/v3/parking/{memberId}/tickets")
    Observable<ResponseBean> parkingCoupon(@Path("memberId") String memberId, @QueryMap Map<String, Object> map);

    /**
     * 停车缴费订单查询
     * @param memberId
     * @param map
     * @return
     */
    @GET("api/v3/parking/member/{memberId}/payment")
    Observable<ResponseBean> findParkingPaymentOrder(@Path("memberId") String memberId, @QueryMap Map<String, Object> map);

}
