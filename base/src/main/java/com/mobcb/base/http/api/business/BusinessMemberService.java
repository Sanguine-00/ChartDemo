package com.mobcb.base.http.api.business;

import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/6/15 0015
 * @desc : [商管用户接口]
 */

public interface BusinessMemberService {

    /**
     * 商管端登录
     * @param object
     * @return
     */
    @POST("api/v3/management/mall/login")
    Observable<ResponseBean> managerLogin(@Body Object object);

    /**
     * 头像修改
     * @param memberId
     * @param body
     * @return
     */
    @PUT("api/v3/management/mall/{managerId}/info")
    Observable<ResponseBean> modifyAvatar(@Path("managerId")String memberId, @Body Object body);

    /**
     * 密码修改
     * @param managerId
     * @param object
     * @return
     */
    @PUT("/api/v3/management/mall/{managerId}/password")
    Observable<ResponseBean> modifyPassword(@Path("managerId")String managerId,@Body Object object);

    /**
     * 登出
     *
     * @param body
     * @return
     */
    @POST("api/v3/member/logout")
    Observable<ResponseBean> memberLoginOut(@Body Object body);
}
