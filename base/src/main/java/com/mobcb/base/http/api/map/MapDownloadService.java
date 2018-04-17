package com.mobcb.base.http.api.map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author : QK
 * @version : [V1.0.0]
 * @date : 2017/6/8 0008
 * @desc : [文件下载]
 */

public interface MapDownloadService {

    /**
     * 地图文件获取
     *
     * @param downloadUrl
     * @return
     */
    @GET
    Observable<ResponseBody> downloadMap(@Url String downloadUrl);
}
