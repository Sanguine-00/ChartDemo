package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.AccessTokenBean;
import com.mobcb.base.http.bean.ApiBody;
import com.mobcb.base.http.bean.PublicKeyBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lvmenghui
 * on 2018/3/29.
 */
public interface AuthService {

    @GET("api/v3/securities/rsa/pubkey")
    Observable<PublicKeyBean> getPubKey(@Query("deviceId") String deviceId, @Query("appKey") String appKey, @Query("rnd") String rnd);

    @POST("api/v3/securities/devices/register")
    Observable<AccessTokenBean> getAccessToken(@Query("deviceId") String deviceId, @Query("appKey") String appKey
            , @Query("rnd") String rnd, @Body ApiBody requestBody);

}