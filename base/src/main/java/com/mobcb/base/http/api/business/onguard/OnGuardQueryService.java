package com.mobcb.base.http.api.business.onguard;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/9/12 0012
 * @desc : [在岗查询]
 */

public interface OnGuardQueryService {

    /**
     * 根据姓名或手机号获取员工信息列表
     * @param params
     * @return
     */
    @GET("api/v3/management/mac")
    Observable<ResponseBean> getStaffList(@QueryMap Map<String,Object> params);
}
