package com.example.dolphin.infrastructure.threads;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.dolphin.application.service.DownloadService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import lombok.AllArgsConstructor;
import okhttp3.Headers;
import okhttp3.ResponseBody;

/**
 * @author 王景阳
 * @date 2022/11/27 17:39
 */
@AllArgsConstructor
public class DownThread implements Runnable {

    private final Activity activity;
    private final ResponseBody body;
    private final Headers headers;
    private final File file;

    @Override
    public void run() {
        String msg = headers.get(StringPool.MSG_STR);
        try {
            if (body == null || msg == null || !msg.equals(StringPool.DOWN_SUCCESS)) {
                throw new Exception(msg);
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = body.byteStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            //添加到相册
            MediaScannerConnection.scanFile(activity, new String[]{file.toString()}, null, null);
        } catch (Exception e) {
            DownloadService.DOWN_STATUS = false;
            activity.runOnUiThread(() -> BaseTool.shortToast(activity, StringPool.DOWN_FAIL));
            return;
        }
        activity.runOnUiThread(() -> BaseTool.shortToast(activity, StringPool.DOWN_SUCCESS));
        DownloadService.DOWN_STATUS = false;
    }

    /**
     * 保存图片
     */
    public void saveImage(Context context, File file) {
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getImageContentValues(context, file, System.currentTimeMillis());
        localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);

        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        final Uri localUri = Uri.fromFile(file);
        localIntent.setData(localUri);
        context.sendBroadcast(localIntent);
    }

    /**
     * 保存视频
     */
    public void saveVideo(Context context, File file) {
        //添加到相册
        ContentResolver localContentResolver = context.getContentResolver();
        ContentValues localContentValues = getVideoContentValues(context, file, System.currentTimeMillis());
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);
    }

    public ContentValues getImageContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "image/jpeg");
        localContentValues.put("datetaken", paramLong);
        localContentValues.put("date_modified", paramLong);
        localContentValues.put("date_added", paramLong);
        localContentValues.put("orientation", 0);
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", paramFile.length());
        return localContentValues;
    }

    public ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/mp4");
        localContentValues.put("datetaken", paramLong);
        localContentValues.put("date_modified", paramLong);
        localContentValues.put("date_added", paramLong);
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", paramFile.length());
        return localContentValues;
    }
}
