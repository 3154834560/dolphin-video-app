package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.Status;
import com.example.dolphin.infrastructure.tool.BaseTool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/24 19:25
 */
@AllArgsConstructor
public class UploadVideoListener implements View.OnClickListener {

    public static Status UPLOAD_STATUS = new Status(false);

    private Activity activity;

    private Class<?> class1;

    private Class<?> class2;

    @Override
    public void onClick(View v) {
        if (UPLOAD_STATUS.isStatus()) {
            BaseTool.shortToast(activity, "视频上传中！");
            return;
        }
        Intent intent = new Intent(activity, StringPool.CURRENT_USER == null ? class1 : class2);
        activity.startActivity(intent);
        if(class1==class2){
            activity.finish();
        }
    }
}