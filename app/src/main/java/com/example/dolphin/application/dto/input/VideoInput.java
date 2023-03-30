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
        video.setCoverName(coverName);
        video.setNumbers(numbers);
        return video;
    }
}
