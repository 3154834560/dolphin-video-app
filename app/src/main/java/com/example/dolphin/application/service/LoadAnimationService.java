package com.example.dolphin.application.service;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dolphin.R;

import lombok.RequiredArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/27 20:02
 */
@RequiredArgsConstructor
public class LoadAnimationService {

    private final Activity activity;

    private Dialog dialog = null;

    private ImageView imageView = null;


    public Dialog getDialog() {
        if (dialog == null) {
            createLoadAnimation();
        }
        return dialog;
    }

    public ImageView getImageView() {
        if (imageView == null) {
            createLoadAnimation();
        }
        return imageView;
    }

    public void createLoadAnimation() {
        //指明Dialog容器弹出的动画风格
        dialog = new Dialog(activity, R.style.JumpDialog);
        //根据layout文件绘制出加载动画的视图
        ConstraintLayout linear = (ConstraintLayout) LayoutInflater.from(activity).inflate(R.layout.load_animation_page, null);
        imageView = linear.findViewById(R.id.load_icon);
        bindDialogAndLayout(activity, dialog, linear);
    }

    private void bindDialogAndLayout(Context context, Dialog dialog, ConstraintLayout linear) {
        //将视图加入容器
        dialog.setContentView(linear);
        //获得窗口
        Window dialogWindow = dialog.getWindow();
        //放置在底部
        dialogWindow.setGravity(Gravity.TOP);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        // 新位置X坐标
        layoutParams.x = 0;
        // 新位置Y坐标
        layoutParams.y = 0;
        // 宽度
        layoutParams.width = (int) context.getResources().getDisplayMetrics().widthPixels;
        linear.measure(0, 0);
        layoutParams.height = (int) context.getResources().getDisplayMetrics().heightPixels;
        // 透明度
        layoutParams.alpha = 1;
        dialogWindow.setAttributes(layoutParams);
        //设置setCancelable(true)时，点击ProgressDialog以外的区域的时候ProgressDialog就会关闭
        // 反之设置setCancelable(false)时，点击ProgressDialog以外的区域不会关闭ProgressDialog
        dialog.setCancelable(true);
        dialog.show();
    }
}
