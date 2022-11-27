package com.example.dolphin.activity.tools.guessNumber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dolphin.R;

public class GussNumberGame extends BaseActivity {

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_in);

        start = findViewById(R.id.main_btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到游戏界面
                navigateTo(StartGame.class);
            }
        });
    }
}