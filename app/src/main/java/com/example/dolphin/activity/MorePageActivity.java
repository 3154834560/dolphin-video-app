package com.example.dolphin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2022/11/19 19:45
 */
public class MorePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_page);
        initData();
    }

    private void initData() {
        User user = StringPool.CURRENT_USER;
        LinearLayout linear = findViewById(R.id.more_linear);
        CircleImageView headPortrait = findViewById(R.id.more_head);
        TextView nick = findViewById(R.id.more_nick);
        Button logOut = findViewById(R.id.log_out);

        BaseTool.setTextTypeFace(nick, getAssets());
        BaseTool.setButtonTypeFace(logOut, getAssets());

        linear.setOnClickListener(v -> {
            startActivity(new Intent(MorePageActivity.this, user == null ? LoginPageActivity.class : MePageActivity.class));
            finish();
        });
        if (user != null) {
            Glide.with(this).load(user.getHeadPortraitUrl()).into(headPortrait);
            nick.setText(user.getNick());
            logOut.setOnClickListener(v -> {
                UserService userService = new UserService();
                userService.writeLoginInfo(MorePageActivity.this, StringPool.CURRENT_USER);
                StringPool.CURRENT_USER = null;
                StringPool.CONCERN = null;
                finish();
            });
        } else {
            nick.setText(StringPool.NOT_LOGIN);
            logOut.setOnClickListener(v -> {
                BaseTool.shortToast(MorePageActivity.this, StringPool.CURRENT_NOT_LOGIN);
            });
        }
    }
}
