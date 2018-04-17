package com.mobcb.base.http.api.business.notice;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/7/17 0017
 * @desc : [系统通知]
 */

public interface SystemNoticeService {

    /**
     * 系统通知列表
     * @param params
     * @return
     */
    @GET("api/v3/management/notice")
    Observable<ResponseBean> getSystemNoticeList(@QueryMap Map<String,Object> params);

    /**
     * 系统消息
     * @param params
     * @return
     */
    @GET("api/v3/management/notice/count")
    Observable<ResponseBean> getNoticeCount(@QueryMap Map<String,Object> params);

    /**
     * 系统通知详情
     * @param newsId
     * @param map
     * @return
     */
    @GET("api/v3/management/notice/detail/{newsId}")
    Observable<ResponseBean> noticeDetail(@Path("newsId")String newsId,@QueryMap Map<String,Object> map);

    /**
     * 系统消息已读状态修改
     * @param object
     * @return
     */
    @PUT("api/v3/management/notice")
    Observable<ResponseBean> modifyNoticeStatus(@Body Object object);
}
