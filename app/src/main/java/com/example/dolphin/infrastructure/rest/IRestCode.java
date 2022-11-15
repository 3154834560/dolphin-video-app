package com.example.dolphin.infrastructure.rest;

/**
 * 自定义响应码接口
 *
 * @author 王景阳
 * @date 2022/10/27 15:16
 */
public interface IRestCode {
    Integer getCode();

    String getMsg();

    Boolean getFlag();
}
