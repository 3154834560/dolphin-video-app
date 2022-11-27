package com.example.dolphin.application.service;

import android.content.Context;

import com.example.dolphin.api.CollectionApi;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/26 15:11
 */
public class CollectionService {

    private final CollectionApi COLLECTION_API = RetrofitUtils.getInstance().getRetrofit().create(CollectionApi.class);

    public List<Video> getAllCollection(Context context) {
        try {
            Call<Result<List<Video>>> call = COLLECTION_API.getAllCollection(StringPool.CURRENT_USER.getUserName());
            Result<List<Video>> result = ApiTool.sendRequest(call);
            StringPool.COLLECTION_LIST = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.COLLECTION_LIST;
    }

    public Boolean isCollection(String videoId) {
        if (StringPool.COLLECTION_LIST == null || StringPool.COLLECTION_LIST.size() == 0) {
            return false;
        }
        for (Video video : StringPool.COLLECTION_LIST) {
            if (video.getId().equals(videoId)) {
                return true;
            }
        }
        return false;
    }

    public boolean collection(Context context, String videoId) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = COLLECTION_API.collection(StringPool.CURRENT_USER.getUserName(), videoId);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                getAllCollection(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }

    public boolean unCollection(Context context, String videoId) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = COLLECTION_API.unCollection(StringPool.CURRENT_USER.getUserName(), videoId);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                getAllCollection(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }
}
