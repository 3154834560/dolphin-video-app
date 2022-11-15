package com.example.dolphin.infrastructure.listeners;

import android.view.View;
import android.widget.RadioButton;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author 王景阳
 * @date 2022/11/14 17:54
 */
@AllArgsConstructor
@NoArgsConstructor
public class MaleButtonListener implements View.OnClickListener {

    private RadioButton male;

    private RadioButton female;

    @Override
    public void onClick(View v) {
        male.setChecked(true);
        female.setChecked(false);
    }
}
