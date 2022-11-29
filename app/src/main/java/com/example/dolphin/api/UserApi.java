package com.example.dolphin.api;


import com.example.dolphin.application.dto.input.UserInput;
import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author 王景阳
 * @date 2022/10/27 18:48
 */

public interface UserApi {

    @POST("/dolphin/user")
    Call<Result<UserInput>> create(@Body UserOutput userOutput);

    @PUT("/dolphin/user")
    Call<Result<UserInput>> update(@Body UserOutput userOutput);

    @GET("/dolphin/user")
    Call<Result<UserInput>> getBy(@Query("userName") String userName);

    @GET("/dolphin/user/all")
    Call<Result<List<UserInput>>> getAll();

    @GET("/dolphin/user/verify")
    Call<Result<Integer>> verify(@Query("userName") String userName, @Query("password") String password);
}
