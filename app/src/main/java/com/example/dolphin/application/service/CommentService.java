package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.dolphin.api.CommentApi;
import com.example.dolphin.application.dto.input.CommentInput;
import com.example.dolphin.application.dto.output.CommentOutput;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 评论服务类
 *
 * @author 王景阳
 * @date 2023/3/28 20:57
 */
public class CommentService {

    private final CommentApi COMMENT_API = RetrofitUtils.create(CommentApi.class);

    /**
     * 获取所以评论
     */
    @SuppressLint("NewApi")
    public void updateCommentBy(Context context, String videoId) {
        try {
            Call<Result<List<CommentInput>>> call = COMMENT_API.getAllComment(videoId);
            Result<List<CommentInput>> result = ApiTool.sendRequest(call);
            StringPool.COMMENT_INPUT_MAP.put(videoId, result.getData() == null ? new ArrayList<>() : result.getData());
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
    }

    /**
     * 获取评论实例
     */
    @SuppressLint("NewApi")
    public Integer getCommentCount(Context context, String videoId) {
        try {
            Call<Result<Integer>> call = COMMENT_API.getCommentCount(videoId);
            Result<Integer> result = ApiTool.sendRequest(call);
            StringPool.COMMENT_COUNT_MAP.put(videoId, result.getData());
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.COMMENT_COUNT_MAP.getOrDefault(videoId, 0);
    }

    /**
     * 评论
     */
    public boolean comment(Context context, CommentOutput output) {
        try {
            Call<Result<Boolean>> call = COMMENT_API.comment(output);
            Result<Boolean> result = ApiTool.sendRequest(call);
            updateCommentBy(context, output.getVideoId());
            getCommentCount(context, output.getVideoId());
            return result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return false;
    }

    /**
     * 取消评论
     */
    public boolean unComment(Context context, String id) {
        try {
            Call<Result<Boolean>> call = COMMENT_API.unComment(id);
            Result<Boolean> result = ApiTool.sendRequest(call);
            return result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return false;
    }
}
