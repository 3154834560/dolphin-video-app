package com.example.dolphin.infrastructure.threads;

import android.app.Dialog;
import android.widget.ImageView;

import com.example.dolphin.infrastructure.structs.Status;

import lombok.Setter;
import lombok.SneakyThrows;

/**
 * @author 王景阳
 * @date 2022/11/27 20:36
 */
@Setter
public class LoadAnimationThread implements Runnable {

    private Dialog dialog;

    private ImageView imageView;

    private static Status status;

    private LoadAnimationThread() {
    }

    public static LoadAnimationThread getInstance(Dialog dialog, ImageView imageView, Status status) {
        LoadAnimationThread thread = new LoadAnimationThread();
        thread.setDialog(dialog);
        thread.setImageView(imageView);
        LoadAnimationThread.status = status;
        return thread;
    }

    @SneakyThrows
    @Override
    public void run() {
        int angle = 90;
        while (status.isStatus()) {
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
