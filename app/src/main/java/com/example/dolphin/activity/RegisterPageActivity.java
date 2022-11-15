package com.example.dolphin.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.listeners.FemaleButtonListener;
import com.example.dolphin.infrastructure.listeners.MaleButtonListener;
import com.example.dolphin.infrastructure.listeners.RegisterButtonListener;
import com.example.dolphin.infrastructure.tool.BaseTool;

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
        addListener();
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

    private void addListener() {
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        male.setOnClickListener(new MaleButtonListener(male, female));
        female.setOnClickListener(new FemaleButtonListener(male, female));
        List<EditText> editTexts = Arrays.asList(findViewById(R.id.register_userName2), findViewById(R.id.register_password2)
                , findViewById(R.id.register_nick2), findViewById(R.id.register_birthday2)
                , findViewById(R.id.register_phone2));
        Button register = findViewById(R.id.register_and_login);
        register.setOnClickListener(new RegisterButtonListener(this, editTexts, findViewById(R.id.sex)));
    }
}
