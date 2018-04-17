package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by QK on 2018/1/23.
 * qian.kang@mobcb.com
 */

public interface UnReadService {

    @GET("api/v3/management/notice/moduleNewsCount")
    rx.Observable<ResponseBean> getUnReadCount(@QueryMap Map<String, String> map);

}
