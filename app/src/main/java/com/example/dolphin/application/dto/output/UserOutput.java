package com.example.dolphin.application.dto.output;


import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dolphin.domain.enums.SexEnum;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/10/29 20:51
 */
@Data
public class UserOutput {

    private String userName;

    private String name;

    private String nick;

    private String password;

    private SexEnum sex;

    private boolean isAdmin;

    private long birthday;

    private String phone;

    private String introduction;

    public void copy(List<EditText> editTexts, RadioGroup sexGroup) {
        this.userName = editTexts.get(0).getText().toString().trim();
        this.password = editTexts.get(1).getText().toString().trim();
        this.nick = editTexts.get(2).getText().toString().trim();
        this.sex = SexEnum.getInstance(sexGroup);
//        this.birthday = LocalDateTime.ofEpochSecond(L);
        this.phone = editTexts.get(4).getText().toString().trim();
    }
}
