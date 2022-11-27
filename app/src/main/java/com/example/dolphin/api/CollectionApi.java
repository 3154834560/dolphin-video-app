package com.example.dolphin.api;

import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 王景阳
 * @date 2022/11/20 13:19
 */
public interface CollectionApi {

    @GET("dolphin/collection")
    Call<Result<List<Video>>> getAllCollection(@Query("userName") String userName);

    @POST("dolphin/collection")
    Call<Result<Boolean>> collection(@Query("userName") String userName,@Query("videoId") String videoId);

    @DELETE("dolphin/collection")
    Call<Result<Boolean>> unCollection(@Query("userName") String userName,@Query("videoId") String videoId);


}
