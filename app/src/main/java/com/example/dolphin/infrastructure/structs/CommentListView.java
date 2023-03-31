package com.example.dolphin.infrastructure.structs;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王景阳
 * @date 2023/3/29 0:02
 */
@EqualsAndHashCode
@Data
public class CommentListView {
    private String commentId;

    private String userName;

    private String nick;

    private String headPortraitName;

    private String content;

    private LocalDateTime createAt;

    private boolean isCurrentUser;

    private Class<?> nextClass;
}
