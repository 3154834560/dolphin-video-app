package com.example.dolphin.infrastructure.listeners;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * @author 王景阳
 * @date 2022/11/13 21:41
 */
public class ProtocolButtonListener implements View.OnClickListener {

    private final RadioGroup radioGroup;

    private final RadioButton radioButton;

    private boolean checked;

    public ProtocolButtonListener(RadioGroup radioGroup, RadioButton radioButton) {
        this.radioGroup = radioGroup;
        this.radioButton = radioButton;
        this.checked = false;
    }

    @Override
    public void onClick(View v) {
        checked = !checked;
        if (!checked) {
            radioGroup.clearCheck();
        } else {
            radioButton.setChecked(true);
        }
    }
}
