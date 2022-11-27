package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import com.example.dolphin.api.DownloadApi;
import com.example.dolphin.infrastructure.consts.HttpPool;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.threads.DownThread;
import com.example.dolphin.infrastructure.threads.LoadAnimationThread;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author 王景阳
 * @date 2022/11/27 15:24
 */
public class DownloadService {

    public static boolean DOWN_STATUS = false;

    private final DownloadApi DOWN_API = RetrofitUtils.getInstance().getRetrofit().create(DownloadApi.class);

    /**
     * 文件下载接口
     */
    @SuppressLint("NewApi")
    public void downloadFile(Activity activity, String type, String name) {
        DOWN_STATUS = true;
        LoadAnimationService loadAnimationThread = new LoadAnimationService(activity);
        CompletableFuture.runAsync(new LoadAnimationThread(loadAnimationThread.getDialog(), loadAnimationThread.getImageView()));
        Call<ResponseBody> call = DOWN_API.download(HttpPool.URI + "/dolphin/down?type=" + type + "&name=" + name);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String filePath = StringPool.COM_EXAMPLE_DOLPHIN_PATH + File.separator + System.currentTimeMillis() + name.substring(name.lastIndexOf(StringPool.DOT));
                File file = new File(filePath);
                ResponseBody body = response.body();
                Headers headers = response.headers();
                CompletableFuture.runAsync(new DownThread(activity, body, headers, file));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                activity.runOnUiThread(() -> {
                    BaseTool.shortToast(activity, t.getMessage());
                    BaseTool.shortToast(activity, StringPool.DOWN_FAIL);
                });
                //DOWN_STATUS = false;
            }
        });
    }

}

