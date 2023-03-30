package com.example.dolphin.domain.entity;


import com.example.dolphin.domain.enums.SexEnum;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王景阳
 * @date 2022/10/29 20:40
 */

@Data
@EqualsAndHashCode
public class User {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像名称-带后缀
     */
    private String headPortraitName;
    /**
     * 性别
     */
    private SexEnum sex;
    /**
     * 生日
     */
    private LocalDateTime birthday;
    /**
     * 电话
     */
    private String phone;
    /**
     * 个人简介
     */
    private String introduction;
}
