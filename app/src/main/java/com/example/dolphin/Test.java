package com.example.dolphin;


import com.alibaba.fastjson.JSON;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.structs.LoginInfo;

/**
 * @author 王景阳
 * @date 2022/10/29 16:28
 */
public class Test {
    public static void main(String[] args) {
        LoginInfo info=new LoginInfo();
        info.setUpLoginTime(System.currentTimeMillis());
        User user = new User();
        user.setNick("wjy");
        user.setUserName("wjy");
        info.setLoggedUser(user);
        System.out.println(JSON.toJSONString(info));
        System.out.println(JSON.parseObject(JSON.toJSONString(info),LoginInfo.class));
    }
}