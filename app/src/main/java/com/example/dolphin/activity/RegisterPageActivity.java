package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.enums.SexEnum;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.FemaleButtonListener;
import com.example.dolphin.infrastructure.listeners.MaleButtonListener;
import com.example.dolphin.infrastructure.listeners.RegisterButtonListener;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/14 17:47
 */
public class RegisterPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        initTypeFace();
        Intent intent = getIntent();
        String type = intent.getStringExtra(StringPool.TYPE);
        if (type != null && type.equals(StringPool.UPDATE)) {
            initData();
        }
        addListener(type);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi", "ResourceType"})
    private void initData() {
        User user = StringPool.CURRENT_USER;
        LinearLayout linear = findViewById(R.id.register_user_name_layout);
        EditText userName = findViewById(R.id.register_userName2);
        EditText password = findViewById(R.id.register_password2);
        EditText nick = findViewById(R.id.register_nick2);
        EditText birthday = findViewById(R.id.register_birthday2);
        EditText phone = findViewById(R.id.register_phone2);
        RadioGroup sex = findViewById(R.id.sex);
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        Button button = findViewById(R.id.register_and_login);
        linear.setBackground(getDrawable(R.drawable.shape_6));
        userName.setBackground(getDrawable(R.drawable.shape_7));
        userName.setFocusable(false);
        userName.setClickable(false);
        userName.setText(user.getUserName());
        password.setText(user.getPassword());
        nick.setText(user.getNick());
        phone.setText(user.getPhone());
        sex.clearCheck();
        if (user.getSex().equals(SexEnum.MALE)) {
            male.setChecked(true);
        } else {
            female.setChecked(true);
        }
        if (user.getBirthday().toEpochSecond(ZoneOffset.ofHours(StringPool.EIGHT)) != StringPool.ZERO) {
            birthday.setText(DateTimeFormatter.ofPattern("yyyy.MM.dd").format(user.getBirthday()));
        }
        button.setText(StringPool.UPDATE);
    }

    private void initTypeFace() {
        List<TextView> textViews = Arrays.asList(findViewById(R.id.register_topic), findViewById(R.id.register_userName1)
                , findViewById(R.id.register_password1), findViewById(R.id.register_nick1)
                , findViewById(R.id.register_birthday1), findViewById(R.id.register_phone1));
        List<EditText> editTexts = Arrays.asList(findViewById(R.id.register_userName2), findViewById(R.id.register_password2)
                , findViewById(R.id.register_nick2), findViewById(R.id.register_birthday2)
                , findViewById(R.id.register_phone2));
        List<Button> radioButtons = Arrays.asList(findViewById(R.id.male), findViewById(R.id.female), findViewById(R.id.register_and_login));
        BaseTool.setTextTypeFace(textViews, getAssets());
        BaseTool.setEditTextTypeFace(editTexts, getAssets());
        BaseTool.setButtonTypeFace(radioButtons, getAssets());
    }

    private void addListener(String type) {
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        male.setOnClickListener(new MaleButtonListener(male, female));
        female.setOnClickListener(new FemaleButtonListener(male, female));
        List<EditText> editTexts = Arrays.asList(findViewById(R.id.register_userName2), findViewById(R.id.register_password2)
                , findViewById(R.id.register_nick2), findViewById(R.id.register_birthday2)
                , findViewById(R.id.register_phone2));
        Button register = findViewById(R.id.register_and_login);
        register.setOnClickListener(new RegisterButtonListener(this, editTexts, findViewById(R.id.sex), type));
    }
}
