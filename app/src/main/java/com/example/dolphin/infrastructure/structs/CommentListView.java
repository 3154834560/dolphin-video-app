package com.example.dolphin.infrastructure.structs;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2023/3/29 0:02
 */
@EqualsAndHashCode
@Data
@Accessors(chain = true)
public class CommentListView {
    private String userName;

    private String nick;

    private String headPortraitUrl;

    private String content;

    private LocalDateTime createAt;

    private Class<?> nextClass;
}
