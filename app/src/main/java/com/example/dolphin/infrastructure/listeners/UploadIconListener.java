package com.example.dolphin.infrastructure.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/15 16:48
 */
@AllArgsConstructor
public class UploadIconListener implements View.OnClickListener {

    private Context context;

    private Class<?> loginClass;

    private Class<?> uploadClass;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, StringPool.CURRENT_USER == null ? loginClass : uploadClass);
        context.startActivity(intent);
    }
}
