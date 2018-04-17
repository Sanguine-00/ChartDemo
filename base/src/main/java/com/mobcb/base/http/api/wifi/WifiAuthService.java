package com.mobcb.base.http.api.wifi;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author lvmenghui
 * @version [V1.0.0]
 * @date 2017/5/22 0022
 * @desc [wifi认证服务]
 */
public interface WifiAuthService {

    /**
     * 请求网站，检查是否可正常上网
     *
     * @return
     */
    @GET
    Observable<ResponseBody> requestUrl(@Url String url);

    /**
     * 进行Wifi认证
     *
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> authWifi(@Url String url, @QueryMap Map<String, String> map);

}
