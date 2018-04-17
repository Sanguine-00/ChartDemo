package com.mobcb.base.http.api.business.coupon;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/25 0025
 * @desc : [优惠券相关接口]
 */

public interface BMCouponService {
    /**
     * 优惠券列表
     * @param params
     * @return
     */
    @GET("api/v3/coupon/list")
    Observable<ResponseBean> getCouponList(@QueryMap Map<String, Object> params);

    /**
     * 查询会员信息
     * @param params
     * @return
     */
    @GET("api/v3/member/info")
    Observable<ResponseBean> getMemberInfo(@QueryMap Map<String, Object> params);

    /**
     * 发放优惠券
     * @param couponId
     * @param object
     * @return
     */
    @POST("api/v3/coupon/{couponId}/release ")
    Observable<ResponseBean> sendOut(@Path("couponId") String couponId, @Body Object object);

    /**
     * 优惠券核销
     * @param object
     * @return
     */
    @POST("api/v3/coupon/verify")
    Observable<ResponseBean> verification(@Body Object object);

    /**
     * 获取商户列表
     * @param params
     * @return
     */
    @GET("api/v3/shop/list")
    Observable<ResponseBean> getShopList(@QueryMap Map<String,Object> params);

    /**
     * 优惠券核销记录
     * @param params
     * @return
     */
    @GET("api/v3/coupon/writeoff/data")
    Observable<ResponseBean> verificationRecord(@QueryMap Map<String,Object> params);

    /**
     * 查询优惠券
     * @param couponId
     * @return
     */
    @GET("api/v3/coupon/sn/{couponId}")
    Observable<ResponseBean> getCouponDetail(@Path("couponId")String couponId,@QueryMap Map<String,Object> params);

    /**
     * 优惠券发放记录
     * @param params
     * @return
     */
    @GET("api/v3/coupon/release/records")
    Observable<ResponseBean> getCouponReleaseRecord(@QueryMap Map<String,Object> params);

    /**
     * 获取已核销的优惠券种类
     *
     * @param shopId
     * @return
     */
    @GET("api/v3/coupon/writeOff/{shopId}/coupon/list")
    Observable<ResponseBean> getCouponRecordList(@Path("shopId") String shopId);
}
