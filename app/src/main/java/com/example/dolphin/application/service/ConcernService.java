package com.example.dolphin.application.service;

import android.content.Context;

import com.example.dolphin.api.ConcernApi;
import com.example.dolphin.domain.entity.Concern;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.List;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/20 13:13
 */
public class ConcernService {

    private final ConcernApi CONCERN_API = RetrofitUtils.create(ConcernApi.class);

    public List<Concern> getAllConcern(Context context) {
        try {
            Call<Result<List<Concern>>> call = CONCERN_API.getAllConcern(StringPool.CURRENT_USER.getUserName());
            Result<List<Concern>> result = ApiTool.sendRequest(call);
            StringPool.CONCERN_LIST = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.CONCERN_LIST;
    }

    public boolean isConcern(String userName) {
        if (StringPool.CONCERN_LIST == null || StringPool.CONCERN_LIST.size() == 0) {
            return false;
        }
        for (Concern concern : StringPool.CONCERN_LIST) {
            if (concern.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean concern(Context context, String concernedUserName) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = CONCERN_API.concern(StringPool.CURRENT_USER.getUserName(), concernedUserName);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                getAllConcern(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }

    public boolean unConcern(Context context, String concernedUserName) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = CONCERN_API.unconcern(StringPool.CURRENT_USER.getUserName(), concernedUserName);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                getAllConcern(context);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }
}
