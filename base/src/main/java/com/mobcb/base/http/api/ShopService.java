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
 * @date 2017/5/10 0010
 * @desc [商户相关网络请求接口]
 */
public interface ShopService {

    /**
     * 商户列表
     * @param params
     * @return
     * @author Shiyaozu
     */
    @GET("api/v3/shop/list")
    Observable<ResponseBean> getShopList(@QueryMap Map<String, Object> params);

    /**
     * 商户详情
     * @param shopId
     * @param map
     * @return
     */
    @GET("api/v3/shop/{shopId}")
    Observable<ResponseBean> getShopDetail(@Path("shopId")String shopId, @QueryMap Map<String,String> map);

}