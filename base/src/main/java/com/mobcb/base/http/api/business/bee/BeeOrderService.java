package com.mobcb.base.http.api.business.bee;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author ZGQ
 * @version [V1.0.0]
 * @date 2017/8/30
 * @desc [描述相关类/方法]
 */
public interface BeeOrderService {

    /**
     * 根据状态获取各自状态下的新订单
     * @param map
     * @return
     */
    @POST("api/v3/shopCar/selectByStatus")
    Observable<ResponseBean> getBeeOrderListByStatus(@Body Map<String, Object> map);

    /**
     * 修改订单状态
     * @param map
     * @return
     */
    @POST("api/v3/shopCar/changeStatus")
    Observable<ResponseBean> changeOrderStatus(@Body Map<String, Object> map);

    /**
     * 获取所有新订单的个数
     * @return
     */
    @POST("api/v3/shopCar/getNotReadRec")
    Observable<ResponseBean> getBeeNewOrderCount(@Body Object object);

    /**
     * 设置新订单已读状态
     * @param object
     * @return
     */
    @POST("api/v3/shopCar/setRiderReadRec")
    Observable<ResponseBean> setNewOrderRead(@Body Object object);
}
