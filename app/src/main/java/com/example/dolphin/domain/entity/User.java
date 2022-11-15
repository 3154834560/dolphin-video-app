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

    private String userName;

    private String name;

    private String nick;

    private String password;

    private String headPortraitUrl;

    private SexEnum sex;

    private LocalDateTime birthday;

    private String phone;

    private String introduction;

    private boolean isAdmin;

}
