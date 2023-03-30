package com.example.dolphin.application.dto.input;


import com.example.dolphin.domain.model.Video;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/10/29 20:53
 */
@Data
public class VideoInput {
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

    public Video to() {
        Video video = new Video();
        video.setId(id);
        video.setVideoName(videoName);
        video.setAuthor(author);
        video.setAuthorNick(authorNick);
        video.setIntroduction(introduction);
        video.setCoverName(coverName);
        video.setNumbers(numbers);
        return video;
    }
}
