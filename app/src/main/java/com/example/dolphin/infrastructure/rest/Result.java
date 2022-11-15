package com.example.dolphin.infrastructure.rest;

import java.io.Serializable;

import lombok.Setter;

/**
 * @author 王景阳
 * @date 2022/10/27 15:16
 */
@Setter
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private Boolean success;
    private T data;

    public Result() {
    }

    protected Result(Integer code, String msg, Boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public static <T> Result<T> data(T data) {
        return new Result<>(RestCode.SUCCESS.getCode(), RestCode.SUCCESS.getMsg(), RestCode.SUCCESS.getFlag(), data);
    }

    public static Result<?> ok(String msg) {
        return new Result<>(RestCode.SUCCESS.getCode(), msg, RestCode.SUCCESS.getFlag(), null);
    }

    public static Result<?> fail(String msg) {
        return new Result<>(RestCode.FAILURE.getCode(), msg, RestCode.FAILURE.getFlag(), null);
    }

    public static Result<?> fail() {
        return new Result<>(RestCode.TOKEN_FAILURE.getCode(), null, RestCode.TOKEN_FAILURE.getFlag(), null);
    }

    //region gt&...

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    //endregion
}
