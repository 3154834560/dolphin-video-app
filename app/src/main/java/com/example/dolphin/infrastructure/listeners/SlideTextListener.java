package com.example.dolphin.infrastructure.listeners;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

/**
 * @author 王景阳
 * @date 2022/11/12 16:49
 */
public class SlideTextListener implements View.OnClickListener {

    private final int currentItem;

    private final ViewPager2 viewPager2;

    public SlideTextListener(ViewPager2 viewPager2, int currentItem) {
        this.viewPager2 = viewPager2;
        this.currentItem = currentItem;
    }

    @Override
    public void onClick(View v) {
        viewPager2.setCurrentItem(currentItem, false);
    }
}
