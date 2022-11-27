package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dolphin.application.service.CollectionService;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * @author 王景阳
 * @date 2022/11/13 21:48
 */

@AllArgsConstructor
@NoArgsConstructor
public class LoginButtonListener implements View.OnClickListener {

    private final UserService userService = new UserService();

    private Activity activity;

    private TextView userName;

    private TextView password;

    private RadioButton protocolButton;


    @Override
    public void onClick(View v) {
        if (!protocolButton.isChecked()) {
            BaseTool.shortToast(activity, StringPool.SELECT_PROTOCOL);
            return;
        }
        Integer verifyResult = userService.verify(activity, userName.getText().toString(), password.getText().toString());
        if (verifyResult.equals(StringPool.TWO)) {
            StringPool.CURRENT_USER = userService.getBy(activity, userName.getText().toString());
            ConcernService concernService=new ConcernService();
            concernService.getAllConcern(activity);
            CollectionService collectionService=new CollectionService();
            collectionService.getAllCollection(activity);
            StringPool.INDEX = 0;
            userService.writeLoginInfo(activity, StringPool.CURRENT_USER);
            activity.finish();
            return;
        }
        BaseTool.shortToast(activity, StringPool.MSG.get(verifyResult));
    }

}
