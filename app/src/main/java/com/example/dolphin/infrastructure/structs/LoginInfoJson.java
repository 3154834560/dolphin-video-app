package com.example.dolphin.infrastructure.structs;

import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

/**
 * @author 王景阳
 * @date 2022/11/14 9:45
 */
@Data
public class LoginInfoJson {

    LoginInfo currentUser;

    Map<String, LoginInfo> historyUser;

    public Map<String, LoginInfo> getHistoryUser() {
        if (historyUser == null) {
            return new TreeMap<>();
        }
        return historyUser;
    }

    public void updateHistoryUser(LoginInfo user) {
        if (historyUser == null) {
            historyUser = new TreeMap<>();
        }
        historyUser.put(user.getLoggedUser().getUserName(), user.updateLoginTime());
    }

    public void deleteHistoryUser(LoginInfo user) {
        if (historyUser == null) {
            historyUser = new TreeMap<>();
            return;
        }
        historyUser.remove(user.getLoggedUser().getUserName());
    }

}
