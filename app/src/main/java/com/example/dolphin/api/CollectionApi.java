package com.example.dolphin.api;

import com.example.dolphin.application.dto.input.CollectionInput;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 收藏接口
 *
 * @author 王景阳
 * @date 2022/11/20 13:19
 */
public interface CollectionApi {
    /**
     * 获取指定用户的所以收藏
     */
    @GET("dolphin/collection")
    Call<Result<List<CollectionInput>>> getAllCollection(@Query("userName") String userName);

    /**
     * 收藏指定视频
     */
    @POST("dolphin/collection")
    Call<Result<Boolean>> collection(@Query("userName") String userName, @Query("videoId") String videoId);

    /**
     * 取消收藏指定视频
     */
    @DELETE("dolphin/collection")
    Call<Result<Boolean>> unCollection(@Query("userName") String userName, @Query("videoId") String videoId);
}
