package com.example.dolphin.api;

import com.example.dolphin.application.dto.input.CommentInput;
import com.example.dolphin.application.dto.output.CommentOutput;
import com.example.dolphin.infrastructure.rest.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author 王景阳
 * @date 2023/3/28 20:57
 */
public interface CommentApi {

    @GET("dolphin/comment")
    Call<Result<List<CommentInput>>> getAllComment(@Query("videoId") String videoId);


    @GET("dolphin/comment/count")
    Call<Result<Integer>> getCommentCount(@Query("videoId") String videoId);

    @POST("dolphin/comment")
    Call<Result<Boolean>> comment(@Body CommentOutput output);

    @DELETE("dolphin/comment")
    Call<Result<Boolean>> unComment(@Query("id") String id);
}
