package com.example.dolphin.infrastructure.threads;

import android.app.Activity;
import android.media.MediaScannerConnection;

import com.example.dolphin.application.service.DownloadService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.io.File;
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
public class DownRunnable implements Runnable {

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
            file.deleteOnExit();
            DownloadService.DOWN_STATUS.compareAndSet(true, false);
            activity.runOnUiThread(() -> BaseTool.shortToast(activity, StringPool.DOWN_FAIL));
            return;
        }
        activity.runOnUiThread(() -> BaseTool.shortToast(activity, StringPool.DOWN_SUCCESS));
        DownloadService.DOWN_STATUS.compareAndSet(true, false);
    }

}
