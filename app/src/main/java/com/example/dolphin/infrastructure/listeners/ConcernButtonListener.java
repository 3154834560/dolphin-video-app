package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.example.dolphin.R;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/28 23:54
 */
@AllArgsConstructor
public class ConcernButtonListener implements View.OnClickListener {

    private final Context context;

    private final Button concernButton;

    private final String author;

    private final ConcernService concernService = new ConcernService();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (concernButton.getText().toString().equals(StringPool.Followed)) {
            concernButton.setText(StringPool.Follow);
            concernButton.setBackground(context.getDrawable(R.drawable.shape_2));
            concernService.unConcern(context, author);
        } else {
            concernButton.setText(StringPool.Followed);
            concernButton.setBackground(context.getDrawable(R.drawable.shape_8));
            concernService.concern(context, author);
        }
    }
}
