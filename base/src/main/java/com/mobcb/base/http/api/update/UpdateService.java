package com.mobcb.base.http.api.update;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author shiyaozu
 * @version ${version}
 * @date 2017/7/27
 * @desc [升级接口]
 */

public interface UpdateService {
    @GET("api/v3/app/update")
    Observable<ResponseBean> checkUpdateClient(@QueryMap Map<String, Object> params);
}
