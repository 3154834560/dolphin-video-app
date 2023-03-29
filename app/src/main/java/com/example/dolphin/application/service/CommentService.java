package com.example.dolphin.application.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.dolphin.api.CommentApi;
import com.example.dolphin.application.dto.input.CommentInput;
import com.example.dolphin.application.dto.output.CommentOutput;
import com.example.dolphin.domain.entity.Comment;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2023/3/28 20:57
 */
public class CommentService {

    private final CommentApi COMMENT_API = RetrofitUtils.create(CommentApi.class);

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Comment> getAllComment(Context context, String videoId) {
        try {
            Call<Result<List<CommentInput>>> call = COMMENT_API.getAllComment(videoId);
            Result<List<CommentInput>> result = ApiTool.sendRequest(call);
            StringPool.COMMENT_MAP.put(videoId, result.getData().stream().map(CommentInput::copy).collect(Collectors.toList()));
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return StringPool.COMMENT_MAP.getOrDefault(videoId, new ArrayList<>());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean comment(Context context, CommentOutput output) {
        try {
            Call<Result<Boolean>> call = COMMENT_API.comment(output);
            Result<Boolean> result = ApiTool.sendRequest(call);
            getAllComment(context, output.getVideoId());
            getCommentCount(context, output.getVideoId());
            return result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return false;
    }

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
