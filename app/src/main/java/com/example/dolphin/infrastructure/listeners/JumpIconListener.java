package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/15 16:48
 */
@AllArgsConstructor
public class JumpIconListener implements View.OnClickListener {

    private Activity activity;

    private Class<?> class1;

    private Class<?> class2;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, StringPool.CURRENT_USER == null ? class1 : class2);
        activity.startActivity(intent);
        if(class1==class2){
            activity.finish();
        }
    }
}
