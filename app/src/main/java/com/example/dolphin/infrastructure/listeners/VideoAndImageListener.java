package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.view.View;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.media.DialogContainer;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/15 19:21
 */
@AllArgsConstructor
public class VideoAndImageListener implements View.OnClickListener {

    private Activity activity;

    private int requestCode;

    private String fileType;

    @Override
    public void onClick(View v) {
        DialogContainer.showPictureDialog(activity, R.layout.dialog_album, requestCode, fileType);
    }
}
