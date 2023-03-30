package com.example.dolphin.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王景阳
 * @date 2022/11/20 13:12
 */
@Data
@EqualsAndHashCode
public class Concern {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 头像名
     */
    private String headPortraitName;
    /**
     * 昵称
     */
    private String nick;
}
