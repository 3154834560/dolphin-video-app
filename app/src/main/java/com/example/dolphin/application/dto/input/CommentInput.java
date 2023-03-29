package com.example.dolphin.application.dto.input;

import android.annotation.SuppressLint;

import com.example.dolphin.domain.entity.Comment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2023/3/29 13:44
 */
@Data
public class CommentInput {

    private String commentId;

    private String userName;

    private String nick;

    private String headPortraitUrl;

    private String content;

    private long createAt;

    @SuppressLint("NewApi")
    public Comment copy() {
        Comment comment = new Comment();
        comment.setUserName(this.userName);
        comment.setCommentId(this.commentId);
        comment.setNick(this.nick);
        comment.setHeadPortraitUrl(this.headPortraitUrl);
        comment.setContent(this.content);
        comment.setCreateAt(LocalDateTime.ofEpochSecond(this.createAt, 0, ZoneOffset.ofHours(8)));
        return comment;
    }
}
