package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.activity.LoginPageActivity;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/23 17:44
 */
@AllArgsConstructor
public class SupportListener implements View.OnClickListener {

    private Context context;

    private Video video;

    private ImageView supportIcon;

    private TextView supportNumber;

    private final VideoService videoService = new VideoService();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (StringPool.CURRENT_USER == null) {
            context.startActivity(new Intent(context, LoginPageActivity.class));
            return;
        }
        if (videoService.isSupport(context, video.getId())) {
            videoService.unSupportVideo(context, video);
            supportIcon.setBackground(context.getDrawable(R.drawable.icon_un_support1));
        } else {
            videoService.supportVideo(context, video);
            supportIcon.setBackground(context.getDrawable(R.drawable.icon_support));
        }
        @SuppressLint("DefaultLocale") String number = String.format("%.2f万", video.getNumbers() / 10000.0);
        supportNumber.setText(video.getNumbers() >= 10000 ? number : video.getNumbers() + "");
    }
}
