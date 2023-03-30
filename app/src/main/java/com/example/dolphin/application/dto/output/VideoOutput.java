package com.example.dolphin.application.dto.output;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/11/30 8:42
 */
@Data
public class VideoOutput {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 视频简介
     */
    private String videoIntroduction;
    /**
     * 视频名-带后缀
     */
    private String videoName;
    /**
     * 视频封面名-带后缀
     */
    private String coverName;
    /**
     * 是否有封面
     */
    private boolean hasCover;
    /**
     * 是否是最后一块
     */
    private boolean end;

    public VideoOutput(String userName, String videoIntroduction, String videoName, String coverName) {
        this.userName = userName;
        this.videoIntroduction = videoIntroduction;
        this.videoName = videoName;
        this.coverName = coverName;
        this.hasCover = coverName != null;
        this.end = false;
    }

}
