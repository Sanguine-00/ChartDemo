package com.mobcb.base.http.api.business.returnmanagement;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/17 0017
 * @desc : [退货授权]
 */

public interface ReturnManagementService {

    /**
     * 查询授权信息
     * @param params
     * @return
     */
    @GET("api/v3/authentication/auth/info")
    Observable<ResponseBean> getAuthInfo(@QueryMap Map<String,Object> params);

    /**
     * 授权
     * @param params
     * @return
     */
    @GET("api/v3/authentication/auth/grant")
    Observable<ResponseBean> grant(@QueryMap Map<String,Object> params);
}
