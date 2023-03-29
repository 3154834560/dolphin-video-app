package com.example.dolphin.infrastructure.tool;

/**
 * @author 王景阳
 * @date 2023/3/29 13:13
 */
public class NumberChangeTool {
    public static String numberChange(Integer n) {
        if (n > 10000) {
            return (n / 10000) + "w+";
        }
        if (n > 1000) {
            return (n / 1000) + "k+";
        }
        return n + "";
    }
}
