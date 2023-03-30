package com.example.dolphin.application.dto.input;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/11/10 21:08
 */
@Data
public class CollectionInput {
    /**
     * 视频id
     */
    private String videoId;
    /**
     * 视频作者，对应用户名
     */
    private String author;
    /**
     * 头像名称-带后缀
     */
    private String headPortraitName;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 视频封面-带后缀
     */
    private String coverName;
    /**
     * 视频名-带后缀
     */
    private String videoName;
    /**
     * 视频简介
     */
    private String introduction;
    /**
     * 点赞数
     */
    private long numbers;
}
