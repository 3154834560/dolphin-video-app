package com.example.dolphin.infrastructure.listeners;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.adapter.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/12 17:02
 */
public class HomeViewPageListener extends ViewPager2.OnPageChangeCallback {

    private final Resources resources;

    private final List<TextView> topTexts;

    public HomeViewPageListener(Resources resources, List<TextView> topTexts) {
        this.topTexts = topTexts;
        this.resources = resources;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        for (int i = 0; i < topTexts.size(); i++) {
            if (i == position) {
                topTexts.get(i).setBackground(ResourcesCompat.getDrawable(resources, R.drawable.underline, null));
            } else {
                topTexts.get(i).setBackground(ResourcesCompat.getDrawable(resources, R.drawable.empty, null));
            }
        }
    }
}
