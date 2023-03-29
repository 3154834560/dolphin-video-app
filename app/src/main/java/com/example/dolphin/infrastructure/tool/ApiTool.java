package com.example.dolphin.infrastructure.tool;

import android.annotation.SuppressLint;

import com.example.dolphin.infrastructure.rest.Result;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author 王景阳
 * @date 2022/11/12 18:57
 */
public class ApiTool {

    @SneakyThrows
    @SuppressLint("NewApi")
    public static <T> Result<T> sendRequest(Call<Result<T>> call) {
        CompletableFuture<Result<T>> future = CompletableFuture.supplyAsync(new Supplier<Result<T>>() {
            @SneakyThrows
            @Override
            public Result<T> get() {
                Response<Result<T>> response = call.execute();
                return response.body();
            }
        });
        return future.get();
    }
}
