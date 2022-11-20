package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.asm.FieldWriter;
import com.example.dolphin.api.UserApi;
import com.example.dolphin.application.dto.input.UserInput;
import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.structs.LoginInfo;
import com.example.dolphin.infrastructure.structs.LoginInfoJson;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.FileTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/13 21:55
 */
public class UserService {

    private final UserApi USER_API = RetrofitUtils.getInstance().getRetrofit().create(UserApi.class);

    @SuppressLint("NewApi")
    public List<User> getAll(Context context) {
        List<User> users = new ArrayList<>();
        try {
            Call<Result<List<UserInput>>> call = USER_API.getAll();
            Result<List<UserInput>> result = ApiTool.sendRequest(call);
            users.addAll(result.getData().stream().map(UserInput::copy).collect(Collectors.toList()));
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return users;
    }

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

    public void update(Context context,UserOutput output) {
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

    public User getBy(Context context, String userName) {
        User user = null;
        try {
            Call<Result<UserInput>> call = USER_API.getBy(userName);
            Result<UserInput> result = ApiTool.sendRequest(call);
            user = result.getData().copy();
            writeLoginInfo(context, user);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return user;
    }

    public Integer verify(Context context, String userName, String password) {
        Integer verifyResult = 0;
        try {
            Call<Result<Integer>> call = USER_API.verify(userName, password);
            Result<Integer> result = ApiTool.sendRequest(call);
            verifyResult = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return verifyResult;
    }

    public LoginInfoJson getLoginUserInfo(Context context) {
        File file = new File(StringPool.LOGIN_INFO_FILE_PATH);
        LoginInfoJson loginInfo = new LoginInfoJson();
        try {
            loginInfo = JSON.parseObject(new FileInputStream(file), LoginInfoJson.class);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NO_STORAGE_PERMISSION);
        }
        return loginInfo == null ? new LoginInfoJson() : loginInfo;
    }

    public void writeLoginInfo(Context context, User user) {
        LoginInfo currentUser = new LoginInfo(user, System.currentTimeMillis(), StringPool.INDEX);
        LoginInfoJson loginUserInfo = getLoginUserInfo(context);
        if (user != null) {
            loginUserInfo.setCurrentUser(currentUser);
            loginUserInfo.updateHistoryUser(currentUser);
        } else {
            loginUserInfo.setCurrentUser(null);
        }
        writeLoginInfo(context, loginUserInfo);
    }

    public void deleteHistoryLoginInfo(Context context, User user) {
        LoginInfo currentUser = new LoginInfo(user, System.currentTimeMillis(), 0);
        LoginInfoJson loginUserInfo = getLoginUserInfo(context);
        loginUserInfo.deleteHistoryUser(currentUser);
        writeLoginInfo(context, loginUserInfo);
    }

    public void quitLogin(Context context) {
        LoginInfoJson loginUserInfo = getLoginUserInfo(context);
        loginUserInfo.setCurrentUser(null);
        StringPool.CURRENT_USER = null;
        writeLoginInfo(context, loginUserInfo);
    }

    public void writeLoginInfo(Context context, LoginInfoJson loginUserInfo) {
        File file = new File(StringPool.LOGIN_INFO_FILE_PATH);
        try {
            JSON.writeJSONString(new FileOutputStream(file), loginUserInfo);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NO_STORAGE_PERMISSION);
        }
    }

}
