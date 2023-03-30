package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.dolphin.R;
import com.example.dolphin.application.dto.output.CommentOutput;
import com.example.dolphin.application.service.CommentService;
import com.example.dolphin.infrastructure.adapter.CommentListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import lombok.AllArgsConstructor;

/**
 * @author 王景阳
 * @date 2023/3/29 13:04
 */
@AllArgsConstructor
public class CommentSendListener implements View.OnClickListener {
    private Activity activity;
    private LinearLayout linearLayout;

    private String videoId;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        EditText editText = linearLayout.findViewById(R.id.comment_edit_text);
        if (editText.getText() == null || editText.getText().toString().trim().isEmpty()) {
            BaseTool.shortToast(linearLayout.getContext(), StringPool.CONTENT_CANNOT_BE_EMPTY);
            return;
        }
        sendComment(editText);
        updateData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateData() {
        TextView commentNumber = activity.findViewById(R.id.comment_number);
        ListView commentList = linearLayout.findViewById(R.id.comment_list_view);
        commentNumber.setText(BaseTool.numberToString(StringPool.COMMENT_COUNT_MAP.get(videoId)));
        ((CommentListViewAdapter) commentList.getAdapter()).notifyDataSetChanged();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendComment(EditText editText) {
        CommentService service = new CommentService();
        CommentOutput output = new CommentOutput();
        output.setContent(editText.getText().toString());
        output.setUserName(StringPool.CURRENT_USER.getUserName());
        output.setVideoId(videoId);
        service.comment(linearLayout.getContext(), output);
    }
}
