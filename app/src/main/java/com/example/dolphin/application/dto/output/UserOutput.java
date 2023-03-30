package com.example.dolphin.application.dto.output;


import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dolphin.domain.enums.SexEnum;
import com.example.dolphin.domain.model.User;
import com.example.dolphin.infrastructure.consts.StringPool;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/10/29 20:51
 */
@Data
public class UserOutput {
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
    public void copy(List<EditText> editTexts, RadioGroup sexGroup) {
        this.userName = editTexts.get(0).getText().toString().trim();
        this.password = editTexts.get(1).getText().toString().trim();
        this.nick = editTexts.get(2).getText().toString().trim();
        this.sex = SexEnum.getInstance(sexGroup);
        String birthdayStr = editTexts.get(3).getText().toString().trim();
        if (!birthdayStr.isEmpty()) {
            String[] split = birthdayStr.split("\\.");
            if (split.length == StringPool.THREE) {
                try {
                    int year = Integer.parseInt(split[0]);
                    int month = Integer.parseInt(split[1]);
                    int day = Integer.parseInt(split[2]);
                    this.birthday = LocalDateTime.of(year, month, day, 12, 12).toEpochSecond(ZoneOffset.ofHours(8));
                } catch (Exception e) {
                    this.birthday = 0;
                }
            }
        }
        this.phone = editTexts.get(4).getText().toString().trim();
    }

    @SuppressLint("NewApi")
    public static UserOutput copy(User user) {
        UserOutput output = new UserOutput();
        output.setUserName(user.getUserName());
        output.setPassword(user.getPassword());
        output.setNick(user.getNick());
        output.setSex(user.getSex());
        output.setBirthday(user.getBirthday().toEpochSecond(ZoneOffset.ofHours(8)));
        output.setPhone(user.getPhone());
        output.setIntroduction(user.getIntroduction());
        return output;
    }
}
