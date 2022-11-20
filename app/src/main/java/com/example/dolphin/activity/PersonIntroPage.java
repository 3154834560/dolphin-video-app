package com.example.dolphin.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.application.dto.output.UserOutput;
import com.example.dolphin.application.service.ImageService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.BaseTool;

/**
 * @author 王景阳
 * @date 2022/11/18 21:06
 */
public class PersonIntroPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_introduction);
        initData();
        addListener();
    }

    private void initData() {
        Button complete = findViewById(R.id.complete_button);

        BaseTool.setTextTypeFace((TextView) findViewById(R.id.set_person_introduction), getAssets());
        BaseTool.setButtonTypeFace(complete, getAssets());
        BaseTool.setEditTextTypeFace((EditText) findViewById(R.id.person_introduction), getAssets());
    }

    private void addListener() {
        ImageView returns = findViewById(R.id.person_introduction_return);
        returns.setOnClickListener(v -> finish());

        Button complete = findViewById(R.id.complete_button);
        complete.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.person_introduction);
            String introduction = editText.getText().toString();
            if (introduction.isEmpty()) {
                return;
            }
            StringPool.CURRENT_USER.setIntroduction(introduction);
            UserService userService = new UserService();
            userService.update(this, UserOutput.copy(StringPool.CURRENT_USER));
            finish();
        });

    }
}
