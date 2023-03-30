package com.example.dolphin.application.dto.input;

import android.annotation.SuppressLint;

import com.example.dolphin.infrastructure.tool.DateTimeTool;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2023/3/29 13:44
 */
@Data
public class CommentInput {
    /**
     * 评论id
     */
    private String commentId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 头像名称-带后缀
     */
    private String headPortraitName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 创建时间
     */
    private long createAt;

    @SuppressLint("NewApi")
    public LocalDateTime getCreateAt() {
        return DateTimeTool.toLocalDateTime(createAt);
    }
}
