package com.example.dolphin.infrastructure.consts;


import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 王景阳
 * @date 2022/10/27 18:46
 */
public class StringPool {

    public static boolean LOGIN = false;

    public static MultipartBody.Part VIDEO = null;

    public static MultipartBody.Part IMAGE = null;

    public static User CURRENT_USER = null;

    public static String ALBUM_PATH = "";

    public static final String VIDEO_TYPE = "video/*";

    public static final String IMAGE_TYPE = "image/*";

    public static final String PHOTO_TYPE = ".jpg";

    public static String WORKING_PATH = "";

    public static String MALE = "男";

    public static String FEMALE = "女";

    public static final String LOGIN_INFO_FILE_NAME = "login-info.json";

    public static String LOGIN_INFO_FILE_PATH = "";

    public static final Integer[] LOCKS = new Integer[]{1};

    public static final String FONT_TYPE_FACE = "STXINGKA.TTF";

    public static final String NOT_NETWORK = "网络异常！";

    public static final String NOT_VIDEOS = "当前数据库没有视频！";

    public static final String NO_STORAGE_PERMISSION = "没有内存访问权限！";

    public static Integer INDEX = 0;

    public static Integer SUCCESS = 200;

    public static final int VIDEO_CODE = 1;

    public static final int IMAGE_CODE = 2;

    public static final int ALBUM_CODE = 1;

    public static final int CAMERA_CODE = -1;

    public static Integer ZERO = 0;

    public static Integer ONE = 1;

    public static Integer TWO = 2;

    public static Integer THREE = 2;

    public static List<Video> videos = new ArrayList<>();

    public static final Integer VIDEO_UPDATE_DOT = 3;

    public final static String PROTOCOL = "已阅读并同意<font color=blue>海豚视频服务协议</font>";

    public final static String NOT_USER_NAME = "该用户名不存在！";

    public final static String PASSWORD_ERROR = "密码错误！";

    public final static String SELECT_PROTOCOL = "请勾选协议！";

    public static final Map<Integer, String> MSG = new HashMap<Integer, String>() {{
        put(0, NOT_USER_NAME);
        put(1, PASSWORD_ERROR);
    }};


}
