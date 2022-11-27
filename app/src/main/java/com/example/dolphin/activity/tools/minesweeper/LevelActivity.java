package com.example.dolphin.activity.tools.minesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;

public class LevelActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_level_page);
    }

    /*根据不同难度返回相应参数*/
    public void Easy(View view) {
        Intent data = new Intent();
        data.putExtra("result", "easy");
        setResult(2, data);
        finish();
    }

    public void Hard(View view) {
        Intent data = new Intent();
        data.putExtra("result", "hard");
        setResult(2, data);
        finish();
    }

    public void Return(View view) {
        Intent data = new Intent();
        data.putExtra("result", "");
        setResult(2, data);
        finish();
    }
}
