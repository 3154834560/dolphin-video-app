package com.example.dolphin.api;

import com.example.dolphin.application.dto.input.VideoInput;
import com.example.dolphin.domain.model.Video;
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
 * 视频接口
 *
 * @author 王景阳
 * @date 2022/11/12 11:43
 */
public interface VideoApi {
    /**
     * 获取指定页数的视频，视频会随机打乱
     */
    @GET("/dolphin/video/random/{index}")
    Call<Result<List<Video>>> randomGet(@Path("index") Integer index);

    /**
     * 获取指定用户所以视频
     */
    @GET("/dolphin/video/all")
    Call<Result<List<VideoInput>>> getAll(@Query("userName") String userName);

    /**
     * 获取指定视频信息
     */
    @GET("/dolphin/video")
    Call<Result<Video>> getBy(@Query("id") String id);

    /**
     * 为视频点赞或取消点赞
     */
    @POST("/dolphin/support")
    Call<Result<Boolean>> supportVideo(@Query("userName") String userName, @Query("videoId") String videoId, @Query("n") Integer n);

    /**
     * 验证是否点赞
     */
    @GET("/dolphin/support")
    Call<Result<Boolean>> isSupport(@Query("userName") String userName, @Query("videoId") String videoId);

    /**
     * 分块上传视频
     */
    @Multipart
    @POST("/dolphin/video/shard")
    Call<Result<Boolean>> uploadVideo(@Query("videoInput") String videoInputStr
            , @Part MultipartBody.Part video, @Part MultipartBody.Part cover);
}
