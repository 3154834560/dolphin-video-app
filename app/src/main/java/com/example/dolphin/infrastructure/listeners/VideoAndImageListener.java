package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.application.service.DownloadService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

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

    private int layoutId;

    @Override
    public void onClick(View v) {
        showPictureDialog(activity, layoutId, requestCode, fileType);
    }

    public void showPictureDialog(Activity activity, int layoutId, int requestCode, String fileType) {
        //指明Dialog容器弹出的动画风格
        Dialog dialog = new Dialog(activity, R.style.JumpDialog);
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(activity).inflate(layoutId, null);
        TextView photograph = (TextView) linear.findViewById(R.id.photograph);
        TextView album = (TextView) linear.findViewById(R.id.album);
        TextView cancel = (TextView) linear.findViewById(R.id.cancel);

        if (layoutId == R.layout.dialog_album2 || layoutId == R.layout.dialog_album3) {
            TextView downPhotos = (TextView) linear.findViewById(R.id.down_photos);
            downPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownloadService downloadService = new DownloadService();
                    String headPortraitUrl = StringPool.CURRENT_USER.getHeadPortraitUrl();
                    String imageName = headPortraitUrl.substring(headPortraitUrl.lastIndexOf(StringPool.SLASH) + 1);
                    downloadService.downloadFile(activity, StringPool.IMAGES, imageName);
                }
            });
        }
        if (layoutId != R.layout.dialog_album3) {
            //点击拍照
            photograph.setOnClickListener(view -> {
                deleteDialog(dialog);
                //发送打开相机通知
                BaseTool.shortToast(activity, "该功能暂未实现！");
        /*    String dir = StringPool.ALBUM_PATH + System.currentTimeMillis() + StringPool.PHOTO_TYPE;
            Uri headCacheUri = Uri.fromFile(new File(dir));
            Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, headCacheUri);
            activity.startActivityForResult(takePicIntent, StringPool.CAMERA_CODE * requestCode);*/
            });
            //点击相册，发送相册的广播，这个相册广播是在 MainActivity中注册的
            album.setOnClickListener(view -> {
                deleteDialog(dialog);
                //打开相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setTypeAndNormalize(fileType);
                activity.startActivityForResult(intent, StringPool.ALBUM_CODE * requestCode);
            });
        }
        //取消Dialog
        cancel.setOnClickListener(view -> deleteDialog(dialog));
        bindDialogAndLayout(activity, dialog, linear);
    }

    public void bindDialogAndLayout(Context context, Dialog dialog, LinearLayout linear) {
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
        layoutParams.width = (int) context.getResources().getDisplayMetrics().widthPixels;
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

    public void deleteDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
