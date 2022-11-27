package com.example.dolphin.activity.tools.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolphin.R;


public class BMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_page);
        final Double[] bmi = {1.0};
        final Double[] h = {1.0};
        final Double[] w = {1.0};
        final String[] caus = {"未知"};
        EditText height = findViewById(R.id.m);
        EditText weight = findViewById(R.id.kg);
        Button cacu = findViewById(R.id.cacu);
        TextView conclution = findViewById(R.id.conclution);
        Intent it = this.getIntent();

        cacu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = 1;
                if (String.valueOf(height.getText()).equals("") || String.valueOf(weight.getText()).equals("")) {
                    t = 0;
                    Toast.makeText(BMIActivity.this, "体重或身高不能为空！", Toast.LENGTH_LONG).show();
                }
                if (t == 1) {
                    try {
                        h[0] = Double.valueOf(String.valueOf(height.getText()));
                        w[0] = Double.valueOf(String.valueOf(weight.getText()));
                    } catch (Exception e) {
                        t = 0;
                        Toast.makeText(BMIActivity.this, "数据格式有误，请重新输入！", Toast.LENGTH_LONG).show();
                    }
                }
                RadioButton r1 = findViewById(R.id.who);
                RadioButton r2 = findViewById(R.id.china);
                if ((!r1.isChecked()) && (!r2.isChecked())) {
                    t = 0;
                    Toast.makeText(BMIActivity.this, "请选择相应BMI标准！", Toast.LENGTH_LONG).show();
                }
                if (t == 1) {
                    bmi[0] = w[0] / h[0] / h[0];
                    if (r1.isChecked()) {
                        if (bmi[0] < 18.5) {
                            caus[0] = "体重过低";
                        } else {
                            if (bmi[0] < 25) {
                                caus[0] = "体重正常";
                            } else {
                                if (bmi[0] < 30) {
                                    caus[0] = "体重超重";
                                } else {
                                    if (bmi[0] >= 30) {
                                        caus[0] = "体重肥胖";
                                    }
                                }
                            }
                        }
                    }
                    if (r2.isChecked()) {
                        if (bmi[0] < 18.5) {
                            caus[0] = "体重过低";
                        } else {
                            if (bmi[0] < 24) {
                                caus[0] = "体重正常";
                            } else {
                                if (bmi[0] < 27) {
                                    caus[0] = "体重超重";
                                } else {
                                    if (bmi[0] >= 27) {
                                        caus[0] = "体重肥胖";
                                    }
                                }
                            }
                        }
                    }
                    conclution.setText(caus[0]);
                }
            }
        });
    }
}