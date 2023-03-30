package com.example.dolphin.application.service;

import android.content.Context;

import com.example.dolphin.api.CollectionApi;
import com.example.dolphin.application.dto.input.CollectionInput;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.List;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/26 15:11
 */
public class CollectionService {

    private final CollectionApi COLLECTION_API = RetrofitUtils.create(CollectionApi.class);

    /**
     * 获取指定用户的所以收藏
     */
    public List<CollectionInput> getAllCollection(Context context) {
        try {
            Call<Result<List<CollectionInput>>> call = COLLECTION_API.getAllCollection(StringPool.CURRENT_USER.getUserName());
            Result<List<CollectionInput>> result = ApiTool.sendRequest(call);
            StringPool.COLLECTION_INPUT_LIST = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.COLLECTION_INPUT_LIST;
    }

    /**
     * 是否收藏指定视频
     */
    public Boolean isCollection(String videoId) {
        if (StringPool.COLLECTION_INPUT_LIST == null || StringPool.COLLECTION_INPUT_LIST.size() == 0) {
            return false;
        }
        for (CollectionInput input : StringPool.COLLECTION_INPUT_LIST) {
            if (input.getVideoId().equals(videoId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 收藏指定视频
     */
    public void collection(Context context, String videoId) {
        try {
            Call<Result<Boolean>> call = COLLECTION_API.collection(StringPool.CURRENT_USER.getUserName(), videoId);
            Result<Boolean> result = ApiTool.sendRequest(call);
            if (result.getData()) {
                getAllCollection(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
    }

    /**
     * 取消收藏指定视频
     */
    public void unCollection(Context context, String videoId) {
        try {
            Call<Result<Boolean>> call = COLLECTION_API.unCollection(StringPool.CURRENT_USER.getUserName(), videoId);
            Result<Boolean> result = ApiTool.sendRequest(call);
            if (result.getData()) {
                getAllCollection(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
    }
}
