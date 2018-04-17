package com.mobcb.base.http.api.download;

/**
 * @author : Shiyaozu
 * @version : [V1.0.0]
 * @date : 2017/6/9 0008
 * @desc : [文件下载监听]
 */
public interface DownloadProgressListener {
    /**
     * 文件下载进度回调
     * @param bytesRead 文件已下载量
     * @param contentLength 文件总量
     * @param done 是否完成
     */
    void update(long bytesRead, long contentLength, boolean done);
}