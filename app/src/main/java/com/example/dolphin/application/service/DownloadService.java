package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.dolphin.api.DownloadApi;
import com.example.dolphin.infrastructure.consts.HttpPool;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.threads.DownRunnable;
import com.example.dolphin.infrastructure.threads.LoadAnimationRunnable;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载服务类
 *
 * @author 王景阳
 * @date 2022/11/27 15:24
 */
public class DownloadService {

    public static volatile AtomicBoolean DOWN_STATUS = new AtomicBoolean(false);

    private final DownloadApi DOWN_API = RetrofitUtils.create(DownloadApi.class);

    /**
     * 文件下载接口
     */
    @SuppressLint("NewApi")
    public void downloadFile(Activity activity, String type, String name) {
        addLoadAnimation(activity);
        Call<ResponseBody> call = DOWN_API.download(HttpPool.URI + "/dolphin/down?type=" + type + "&name=" + name);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String filePath = StringPool.COM_EXAMPLE_DOLPHIN_PATH + File.separator + System.currentTimeMillis() + name.substring(name.lastIndexOf(StringPool.DOT));
                File file = new File(filePath);
                ResponseBody body = response.body();
                Headers headers = response.headers();
                CompletableFuture.runAsync(new DownRunnable(activity, body, headers, file));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                activity.runOnUiThread(() -> {
                    BaseTool.shortToast(activity, t.getMessage());
                    BaseTool.shortToast(activity, StringPool.DOWN_FAIL);
                });
                DOWN_STATUS.compareAndSet(true, false);
            }
        });
    }

    @SuppressLint("NewApi")
    private void addLoadAnimation(Activity activity) {
        DOWN_STATUS.compareAndSet(false, true);
        LoadAnimationService loadAnimationService = new LoadAnimationService(activity);
        LoadAnimationRunnable animationThread = LoadAnimationRunnable.getInstance(loadAnimationService.getDialog(), loadAnimationService.getImageView(), DownloadService.DOWN_STATUS);
        CompletableFuture.runAsync(animationThread);
    }

}

