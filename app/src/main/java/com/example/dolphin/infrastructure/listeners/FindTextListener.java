package com.example.dolphin.infrastructure.listeners;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.VideoTool;

/**
 * @author 王景阳
 * @date 2022/11/12 16:49
 */
public class FindTextListener implements View.OnClickListener {

    private final int currentItem;

    private final ViewPager2 homePageView;

    /**
     * 点击次数
     */
    private int count = 0;
    /**
     * 第一次点击时间
     */
    private long firstClick = 0;

    /**
     * 第二次点击时间
     */
    private long secondClick = 0;

    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 500;

    public FindTextListener(ViewPager2 homePageView, int currentItem) {
        this.homePageView = homePageView;
        this.currentItem = currentItem;
    }

    @Override
    public void onClick(View v) {

        //点击
        count++;
        if (StringPool.ONE == count) {
            //记录第一次点击时间
            firstClick = System.currentTimeMillis();
        } else if (StringPool.TWO == count) {
            //记录第二次点击时间
            secondClick = System.currentTimeMillis();
            //判断二次点击时间间隔是否在设定的间隔时间之内
            if (secondClick - firstClick < totalTime) {
                count = 0;
                firstClick = 0;

            } else {
                firstClick = secondClick;
                count = 1;
            }
            secondClick = 0;
        }
        if (homePageView.getCurrentItem() == currentItem) {
            VideoTool.stopPlay(FindFragment.getViewPager2());
            VideoService.updateVideo(homePageView.getContext());
        }
        homePageView.setCurrentItem(currentItem, false);
    }

}
