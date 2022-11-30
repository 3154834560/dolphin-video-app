package com.example.dolphin.application.dto.output;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2022/11/30 8:42
 */
@Getter
@Setter
@Accessors(chain = true)
public class VideoOutput {

    private String userName;

    private String videoIntroduction;

    private String videoName;

    private String coverName;

    private boolean hasCover;

    private boolean end;

    public VideoOutput(String userName, String videoIntroduction, String videoName, String coverName, boolean hasCover) {
        this.userName = userName;
        this.videoIntroduction = videoIntroduction;
        this.videoName = videoName;
        this.coverName = coverName;
        this.hasCover = hasCover;
        this.end = false;
    }

}
