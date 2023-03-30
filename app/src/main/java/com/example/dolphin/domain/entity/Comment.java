package com.example.dolphin.domain.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王景阳
 * @date 2023/3/28 20:57
 */
@Data
@EqualsAndHashCode
public class Comment {
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
     * 昵称
     */
    private String headPortraitName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 发表时间
     */
    private LocalDateTime createAt;
}
