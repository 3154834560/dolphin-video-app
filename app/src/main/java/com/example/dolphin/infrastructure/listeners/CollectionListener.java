package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.dolphin.R;
import com.example.dolphin.activity.LoginPageActivity;
import com.example.dolphin.application.service.CollectionService;
import com.example.dolphin.infrastructure.consts.StringPool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/26 15:52
 */
@AllArgsConstructor
public class CollectionListener implements View.OnClickListener {

    private Context context;

    private String videoId;

    private ImageView collectionIcon;

    private final CollectionService collectionService = new CollectionService();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (StringPool.CURRENT_USER == null) {
            context.startActivity(new Intent(context, LoginPageActivity.class));
            return;
        }
        if (collectionService.isCollection(videoId)) {
            collectionService.unCollection(context, videoId);
            collectionIcon.setBackground(context.getDrawable(R.drawable.un_collection_icon));
        } else {
            collectionService.collection(context, videoId);
            collectionIcon.setBackground(context.getDrawable(R.drawable.collection_icon));
        }
    }
}

