package com.example.dolphin.api;

import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author 王景阳
 * @date 2022/11/12 11:43
 */
public interface VideoApi {


    @GET("/dolphin/video/random/{index}")
    Call<Result<List<Video>>> randomGet(@Path("index") Integer index);

    @Multipart
    @POST("/dolphin/video")
    Call<Result<Boolean>> uploadVideo(@Query("userName") String userName, @Query("introduction") String introduction
            , @Part MultipartBody.Part video, @Part MultipartBody.Part cover);
}
