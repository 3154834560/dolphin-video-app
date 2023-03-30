package com.example.dolphin.application.dto.input;

import android.annotation.SuppressLint;

import com.example.dolphin.domain.enums.SexEnum;
import com.example.dolphin.domain.model.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/10/29 20:51
 */
@Data
public class UserInput {
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
    private long birthday;
    /**
     * 电话
     */
    private String phone;
    /**
     * 个人简介
     */
    private String introduction;

    @SuppressLint("NewApi")
    public User copy() {
        User user = new User();
        user.setUserName(userName);
        user.setNick(nick);
        user.setPassword(password);
        user.setHeadPortraitName(headPortraitName);
        user.setSex(sex);
        user.setBirthday(LocalDateTime.ofEpochSecond(birthday, 0, ZoneOffset.ofHours(8)));
        user.setPhone(phone);
        user.setIntroduction(introduction);
        return user;
    }


}
