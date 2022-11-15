package com.example.dolphin.domain.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2022/10/29 20:36
 */
@Data
@Accessors(chain = true)
public class Video {

    private String id;

    /**
     * 视频播放Url
     */
    private String url;

    /**
     * 视频作者，对应用户名
     */
    private String author;

    /**
     * 视频作者昵称
     */
    private String authorNick;

    /**
     * 视频简介
     */
    private String introduction;

    /**
     * 视频封面
     */
    private String coverUrl;

    /**
     * 点赞数
     */
    private long numbers;

}
