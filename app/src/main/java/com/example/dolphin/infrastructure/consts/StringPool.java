package com.example.dolphin.infrastructure.consts;


import com.example.dolphin.application.dto.input.CollectionInput;
import com.example.dolphin.application.dto.input.CommentInput;
import com.example.dolphin.application.dto.input.ConcernInput;
import com.example.dolphin.domain.model.User;
import com.example.dolphin.domain.model.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王景阳
 * @date 2022/10/27 18:46
 */
public class StringPool {

    public static boolean LOGIN = false;

    public static File VIDEO;

    public static File COVER;

    public static User CURRENT_USER;

    /**
     * 缓存关注
     */
    public static List<ConcernInput> CONCERN_LIST = new ArrayList<>();
    /**
     * 缓存评论
     * key: 视频id
     * value: 视频的评论
     */
    public static final Map<String, List<CommentInput>> COMMENT_INPUT_MAP = new HashMap<>();
    /**
     * 缓存评论数量
     * key: 视频id
     * value: 视频评论数量
     */
    public static final Map<String, Integer> COMMENT_COUNT_MAP = new HashMap<>();
    /**
     * 缓存收藏
     */
    public static List<CollectionInput> COLLECTION_INPUT_LIST = new ArrayList<>();

    public static String RESOURCE_PATH = "";

    public static final String UPLOAD_SUCCESS = "上传成功！";

    public static final String DOWN_SUCCESS = "下载成功！";

    public static final String UPDATE_SUCCESS = "更新成功！";

    public static final String UPLOAD_FAIL = "上传失败！";

    public static final String DOWN_FAIL = "下载失败！";

    public static final String CONTENT_CANNOT_BE_EMPTY = "内容不能为空！";

    public static final String UPDATE_FAIL = "更新失败！";

    public static final String UPDATE = "更新";

    public static final String NOT_LOGIN = "未登录";

    public static final String CURRENT_NOT_LOGIN = "当前未登录！";

    public static final String FOLLOWED = "已关注";

    public static final String FOLLOW = "关注";

    public static String COM_EXAMPLE_DOLPHIN_PATH = "";

    public static final String MSG_STR = "msg";

    public static final String TRUE_STR = "true";

    public static final String FALSE_STR = "false";

    public static final String UPLOAD_VIDEO_BROADCAST_NAME = "com.example.dolphin.upload.video";

    public static final String VIDEO_ID = "videoId";

    public static final String VIDEO_TYPE = "video/*";

    public static final String IMAGE_TYPE = "image/*";

    public static final String AUTHOR_ID = "authorId";

    public static final String SLASH = "/";

    public static final String IMAGES = "images";

    public static final String VIDEOS = "videos";

    public static final String SLASH_IMAGES = SLASH + IMAGES;

    public static final String SLASH_VIDEOS = SLASH + VIDEOS;

    public static final String SLASH_STATIC = "/static";

    public static final String TYPE = "type";

    public static final String PHOTO_TYPE = ".jpg";

    public static final long MAX_VIDEO_SIZE = 1024 * 1024 * 70;

    public static final String VIDEO_TOO_LARGE = "视频过大！最大为70MB";

    public static String WORKING_PATH = "";

    public static boolean IS_UPLOAD = false;

    public static String MALE = "男";

    public static String FEMALE = "女";

    public static final String LOGIN_INFO_FILE_NAME = "login-info.json";

    public static String LOGIN_INFO_FILE_PATH = "";

    public static final Integer[] LOCKS = new Integer[]{1};

    public static final String FONT_TYPE_FACE = "SIMLI.TTF";

    public static final String NOT_NETWORK = "网络异常！";

    public static final String COMMENT_FAILED = "评论失败！";

    public static final String NOT_VIDEOS = "当前数据库没有视频！";

    public static final String NO_STORAGE_PERMISSION = "没有内存访问权限！";

    public static Integer INDEX = 0;

    public static Integer SUCCESS = 200;

    public static final int VIDEO_CODE = 1;

    public static final int IMAGE_CODE = 2;

    public static final int ALBUM_CODE = 1;

    public static final int CONCERN = 1;

    public static final int WORKS = 2;

    public static final int COLLECTION = 3;

    public static final int CAMERA_CODE = -1;

    public static final Integer NEGATIVE_ONE = -1;

    public static final Integer ZERO = 0;

    public static final Integer EIGHT = 8;

    public static final Integer ONE = 1;

    public static final Integer TWO = 2;

    public static final Integer HOME_PAGE_SITE = 2;

    public static Integer THREE = 3;

    public static List<Video> videos = new ArrayList<>();

    public static final Integer VIDEO_UPDATE_DOT = 3;

    public final static String PROTOCOL = "已阅读并同意<font color=blue>海豚视频服务协议</font>";

    public final static String NOT_USER_NAME = "该用户名不存在！";

    public final static String DOT = ".";

    public final static String PASSWORD_ERROR = "密码错误！";

    public final static String SELECT_PROTOCOL = "请勾选协议！";

    public static final Map<Integer, String> MSG = new HashMap<Integer, String>() {{
        put(-1, NOT_NETWORK);
        put(0, NOT_USER_NAME);
        put(1, PASSWORD_ERROR);
    }};

    public final static String EQUATION = "=";

    public final static String C = "C";

    public final static String CE = "CE";

    public final static String EMPTY = "";

    public final static String SPACE = " ";

    public final static String DOT_STR = ".";

    public final static char DOT_CHAR = '.';

    public final static String MULTIPLICATION_SIGN = "x";

    public final static String MINUS_SIGN_STR = "-";

    public final static String PLUS_SIGN_STR = "+";

    public final static String DIVISION_SIGN_STR = "÷";

    public final static String PERCENT_SIGN_STR = "%";

    public final static String EQUAL_SIGN_STR = "=";

    public final static char MULTIPLICATION = 'x';

    public final static char MINUS_SIGN = '-';

    public final static char PLUS_SIGN = '+';

    public final static char DIVISION_SIGN = '÷';

    public final static char PERCENT_SIGN = '%';

    public final static String EXPRESSION_ERROR = "表达式错误";

    public final static String CALCULATION_SYMBOL = MULTIPLICATION_SIGN + MINUS_SIGN_STR + PLUS_SIGN_STR + DIVISION_SIGN_STR;

    public final static String PLEASE_ENTER__SEARCH_CONTENT = "请输入搜索内容";

}
