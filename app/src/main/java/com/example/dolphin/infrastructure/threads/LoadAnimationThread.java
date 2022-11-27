package com.example.dolphin.infrastructure.threads;

import android.app.Dialog;
import android.widget.ImageView;

import com.example.dolphin.application.service.DownloadService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author 王景阳
 * @date 2022/11/27 20:36
 */
@AllArgsConstructor
public class LoadAnimationThread implements Runnable {

    private final Dialog dialog;

    private final ImageView imageView;

    @SneakyThrows
    @Override
    public void run() {
        int angle = 90;
        while (DownloadService.DOWN_STATUS) {
            //支点在图片中心
            imageView.setPivotX(imageView.getWidth() / 2.0f);
            imageView.setPivotY(imageView.getHeight() / 2.0f);
            imageView.setRotation(angle);
            Thread.sleep(25);
            angle = (angle + 1) % 360;
        }
        dialog.cancel();
    }
}
