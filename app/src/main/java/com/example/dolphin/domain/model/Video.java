package com.example.dolphin.domain.model;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2022/10/29 20:36
 */
@Data
@Accessors(chain = true)
public class Video {
    /**
     * 视频id
     */
    private String id;
    /**
     * 视频名称-带后缀
     */
    private String videoName;
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
     * 视频封面名称-带后缀
     */
    private String coverName;
    /**
     * 点赞数
     */
    private long numbers;
}
