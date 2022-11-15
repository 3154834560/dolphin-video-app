package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.LoginButtonListener;
import com.example.dolphin.infrastructure.listeners.ProtocolButtonListener;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author 王景阳
 * @date 2022/11/13 20:56
 */
public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initCurrentData();
    }

    private void initCurrentData() {
        initTypeFace();
        initProtocol();
        initButton();
    }

    @SuppressLint("NewApi")
    private void initProtocol() {
        TextView protocol = findViewById(R.id.login_protocol);
        protocol.setText(Html.fromHtml(StringPool.PROTOCOL, Html.FROM_HTML_MODE_COMPACT));
        BaseTool.setTextTypeFace(protocol, getAssets());
        RadioButton protocolButton = findViewById(R.id.login_protocol_button);
        protocolButton.setOnClickListener(new ProtocolButtonListener(findViewById(R.id.login_protocol_group), protocolButton));
    }


    private void initTypeFace() {
        List<TextView> textViews = Arrays.asList(findViewById(R.id.login_userName), findViewById(R.id.login_password));
        List<Button> buttons = Arrays.asList(findViewById(R.id.login_protocol_button), findViewById(R.id.register), findViewById(R.id.login));
        BaseTool.setTextTypeFace(textViews, getAssets());
        BaseTool.setButtonTypeFace(buttons, getAssets());
    }

    private void initButton() {
        Button register = findViewById(R.id.register);
        Button login = findViewById(R.id.login);
        TextView userName = findViewById(R.id.login_userName);
        TextView password = findViewById(R.id.login_password);
        login.setOnClickListener(new LoginButtonListener(this, userName, password, findViewById(R.id.login_protocol_button)));
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPageActivity.this, RegisterPageActivity.class);
            finish();
            startActivity(intent);
        });
    }
}
