package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.UploadButtonListener;
import com.example.dolphin.infrastructure.listeners.VideoAndImageListener;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.VideoTool;


import lombok.SneakyThrows;

/**
 * @author 王景阳
 * @date 2022/11/15 16:50
 */
public class UploadPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        initData();
    }

    private void initData() {
        EditText uploadVideoIntro = findViewById(R.id.upload_video_introduction);
        Button publish = findViewById(R.id.publish);
        ImageView returns = findViewById(R.id.returns);
        returns.setOnClickListener(v -> {
            StringPool.VIDEO = null;
            StringPool.IMAGE = null;
            this.finish();
        });
        BaseTool.setEditTextTypeFace(uploadVideoIntro, getAssets());
        BaseTool.setButtonTypeFace(publish, getAssets());
        VideoView uploadVideo = findViewById(R.id.upload_video);
        ImageView uploadCover = findViewById(R.id.upload_cover);
        uploadVideo.setOnClickListener(new VideoAndImageListener(this, StringPool.VIDEO_CODE, StringPool.VIDEO_TYPE, R.layout.dialog_album1));
        uploadCover.setOnClickListener(new VideoAndImageListener(this, StringPool.IMAGE_CODE, StringPool.IMAGE_TYPE, R.layout.dialog_album1));
        publish.setOnClickListener(new UploadButtonListener(this, uploadVideoIntro));
    }


    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case StringPool.VIDEO_CODE * StringPool.ALBUM_CODE:
                getVideo(data.getData());
                break;
            case StringPool.VIDEO_CODE * StringPool.CAMERA_CODE:
                getVideo(data.getData());
                break;
            case StringPool.IMAGE_CODE * StringPool.ALBUM_CODE:
                getCover(data.getData());
                break;
            case StringPool.IMAGE_CODE * StringPool.CAMERA_CODE:
                getCover(data.getData());
                break;
            default:
                break;
        }
    }

    private void getVideo(Uri uri) {
        VideoTool.getVideo(this, findViewById(R.id.upload_video), uri);
    }

    private void getCover(Uri uri) {
        VideoTool.getImage(this, findViewById(R.id.upload_cover), uri, "cover");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StringPool.VIDEO = null;
        StringPool.IMAGE = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoView uploadVideo = findViewById(R.id.upload_video);
        uploadVideo.start();
    }
}
