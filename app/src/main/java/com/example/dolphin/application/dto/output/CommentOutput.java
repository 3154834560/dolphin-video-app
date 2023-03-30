package com.example.dolphin.application.dto.output;


import lombok.Data;

/**
 * @author 王景阳
 * @date 2023-03-28 21:02
 */
@Data
public class CommentOutput {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 视频id
     */
    private String videoId;
    /**
     * 评论内容
     */
    private String content;
}
