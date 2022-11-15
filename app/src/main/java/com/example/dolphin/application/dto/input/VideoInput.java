package com.example.dolphin.application.dto.input;


import lombok.*;

/**
 * @author 王景阳
 * @date 2022/10/29 20:53
 */
@Data
@Builder
public class VideoInput {

    private String id;

    /**
     * 视频播放Url
     */
    private String url;

    /**
     * 视频名
     */
    private String videoName;

    /**
     * 视频作者
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
     * 视频封面名
     */
    private String coverName;


}
