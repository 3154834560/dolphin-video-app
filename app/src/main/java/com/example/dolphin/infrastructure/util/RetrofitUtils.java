package com.example.dolphin.infrastructure.util;

import com.example.dolphin.infrastructure.consts.HttpPool;
import com.example.dolphin.infrastructure.consts.StringPool;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;

    private RetrofitUtils() {
    }

    private static RetrofitUtils getInstance() {
        if (retrofitUtils == null) {
            synchronized (StringPool.LOCKS[0]) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(HttpPool.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T create(Class<T> service) {
        return getInstance().getRetrofit().create(service);
    }
}
