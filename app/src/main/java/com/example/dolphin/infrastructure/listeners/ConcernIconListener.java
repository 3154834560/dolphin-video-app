package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.dolphin.R;
import com.example.dolphin.activity.LoginPageActivity;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/20 13:32
 */
@AllArgsConstructor
public class ConcernIconListener implements View.OnClickListener {

    private Context context;

    private String author;

    private ImageView concernIcon;

    private final ConcernService concernService = new ConcernService();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (StringPool.CURRENT_USER == null) {
            context.startActivity(new Intent(context, LoginPageActivity.class));
            return;
        }
        if (concernService.isConcern(author)) {
            concernService.unConcern(context, author);
            concernIcon.setBackground(context.getDrawable(R.drawable.icon_add));
        } else {
            concernService.concern(context, author);
            concernIcon.setBackground(context.getDrawable(R.drawable.icon_concerned));
        }
    }
}
