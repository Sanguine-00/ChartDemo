package com.mobcb.base.http.api.business.notice;


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
 * @date : 2017/7/10 0010
 * @desc : [通知公告]
 */

public interface NoticeService {

    /**
     * 系统消息列表
     * @return
     */
    @GET("api/v3/management/notice")
    Observable<ResponseBean> getSystemNotice(@QueryMap Map<String,Object>params);

    /**
     * 商管通知列表
     * @param params
     * @return
     */
    @GET("api/v3/management/mall/notice/list")
    Observable<ResponseBean> getNoticeList(@QueryMap Map<String,Object>params);

    /**
     * 公告详情
     * @param id
     * @param params
     * @return
     */
    @GET("api/v3/management/mall/notice/{id}")
    Observable<ResponseBean> noticeDetail(@Path("id")String id,@QueryMap Map<String,Object>params);

    /**
     * 通知公告读取状态
     * @param noticeId
     * @param object
     * @return
     */
    @POST("api/v3/management/mall/notice/{noticeId}")
    Observable<ResponseBean> noticeRead(@Path("noticeId")String noticeId, @Body Object object);
}
