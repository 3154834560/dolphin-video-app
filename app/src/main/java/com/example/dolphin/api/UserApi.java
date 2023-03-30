package com.example.dolphin.api;


import com.example.dolphin.application.dto.input.UserInput;
import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.infrastructure.rest.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 下载接口
 *
 * @author 王景阳
 * @date 2022/10/27 18:48
 */

public interface UserApi {
    /**
     * 创建用户
     */
    @POST("/dolphin/user")
    Call<Result<UserInput>> create(@Body UserOutput userOutput);

    /**
     * 更新用户信息
     */
    @PUT("/dolphin/user")
    Call<Result<UserInput>> update(@Body UserOutput userOutput);

    /**
     * 获取指定用户信息
     */
    @GET("/dolphin/user")
    Call<Result<UserInput>> getBy(@Query("userName") String userName);

    /**
     * 验证用户名和密码
     */
    @GET("/dolphin/user/verify")
    Call<Result<Integer>> verify(@Query("userName") String userName, @Query("password") String password);

    /**
     * 更新用户头像
     */
    @Multipart
    @PUT("/dolphin/user/head/portrait")
    Call<Result<String>> updateHeadPortrait(@Query("userName") String userName, @Part MultipartBody.Part image);
}
