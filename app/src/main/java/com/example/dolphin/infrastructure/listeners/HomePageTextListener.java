package com.example.dolphin.infrastructure.listeners;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.dolphin.R;

/**
 * @author 王景阳
 * @date 2022/11/12 20:26
 */
public class HomePageTextListener implements View.OnClickListener {

    private final Resources resources;

    private final TextView homePageText;

    private final TextView meText;


    public HomePageTextListener(Resources resources, TextView homePageText, TextView meText) {
        this.homePageText = homePageText;
        this.resources = resources;
        this.meText=meText;
    }

    @Override
    public void onClick(View v) {

    }
}
