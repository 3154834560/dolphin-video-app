package com.example.dolphin.activity.tools.calculate;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;

/**
 * @author 王景阳
 * @date 2022/11/24 16:34
 */
public class CalculateActivity extends AppCompatActivity {

    /**
     * 计算器按钮（Button）的 id
     */
    private final int[] calculateButtonIds = new int[]{
            R.id.t1_r3_button1, R.id.t1_r3_button2, R.id.t1_r3_button3, R.id.t1_r3_button4,
            R.id.t1_r4_button1, R.id.t1_r4_button2, R.id.t1_r4_button3, R.id.t1_r4_button4,
            R.id.t1_r5_button1, R.id.t1_r5_button2, R.id.t1_r5_button3, R.id.t1_r5_button4,
            R.id.t1_r6_button1, R.id.t1_r6_button2, R.id.t1_r6_button3, R.id.t1_r6_button4,
            R.id.t1_r7_button1, R.id.t1_r7_button2, R.id.t1_r7_button3, R.id.t1_r7_button4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_page);
        addCalculateListener();
    }

    /**
     * 为按钮计算跳转监听器
     * 实现计算器功能
     */
    public void addCalculateListener() {
        for (Integer id : calculateButtonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new CalculateButtonListener(button, this));
        }
    }
}
