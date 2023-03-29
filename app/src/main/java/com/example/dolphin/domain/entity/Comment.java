package com.example.dolphin.domain.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王景阳
 * @date 2023/3/28 20:57
 */
@Data
@EqualsAndHashCode
public class Comment {

    private String commentId;

    private String userName;

    private String nick;

    private String headPortraitUrl;

    private String content;

    private LocalDateTime createAt;

}
