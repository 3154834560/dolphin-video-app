package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.dolphin.api.ConcernApi;
import com.example.dolphin.application.dto.input.ConcernInput;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.List;

import retrofit2.Call;

/**
 * 关注服务类
 *
 * @author 王景阳
 * @date 2022/11/20 13:13
 */
public class ConcernService {

    private final ConcernApi CONCERN_API = RetrofitUtils.create(ConcernApi.class);

    /**
     * 获取指定用户的所以关注
     */
    public List<ConcernInput> getAllConcern(Context context) {
        try {
            Call<Result<List<ConcernInput>>> call = CONCERN_API.getAllConcern(StringPool.CURRENT_USER.getUserName());
            Result<List<ConcernInput>> result = ApiTool.sendRequest(call);
            StringPool.CONCERN_INPUT_LIST = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.CONCERN_INPUT_LIST;
    }

    /**
     * 是否关注指定用户
     */
    public boolean isConcern(String userName) {
        if (StringPool.CONCERN_INPUT_LIST.isEmpty()) {
            return false;
        }
        for (ConcernInput concern : StringPool.CONCERN_INPUT_LIST) {
            if (concern.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 关注指定用
     */
    public void concern(Context context, String concernedUserName) {
        try {
            Call<Result<Boolean>> call = CONCERN_API.concern(StringPool.CURRENT_USER.getUserName(), concernedUserName);
            ApiTool.sendRequest(call);
            StringPool.CONCERN_INPUT_LIST.add(new ConcernInput(concernedUserName));
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
    }

    /**
     * 取消关注指定用户
     */
    @SuppressLint("NewApi")
    public void unConcern(Context context, String concernedUserName) {
        try {
            Call<Result<Boolean>> call = CONCERN_API.unconcern(StringPool.CURRENT_USER.getUserName(), concernedUserName);
            ApiTool.sendRequest(call);
            StringPool.CONCERN_INPUT_LIST.removeIf(c -> c.getUserName().equals(concernedUserName));
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
    }
}
