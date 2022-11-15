package com.example.dolphin.infrastructure.media;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.consts.StringPool;

import java.io.File;

/**
 * @author 王景阳
 * @date 2022/11/12 10:24
 */
public class DialogContainer {

    private static Dialog dialog;

    /**
     * 弹出图片选择对话框，选择拍照、还是本地相册读取
     *
     * @param activity    上下文
     * @param layoutId    需弹出布局id，
     * @param requestCode 判断是
     * @param fileType 过滤相册内容，只显示”图片“或”视频“
     */
    public static void showPictureDialog(Activity activity, int layoutId, int requestCode,String fileType) {
        //指明Dialog容器弹出的动画风格
        dialog = new Dialog(activity, R.style.JumpDialog);
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(activity).inflate(layoutId, null);
        TextView photograph = (TextView) linear.findViewById(R.id.photograph);
        TextView album = (TextView) linear.findViewById(R.id.album);
        TextView cancel = (TextView) linear.findViewById(R.id.cancel);
        //点击拍照
        photograph.setOnClickListener(view -> {
            deleteDialog();
            //发送打开相机通知
            String dir = StringPool.ALBUM_PATH + System.currentTimeMillis() + StringPool.PHOTO_TYPE;
            Uri headCacheUri = Uri.fromFile(new File(dir));
            Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, headCacheUri);
            activity.startActivityForResult(takePicIntent, StringPool.CAMERA_CODE * requestCode);
        });
        //点击相册，发送相册的广播，这个相册广播是在 MainActivity中注册的
        album.setOnClickListener(view -> {
            deleteDialog();
            //打开相册
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setTypeAndNormalize(fileType);
            activity.startActivityForResult(intent, StringPool.ALBUM_CODE * requestCode);
        });
        //取消Dialog
        cancel.setOnClickListener(view -> deleteDialog());
        //将视图加入容器
        dialog.setContentView(linear);
        //获得窗口
        Window dialogWindow = dialog.getWindow();
        //放置在底部
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        // 新位置X坐标
        layoutParams.x = 0;
        // 新位置Y坐标
        layoutParams.y = 0;
        // 宽度
        layoutParams.width = (int) activity.getResources().getDisplayMetrics().widthPixels;
        linear.measure(0, 0);
        layoutParams.height = linear.getMeasuredHeight();
        // 透明度
        layoutParams.alpha = 1;
        dialogWindow.setAttributes(layoutParams);
        //设置setCancelable(true)时，点击ProgressDialog以外的区域的时候ProgressDialog就会关闭
        // 反之设置setCancelable(false)时，点击ProgressDialog以外的区域不会关闭ProgressDialog
        dialog.setCancelable(true);
        dialog.show();
    }


    public static void deleteDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
