package com.example.dolphin.infrastructure.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.VideoTool;


/**
 * @author 王景阳
 * @date 2022/11/12 20:26
 */
public class HomePageTextListener implements View.OnClickListener {

    private final Activity activity;

    private final Class<?> currentClass;

    private final Class<?> homeClass;

    private final ViewPager2 homePageView;

    private final VideoService videoService = new VideoService();

    public HomePageTextListener(Activity activity, Class<?> currentClass, Class<?> homeClass, ViewPager2 homePageView) {
        this.activity = activity;
        this.currentClass = currentClass;
        this.homeClass = homeClass;
        this.homePageView = homePageView;
    }

    @Override
    public void onClick(View v) {
        if (currentClass != homeClass) {
            activity.finish();
            return;
        }
        if (homePageView.getCurrentItem() == StringPool.HOME_PAGE_SITE) {
            VideoTool.stopPlay(FindFragment.getViewPager2());
            videoService.updateVideo(homePageView.getContext());
        } else {
            homePageView.setCurrentItem(StringPool.HOME_PAGE_SITE, false);
        }
    }

}
