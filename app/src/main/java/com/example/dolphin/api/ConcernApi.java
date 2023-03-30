package com.example.dolphin.api;

import com.example.dolphin.application.dto.input.ConcernInput;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 关注
 *
 * @author 王景阳
 * @date 2022/11/20 13:19
 */
public interface ConcernApi {
    /**
     * 获取指定用户的所以关注
     */
    @GET("dolphin/concern")
    Call<Result<List<ConcernInput>>> getAllConcern(@Query("userName") String userName);

    /**
     * 关注指定用
     */
    @POST("dolphin/concern")
    Call<Result<Boolean>> concern(@Query("userName") String userName, @Query("concernedUserName") String concernedUserName);

    /**
     * 取消关注指定用户
     */
    @DELETE("dolphin/concern")
    Call<Result<Boolean>> unconcern(@Query("userName") String userName, @Query("concernedUserName") String concernedUserName);

}
