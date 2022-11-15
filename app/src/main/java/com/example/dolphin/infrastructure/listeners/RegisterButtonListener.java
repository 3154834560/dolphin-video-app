package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/14 17:48
 */

public class RegisterButtonListener implements View.OnClickListener {

    private final Activity activity;

    private final RadioGroup sexGroup;

    private final List<EditText> editTexts;

    public RegisterButtonListener(Activity activity, List<EditText> editTexts, RadioGroup sexGroup) {
        this.activity = activity;
        this.editTexts = editTexts;
        this.sexGroup = sexGroup;
    }

    @Override
    public void onClick(View v) {
        UserOutput userOutput = new UserOutput();
        userOutput.copy(editTexts, sexGroup);
        if (userOutput.getUserName().isEmpty()) {
            BaseTool.shortToast(activity, "请输入用户名！");
        } else if (userOutput.getPassword().isEmpty()) {
            BaseTool.shortToast(activity, "请输入密码！");
        } else if (userOutput.getNick().isEmpty()) {
            BaseTool.shortToast(activity, "请输入昵称！");
        } else {
            Integer verify = UserService.verify(activity, userOutput.getUserName(), "");
            if (!verify.equals(StringPool.ZERO)) {
                BaseTool.shortToast(activity, "用户名：" + userOutput.getUserName() + " 已存在！");
            } else {
                StringPool.CURRENT_USER = UserService.create(activity, userOutput);
                StringPool.INDEX = 0;
                UserService.writeLoginInfo(activity, StringPool.CURRENT_USER);
                activity.finish();
            }
        }
    }

}
