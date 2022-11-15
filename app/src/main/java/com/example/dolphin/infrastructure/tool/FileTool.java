package com.example.dolphin.infrastructure.tool;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.example.dolphin.infrastructure.consts.StringPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import lombok.SneakyThrows;

/**
 * @author 王景阳
 * @date 2022/11/14 9:10
 */
public class FileTool {

    @SuppressWarnings("all")
    public static boolean createFile(String fileName) {
        String filePath = StringPool.WORKING_PATH + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        try {
            Objects.requireNonNull(file.getParentFile()).mkdirs();
            file.createNewFile();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * Uri转化为Bitmap
     */
    @SneakyThrows
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
    }
}
