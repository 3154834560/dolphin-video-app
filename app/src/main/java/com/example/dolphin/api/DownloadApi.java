package com.example.dolphin.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author 王景阳
 * @date 2022/11/27 15:25
 */
public interface DownloadApi {

    /**
     *
     *文件下载
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url);
}
