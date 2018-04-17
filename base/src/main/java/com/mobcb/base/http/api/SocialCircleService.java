package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/5/18 0018
 * @desc [圈子模块相关接口]
 */

public interface SocialCircleService {
    /**
     * 标签列表
     *
     * @return
     */
    @GET("api/v3/weibo/tag/list")
    Observable<ResponseBean> getTagList(@QueryMap Map<String, Object> map);

    /**
     * 最新消息列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/msg/list/new")
    Observable<ResponseBean> getNewMessages(@QueryMap Map<String, Object> map);

    /**
     * 最热消息列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/msg/list/hot")
    Observable<ResponseBean> getHotMessages(@QueryMap Map<String, Object> map);

    /**
     * 已关注消息列表(查询我关注的人发布的信息的列表)
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/msg/list/followed")
    Observable<ResponseBean> getFollowedMessages(@QueryMap Map<String, Object> map);

    /**
     * 店长说消息列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/msg/list/managersay")
    Observable<ResponseBean> getManagerSay(@QueryMap Map<String, Object> map);

    /**
     * 某用户主页的列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/msg/list/Detail")
    Observable<ResponseBean> getMemeberList(@QueryMap Map<String, Object> map);

    /**
     * 点赞
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/praise")
    Observable<ResponseBean> praise(@Body Object object);

    /**
     * 取消点赞
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/unpraise")
    Observable<ResponseBean> unPraise(@Body Object object);

    /**
     * 检查是否点赞过
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/praise/check")
    Observable<ResponseBean> checkPrise(@Body Object object);

    /**
     * 查询圈子用户信息
     *
     * @param memberId
     * @return
     */
    @GET("api/v3/weibo/member/{memberId}")
    Observable<ResponseBean> circleMemberInfo(@Path("memberId") String memberId);

    /**
     * 查询圈子商管或商户信息
     *
     * @param managerId
     * @return
     */
    @GET("api/v3/weibo/manager/{managerId}")
    Observable<ResponseBean> circleManager(@Path("managerId") String managerId);


    /**
     * 用户未读消息数
     * 会员的私人消息数目，可以查询到其他用户评论我、@我、点赞我的等信息类型各自数目
     *
     * @param memberId
     * @return
     */
    @GET("api/v3/weibo/member/{memberId}/private/count")
    Observable<ResponseBean> getUnReadMsgCount(@Path("memberId") String memberId);

    /**
     * 商管或商户未读消息数
     * 会员的私人消息数目，可以查询到其他用户评论我、@我、点赞我的等信息类型各自数目
     *
     * @param managerId
     * @return
     */
    @GET("api/v3/weibo/manager/{managerId}/private/count")
    Observable<ResponseBean> getMangerUnReadMsgCount(@Path("managerId") String managerId);

    /**
     * 会员私人消息；包括@我，评论，赞，回复
     *
     * @param memberId
     * @return
     */
    @GET("api/v3/weibo/member/{memberId}/private")
    Observable<ResponseBean> getMemberPrivateMsg(@Path("memberId") String memberId, @QueryMap Map<String, Object> map);

    /**
     * 商管或者商户私人消息；包括@我，评论，赞，回复
     *
     * @param managerId
     * @return
     */
    @GET("api/v3/weibo/manager/{managerId}/private")
    Observable<ResponseBean> getManagerPrivateMsg(@Path("managerId") String managerId, @QueryMap Map<String, Object> map);

    /**
     * 信息详情
     *
     * @param msgId
     * @return
     */
    @GET("api/v3/weibo/msg/{msgId}")
    Observable<ResponseBean> getMessageDetail(@Path("msgId") String msgId);

    /**
     * 添加评论
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/comment")
    Observable<ResponseBean> addComment(@Body Object object);

    /**
     * 获取评论列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/comment/list")
    Observable<ResponseBean> getCommentList(@QueryMap Map<String, Object> map);

    /**
     * 获取点赞列表
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/praise/list")
    Observable<ResponseBean> getPraisedList(@QueryMap Map<String, Object> map);

    /**
     * （私人）消息设置为已读
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/private/read")
    Observable<ResponseBean> setMessageRead(@Body Object object);

    /**
     * 获取关注列表（我关注的人的列表）
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/follow/list")
    Observable<ResponseBean> getFollowList(@QueryMap Map<String, Object> map);

    /**
     * 获取用户粉丝列表（关注我的人的列表）
     *
     * @param map
     * @return
     */
    @GET("api/v3/weibo/fans/list")
    Observable<ResponseBean> getFansList(@QueryMap Map<String, Object> map);

    /**
     * 检查是否已经关注
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/follow/check")
    Observable<ResponseBean> checkFollow(@Body Object object);

    /**
     * 关注
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/follow")
    Observable<ResponseBean> followMember(@Body Object object);

    /**
     * 取消关注
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/unfollow")
    Observable<ResponseBean> unFollowMember(@Body Object object);

    /**
     * 发布消息
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/publish")
    Observable<ResponseBean> publishMessage(@Body Object object);

    /**
     * 删除消息
     *
     * @param msgId
     * @return
     */
    @DELETE("api/v3/weibo/msg/{msgId}")
    Observable<ResponseBean> deleteMessage(@Path("msgId") String msgId);

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @DELETE("api/v3/weibo/comment/{commentId}")
    Observable<ResponseBean> deleteComment(@Path("commentId") String commentId);

    /**
     * 增加访问量
     *
     * @param object
     * @return
     */
    @POST("api/v3/weibo/msg/visit")
    Observable<ResponseBean> visit(@Body Object object);
}
