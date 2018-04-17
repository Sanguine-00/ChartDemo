package com.mobcb.base.http.api;

import com.mobcb.base.http.bean.ResponseBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Shiyaozu
 * @version [V1.0.0]
 * @date 2017/5/15 0015
 * @desc [上传接口]
 */

public interface UploadService {

    /**
     * 图片/文件上传
     * @param object
     * @return
     */
    @POST("api/v3/app/upload")
    Observable<ResponseBean> upload(@Body Object object);

    /**
     * 批量上传
     */
    @POST("api/v3/app/upload/multidata")
    Observable<ResponseBean> uploadMultidata(@Body Object object);

    /**
     * 设备信息上传
     * 包括deviceToken等信息
     *
     * @param object
     * @return
     */
    @POST("api/v3/appuserdevice")
    Observable<ResponseBean> updateDeviceInfo(@Body Object object);
}
