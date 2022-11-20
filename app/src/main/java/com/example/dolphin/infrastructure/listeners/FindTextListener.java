package com.example.dolphin.infrastructure.listeners;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.tool.VideoTool;

/**
 * @author 王景阳
 * @date 2022/11/12 16:49
 */
public class FindTextListener implements View.OnClickListener {

    private final int currentItem;

    private final VideoService videoService = new VideoService();

    private final ViewPager2 homePageView;

    public FindTextListener(ViewPager2 homePageView, int currentItem) {
        this.homePageView = homePageView;
        this.currentItem = currentItem;
    }

    @Override
    public void onClick(View v) {

        if (homePageView.getCurrentItem() == currentItem) {
            VideoTool.stopPlay(FindFragment.getViewPager2());
            videoService.updateVideo(homePageView.getContext());
        }
        homePageView.setCurrentItem(currentItem, false);
    }

}
