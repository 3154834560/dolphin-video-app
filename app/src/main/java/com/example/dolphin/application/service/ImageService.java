package com.example.dolphin.application.service;

import android.content.Context;

import com.example.dolphin.api.ImageApi;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/17 18:56
 */
public class ImageService {

    private final ImageApi IMAGE_API = RetrofitUtils.create(ImageApi.class);

    public String uploadImage(Context context, MultipartBody.Part image) {
        String msg = StringPool.UPLOAD_FAIL;
        try {
            Call<Result<String>> call = IMAGE_API.uploadImage(StringPool.CURRENT_USER.getUserName(), image);
            Result<String> result = ApiTool.sendRequest(call);
            msg = result.getCode().equals(StringPool.SUCCESS) ? StringPool.UPLOAD_SUCCESS : StringPool.UPLOAD_FAIL;
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return msg;
    }
}
