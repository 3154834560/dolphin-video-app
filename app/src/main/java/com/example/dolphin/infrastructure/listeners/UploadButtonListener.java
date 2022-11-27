package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/15 22:24
 */
@AllArgsConstructor
public class UploadButtonListener implements View.OnClickListener {

    private Activity activity;

    private EditText uploadIntro;

    private final VideoService videoService = new VideoService();

    @Override
    public void onClick(View v) {
        if (StringPool.VIDEO == null) {
            BaseTool.shortToast(activity, "请添加视频！");
            return;
        }
        videoService.uploadVideo(activity, uploadIntro.getText().toString());
        activity.finish();
    }

}
