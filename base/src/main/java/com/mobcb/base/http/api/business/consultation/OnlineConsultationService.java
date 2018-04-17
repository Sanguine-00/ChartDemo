package com.mobcb.base.http.api.business.consultation;


import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/17 0017
 * @desc : [在线咨询]
 */

public interface OnlineConsultationService {

    /**
     * 对话列表(未回复对话、已回复对话、已关闭对话)
     * @param params
     * @return
     */
    @GET("api/v3/management/cscenter/list")
    Observable<ResponseBean> getConsultationList(@QueryMap Map<String,Object> params);

    /**
     * 和用户对话详情(对话消息列表)
     * @param params
     * @return
     */
    @GET("api/v3/management/cscenter/{memberId}")
    Observable<ResponseBean> getUserConsulationList(@Path("memberId")String managerId, @QueryMap Map<String,Object> params);

    /**
     * 回复
     * @param object
     * @return
     */
    @POST("api/v3/management/cscenter/reply")
    Observable<ResponseBean> reply(@Body Object object);

    /**
     * 关闭
     * @param object
     * @return
     */
    @POST("api/v3/management/cscenter/close")
    Observable<ResponseBean> close(@Body Object object);

    /**
     * 消息已读未读
     * @param reportId
     * @param object
     * @return
     */
    @POST("api/v3/management/commonread//{reportId}")
    Observable<ResponseBean> readMessage(@Path("reportId")String reportId, @Body Object object);
}
