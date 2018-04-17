package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author QK
 * @version ${version}
 * @date 2017/8/1
 * @desc [描述相关类/方法]
 */

public interface MonitorService {

    @GET("api/v3/malls/{mallId}/cameras")
    Observable<ResponseBean> getCameras(@Path("mallId") String mallId);
}
