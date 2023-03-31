package com.example.dolphin.infrastructure.tool;

import android.annotation.SuppressLint;

import com.example.dolphin.infrastructure.consts.StringPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Getter;

/**
 * @author 王景阳
 * @date 2023/3/29 0:17
 */
public class DateTimeTool {
    /**
     * 字符串转化为日期
     *
     * @param str 传入字符串
     * @return 返回日期
     */
    @SuppressLint("NewApi")
    public static LocalDateTime strToLocalDateTime(String str, DateFormat dateFormat) {
        LocalDateTime date = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat.getFormat());
        date = LocalDateTime.parse(str, dateTimeFormatter);
        return date;
    }

    /**
     * 日期转化为字符串
     *
     * @param date 传入日期
     * @return 返回字符串
     */
    @SuppressLint("NewApi")
    public static String dateToString(LocalDateTime date, DateFormat dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat.getFormat());
        return dateTimeFormatter.format(date);
    }

    /**
     * 字符串转化为日期
     *
     * @param str 传入字符串
     * @return 返回日期
     */
    @SuppressLint("SimpleDateFormat")
    public static Date strToDate(String str, DateFormat dateFormat) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getFormat());
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转化为字符串
     *
     * @param date 传入日期
     * @return 返回字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date, DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getFormat());
        return simpleDateFormat.format(date);
    }

    /**
     * 将秒数转换为LocalDateTime
     */
    @SuppressLint("NewApi")
    public static LocalDateTime toLocalDateTime(long seconds) {
        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.ofHours(8));
    }

    @SuppressLint("NewApi")
    public static long toLong(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.ofHours(StringPool.TIME_ZONE));
    }

    @SuppressWarnings("all")
    @Getter
    public enum DateFormat {
        ONE(1, "yyyy-MM-dd HH:mm:ss"),
        TOW(2, "yyyy-MM-dd"),
        THREE(3, "HH:mm:ss"),
        FOUR(4, "yyyy/MM/dd HH:mm:ss"),
        FIVE(5, "yyyy年MM月dd日 HH时mm分ss秒"),
        SIX(6, "yyyy年MM月dd日"),
        SEVEN(7, "yyyy.MM.dd");

        DateFormat(int index, String format) {
            this.index = index;
            this.format = format;
        }

        private int index;
        private String format;
    }
}
