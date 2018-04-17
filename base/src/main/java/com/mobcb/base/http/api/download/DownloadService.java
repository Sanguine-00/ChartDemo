package com.mobcb.base.http.api.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author : QK
 * @version : [V1.0.0]
 * @date : 2017/6/8 0008
 * @desc : [文件下载]
 */

public interface DownloadService {

    /**
     * 文件下载
     *
     * @param downloadUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String downloadUrl);
}
