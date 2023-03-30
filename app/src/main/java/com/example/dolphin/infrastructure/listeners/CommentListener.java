package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.activity.AuthorInfoActivity;
import com.example.dolphin.application.dto.input.CommentInput;
import com.example.dolphin.application.service.CommentService;
import com.example.dolphin.infrastructure.adapter.CommentListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.CommentListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2023/3/28 20:46
 */
@AllArgsConstructor
public class CommentListener implements View.OnClickListener {
    private Context context;

    private int layoutId;

    private String videoId;

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        //指明Dialog容器弹出的动画风格
        Dialog dialog = new Dialog(context, R.style.JumpDialog);
        //根据layout文件绘制出加载动画的视图
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context).inflate(layoutId, null);
        ListView commentList = (ListView) linear.findViewById(R.id.comment_list_view);
        TextView commentStr = (TextView) linear.findViewById(R.id.comment_top_text);
        ImageView cancel = (ImageView) linear.findViewById(R.id.comment_top_cancel);
        // todo 评论发送
        Button sendOut = (Button) linear.findViewById(R.id.comment_send_out);
        EditText editText = linear.findViewById(R.id.comment_edit_text);
        sendOut.setOnClickListener(new CommentSendListener((Activity) context, linear, videoId));
        commentListAddListener(commentList);
        BaseTool.setTextTypeFace(commentStr, context.getAssets());
        commentStr.setText(StringPool.COMMENT_COUNT_MAP.get(videoId) + "条评论");
        //取消Dialog
        cancel.setOnClickListener(view -> deleteDialog(dialog));
        bindDialogAndLayout(context, dialog, linear);
    }

    private void commentListAddListener(ListView commentList) {
        int[] layoutIds = new int[]{R.id.comment_user_head_portrait, R.id.comment_user_nick,
                R.id.comment_date_time, R.id.comment_content};
        String[] itemNames = new String[]{"commentList"};
        List<Map<String, CommentListView>> listItems = getCommentData(context, videoId);
        CommentListViewAdapter listViewAdapter = new CommentListViewAdapter(context, listItems, R.layout.comment_list_view, itemNames, layoutIds, videoId);
        commentList.setAdapter(listViewAdapter);
    }

    @SuppressLint("NewApi")
    public static List<Map<String, CommentListView>> getCommentData(Context context, String videoId) {
        List<Map<String, CommentListView>> listItems = new ArrayList<>();
        String[] itemNames = new String[]{"commentList"};
        if (!StringPool.COMMENT_INPUT_MAP.containsKey(videoId) || StringPool.COMMENT_INPUT_MAP.get(videoId) == null) {
            CommentService service = new CommentService();
            service.updateCommentBy(context, videoId);
        }
        for (CommentInput m : Objects.requireNonNull(StringPool.COMMENT_INPUT_MAP.getOrDefault(videoId, new ArrayList<>()))) {
            CommentListView commentListView = new CommentListView();
            commentListView.setContent(m.getContent());
            commentListView.setCreateAt(m.getCreateAt());
            commentListView.setNick(m.getNick());
            commentListView.setUserName(m.getUserName());
            commentListView.setHeadPortraitName(m.getHeadPortraitName());
            commentListView.setContent(m.getContent());
            commentListView.setNextClass(AuthorInfoActivity.class);
            Map<String, CommentListView> listViewMap = new HashMap<>();
            listViewMap.put(itemNames[0], commentListView);
            listItems.add(listViewMap);
        }
        return listItems;
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
