package com.example.dolphin.infrastructure.tool;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/26 19:26
 */
public class PermissionTool {

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final List<String> M_PERMISSION_LIST = new ArrayList<>();

    public static final int PERMISSION_REQUEST = 1;

    /**
     * 检查权限
     */
    public static void checkPermission(Activity activity) {
        M_PERMISSION_LIST.clear();
        //判断哪些权限未授予
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                M_PERMISSION_LIST.add(permission);
            }
        }
        if (!M_PERMISSION_LIST.isEmpty()) {
            //未授予的权限为空，表示都授予了
            //请求权限方法
            String[] permissions = M_PERMISSION_LIST.toArray(new String[0]);
            ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST);
        }
    }
}
