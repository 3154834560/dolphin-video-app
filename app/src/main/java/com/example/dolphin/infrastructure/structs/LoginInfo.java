package com.example.dolphin.infrastructure.structs;

import com.example.dolphin.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/14 9:07
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo implements Comparable<LoginInfo> {

    User loggedUser;

    Long upLoginTime;

    int index;

    public LoginInfo updateLoginTime(){
        this.upLoginTime=System.currentTimeMillis();
        return this;
    }

    @Override
    public int compareTo(LoginInfo o) {
        if (upLoginTime > o.upLoginTime) {
            return -1;
        } else if (upLoginTime < o.upLoginTime) {
            return 1;
        }
        return 0;
    }
}
