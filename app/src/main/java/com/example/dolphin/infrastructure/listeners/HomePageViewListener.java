package com.example.dolphin.infrastructure.listeners;

import android.content.res.Resources;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.R;

import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/12 17:02
 */
public class HomePageViewListener extends ViewPager2.OnPageChangeCallback {

    private final Resources resources;

    private final List<TextView> topTexts;

    private final int underline;

    public HomePageViewListener(Resources resources, List<TextView> topTexts,int underline) {
        this.topTexts = topTexts;
        this.resources = resources;
        this.underline=underline;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        for (int i = 0; i < topTexts.size(); i++) {
            if (i == position) {
                topTexts.get(i).setBackground(ResourcesCompat.getDrawable(resources, underline, null));
            } else {
                topTexts.get(i).setBackground(ResourcesCompat.getDrawable(resources, R.drawable.empty, null));
            }
        }
    }
}
