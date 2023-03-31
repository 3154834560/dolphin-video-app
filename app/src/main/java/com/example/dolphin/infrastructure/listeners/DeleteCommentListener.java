package com.example.dolphin.infrastructure.listeners;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.application.service.CommentService;
import com.example.dolphin.infrastructure.adapter.CommentListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2023/3/31 17:56
 */
@AllArgsConstructor
public class DeleteCommentListener implements View.OnClickListener {
    private Activity activity;

    private LinearLayout linearLayout;

    private CommentListViewAdapter listViewAdapter;

    private String videoId;

    private String commentId;

    @Override
    public void onClick(View v) {
        if (StringPool.CURRENT_USER == null) {
            BaseTool.shortToast(linearLayout.getContext(), StringPool.PLEASE_LOGIN);
            return;
        }
        CommentService service = new CommentService();
        service.unComment(activity, commentId);
        service.updateCommentBy(activity, videoId);
        updateData();
        listViewAdapter.notifyDataSetChanged();
    }

    @SuppressLint({"NewApi", "SetTextI18n"})
    private void updateData() {
        TextView topText = linearLayout.findViewById(R.id.comment_top_text);
        TextView commentNumber = activity.findViewById(R.id.comment_number);
        commentNumber.setText(BaseTool.numberToString(StringPool.COMMENT_COUNT_MAP.getOrDefault(videoId, 0)));
        topText.setText(StringPool.COMMENT_COUNT_MAP.getOrDefault(videoId, 0) + "条评论");
    }
}
