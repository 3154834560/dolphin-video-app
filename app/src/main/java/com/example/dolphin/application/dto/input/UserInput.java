package com.example.dolphin.application.dto.input;

import android.annotation.SuppressLint;

import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.domain.enums.SexEnum;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author 王景阳
 * @date 2022/10/29 20:51
 */
@Data
public class UserInput {

    private String userName;

    private String name;

    private String nick;

    private String password;

    private String headPortraitUrl;

    private SexEnum sex;

    private long birthday;

    private String phone;

    private String introduction;

    private boolean isAdmin;

    @SuppressLint("NewApi")
    public User copy() {
        User user = new User();
        user.setUserName(this.userName);
        user.setName(this.name);
        user.setNick(this.nick);
        user.setPassword(this.password);
        user.setHeadPortraitUrl(this.headPortraitUrl);
        user.setSex(this.sex);
        user.setBirthday(LocalDateTime.ofEpochSecond(this.birthday,0, ZoneOffset.ofHours(8)));
        user.setPhone(this.phone);
        user.setIntroduction(this.introduction);
        user.setAdmin(this.isAdmin);
        return user;
    }


}
