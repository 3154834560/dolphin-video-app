package com.example.dolphin.api;

import com.example.dolphin.infrastructure.rest.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author 王景阳
 * @date 2022/11/17 18:56
 */
public interface ImageApi {

    @Multipart
    @PUT("/dolphin/user/head/portrait")
    Call<Result<String>> uploadImage(@Query("userName") String userName, @Part MultipartBody.Part image);
}
