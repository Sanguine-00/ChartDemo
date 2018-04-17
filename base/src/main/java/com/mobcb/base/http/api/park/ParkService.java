package com.mobcb.base.http.api.park;

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
 * @date : 2017/7/27 0027
 * @desc : [停车券]
 */

public interface ParkService {
    /**
     * 查询会员信息
     * @param params
     * @return
     */
    @GET("api/v3/member/info")
    Observable<ResponseBean> getMemberInfo(@QueryMap Map<String, Object> params);

    /**
     * 获取停车券类型
     * @return
     */
    @GET("api/v3/parkingticket/tickets")
    Observable<ResponseBean> getParkTicketTypes(@QueryMap Map<String, String> map);

    /**
     * 提取停车券发放配置
     * @param shopId
     * @return
     */
    @GET("api/v3/parkingticket/shop/{shopId}/config")
    Observable<ResponseBean> getParkingConfig(@Path("shopId") String shopId);

    /**
     * 账户余额
     * @param shopId
     * @return
     */
    @GET("api/v3/parkingticket/shop/{shopId}/balance")
    Observable<ResponseBean> getParkAccountBalance(@Path("shopId") String shopId);

    /**
     * 发放停车券
     * @param object
     * @return
     */
    @POST("api/v3/parkingticket/member")
    Observable<ResponseBean> sendOutParkTickets(@Body Object object);

    /**
     * 停车券月度发放记录详情
     * @param shopId
     * @param params
     * @return
     */
    @GET("api/v3/parkingticket/shop/{shopId}/detail")
    Observable<ResponseBean> getMonthParkTicket(@Path("shopId") String shopId, @QueryMap Map<String, Object> params);

}
