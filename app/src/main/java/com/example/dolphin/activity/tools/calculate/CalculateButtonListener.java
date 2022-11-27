package com.example.dolphin.activity.tools.calculate;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.consts.ColorPool;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.CalculateTool;

/**
 * 计算器按钮监听类
 *
 * @author 王景阳
 * @date 2022/9/22 13:06
 */
public class CalculateButtonListener implements View.OnClickListener {

    private Button button;

    private AppCompatActivity appCompatActivity;

    public CalculateButtonListener() {
    }

    public CalculateButtonListener(Button button, AppCompatActivity appCompatActivity) {
        this.button = button;
        this.appCompatActivity = appCompatActivity;
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onClick(View v) {
        TextView equationText = appCompatActivity.findViewById(R.id.t1_r1_textView1);
        TextView answerText = appCompatActivity.findViewById(R.id.t1_r2_textView1);
        String equationStr = (String) equationText.getText();
        String buttonStr = (String) button.getText();
        if (StringPool.CALCULATION_SYMBOL.contains(buttonStr)) {
            equationText.setText(equationStr + buttonStr);
            answerText.setText(StringPool.EMPTY);
        } else if (buttonStr.equals(StringPool.C)) {
            answerText.setText(StringPool.EMPTY);
            equationText.setText(StringPool.EMPTY);
        } else if (buttonStr.equals(StringPool.CE)) {
            if (equationStr.length() > 0) {
                equationStr = equationStr.substring(0, equationStr.length() - 1);
            }
            equationText.setText(equationStr);
            if (equationStr.isEmpty()) {
                answerText.setText(StringPool.EMPTY);
            } else {
                if (endIsOperator(equationStr) || startIsOperator(equationStr) || isOperatorLinked(equationStr)) {
                    answerText.setText(StringPool.EMPTY);
                } else {
                    answerText.setText(CalculateTool.getAnswer(equationStr).stripTrailingZeros().toPlainString());
                }
            }
        } else {
            if (buttonStr.equals(StringPool.EQUATION)) {
                if (endIsOperator(equationStr) || startIsOperator(equationStr) || isOperatorLinked(equationStr)) {
                    answerText.setText(StringPool.EXPRESSION_ERROR);
                } else {
                    if(equationStr.length()>0) {
                        String answerStr = CalculateTool.getAnswer(equationStr).stripTrailingZeros().toPlainString();
                        answerText.setText(answerStr);
                        equationText.setText(StringPool.EMPTY);
                    }
                }
            } else {
                equationStr = equationStr + buttonStr;
                equationText.setText(equationStr);
                if (!endIsOperator(equationStr) && !startIsOperator(equationStr) && !isOperatorLinked(equationStr)) {
                    answerText.setText(CalculateTool.getAnswer(equationStr).stripTrailingZeros().toPlainString());
                } else {
                    answerText.setText(StringPool.EMPTY);
                }
            }
        }
        if (buttonStr.equals(StringPool.EQUATION)) {
            answerText.setTextSize(35);
            answerText.setTextColor(ColorPool.BLACK);
        } else {
            answerText.setTextSize(25);
            answerText.setTextColor(ColorPool.DARK_GREY);
        }
    }

    /**
     * 判断操作符（ ‘+’，‘—’，‘x'，'÷'，’ . ‘）是否相连
     */
    private boolean isOperatorLinked(String str) {
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            char upc = str.charAt(i - 1);
            if (isOperator(c) && isOperator(upc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是操作符（ ‘+’，‘—’，‘x'，'÷'，’ . ‘）
     */
    private boolean isOperator(char c) {
        return CalculateTool.isOperator(c) || c == StringPool.DOT_CHAR;
    }

    /**
     * 判断是否以操作符（ ‘+’，‘—’，‘x'，'÷'，’ . ‘）结尾
     */
    private boolean endIsOperator(String str) {
        return str.endsWith(StringPool.PLUS_SIGN_STR) || str.endsWith(StringPool.MINUS_SIGN_STR)
                || str.endsWith(StringPool.MULTIPLICATION_SIGN) || str.endsWith(StringPool.DIVISION_SIGN_STR)
                || str.endsWith(StringPool.DOT_STR);
    }

    /**
     * 判断是否以操作符（ ‘+’，‘—’，‘x'，'÷'，’.‘，’%‘）开头
     */
    private boolean startIsOperator(String str) {
        return str.startsWith(StringPool.PLUS_SIGN_STR) || str.startsWith(StringPool.MINUS_SIGN_STR)
                || str.startsWith(StringPool.MULTIPLICATION_SIGN) || str.startsWith(StringPool.DIVISION_SIGN_STR)
                || str.startsWith(StringPool.DOT_STR) || str.startsWith(StringPool.PERCENT_SIGN_STR);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }
}
