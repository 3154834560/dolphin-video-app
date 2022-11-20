package com.example.dolphin.api;

import com.example.dolphin.domain.entity.Concern;
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
public interface ConcernApi {

    @GET("dolphin/concern")
    Call<Result<List<Concern>>> getAllConcern(@Query("userName") String userName);

    @POST("dolphin/concern")
    Call<Result<Boolean>> concern(@Query("userName") String userName,@Query("concernedUserName") String concernedUserName);

    @DELETE("dolphin/concern")
    Call<Result<Boolean>> unconcern(@Query("userName") String userName,@Query("concernedUserName") String concernedUserName);


}
