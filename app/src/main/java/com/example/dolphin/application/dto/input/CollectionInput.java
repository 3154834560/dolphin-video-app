package com.example.dolphin.application.dto.input;

import lombok.Data;

/**
 * 收藏
 *
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
     * 视频封面-带后缀
     */
    private String coverName;
    /**
     * 点赞数
     */
    private long numbers;
}
