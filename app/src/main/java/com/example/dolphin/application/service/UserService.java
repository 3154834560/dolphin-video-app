package com.example.dolphin.application.service;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.dolphin.api.UserApi;
import com.example.dolphin.application.dto.input.UserInput;
import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.domain.model.User;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 用户服务类
 *
 * @author 王景阳
 * @date 2022/11/13 21:55
 */
public class UserService {

    private final UserApi USER_API = RetrofitUtils.create(UserApi.class);

    /**
     * 创建用户
     */
    public User create(Context context, UserOutput userOutput) {
        User user = null;
        try {
            Call<Result<UserInput>> call = USER_API.create(userOutput);
            Result<UserInput> result = ApiTool.sendRequest(call);
            user = result.getData().copy();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return user;
    }

    /**
     * 更新用户头像
     */
    public String updateHeadPortrait(Context context, MultipartBody.Part image) {
        String msg = StringPool.UPLOAD_FAIL;
        try {
            Call<Result<String>> call = USER_API.updateHeadPortrait(StringPool.CURRENT_USER.getUserName(), image);
            Result<String> result = ApiTool.sendRequest(call);
            msg = result.getCode().equals(StringPool.SUCCESS) ? StringPool.UPLOAD_SUCCESS : StringPool.UPLOAD_FAIL;
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return msg;
    }

    /**
     * 更新用户信息
     */
    public void update(Context context, UserOutput output) {
        try {
            Call<Result<UserInput>> call = USER_API.update(output);
            Result<UserInput> result = ApiTool.sendRequest(call);
            StringPool.CURRENT_USER = result.getData().copy();
            writeLoginInfo(context, StringPool.CURRENT_USER);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.UPDATE_FAIL);
            return;
        }
        BaseTool.shortToast(context, StringPool.UPDATE_SUCCESS);
    }

    /**
     * 获取指定用户信息
     */
    public User getBy(Context context, String userName) {
        User user = null;
        try {
            Call<Result<UserInput>> call = USER_API.getBy(userName);
            Result<UserInput> result = ApiTool.sendRequest(call);
            user = result.getData().copy();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return user;
    }

    /**
     * 验证用户名和密码
     */
    public Integer verify(Context context, String userName, String password) {
        Integer verifyResult = -1;
        try {
            Call<Result<Integer>> call = USER_API.verify(userName, password);
            Result<Integer> result = ApiTool.sendRequest(call);
            verifyResult = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return verifyResult;
    }

    /**
     * 从本地json文件中读取已登录用户信息
     */
    public User getLoginUserInfo(Context context) {
        File file = new File(StringPool.LOGIN_INFO_FILE_PATH);
        User user = null;
        try {
            user = JSON.parseObject(new FileInputStream(file), User.class);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NO_STORAGE_PERMISSION);
        }
        return user;
    }

    /**
     * 将用户信息保存到本地json文件中
     */
    public void writeLoginInfo(Context context, User user) {
        File file = new File(StringPool.LOGIN_INFO_FILE_PATH);
        try {
            JSON.writeJSONString(new FileOutputStream(file), user);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NO_STORAGE_PERMISSION);
        }
    }

    /**
     * 退出登录，清除本地json文件中的数据
     */
    public void quitLogin(Context context) {
        StringPool.CURRENT_USER = null;
        writeLoginInfo(context, null);
    }
}
