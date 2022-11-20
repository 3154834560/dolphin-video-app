package com.example.dolphin.activity.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.R;
import com.example.dolphin.activity.HomePageActivity;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.adapter.ViewPagerAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * @author 王景阳
 * @date 2022/10/10 16:47
 */

public class FindFragment extends Fragment {

    private final VideoService videoService = new VideoService();

    @SuppressLint("StaticFieldLeak")
    private static ViewPagerAdapter pagerAdapter;

    private static ViewPager2 viewPager2;

    public static ViewPager2 getViewPager2() {
        return viewPager2;
    }

    public static ViewPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.video_page, container, false);
        viewPager2 = inflate.findViewById(R.id.video);
        initViewPager2(inflate.getContext(), viewPager2);
        return inflate;
    }

    private void initViewPager2(Context context, ViewPager2 viewPager2) {
        pagerAdapter = new ViewPagerAdapter(context, StringPool.videos);
        addPagerAdapter(viewPager2, pagerAdapter);
    }

    private void addPagerAdapter(ViewPager2 viewPager2, ViewPagerAdapter pagerAdapter) {
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            boolean first = true;

            boolean upIsZero = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (!first && position + positionOffset + positionOffsetPixels == 0 && upIsZero) {
                    VideoTool.stopPlay(FindFragment.getViewPager2());
                    videoService.updateVideo(getContext());
                }
                if (first) {
                    first = false;
                }
                upIsZero = position + positionOffset + positionOffsetPixels == 0;
            }


            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 大于0说明有播放
                int playPosition = GSYVideoManager.instance().getPlayPosition();
                if (Math.abs(StringPool.videos.size() - playPosition + 1) <= StringPool.VIDEO_UPDATE_DOT) {
                    videoService.addVideo(getContext());
                } else if (playPosition >= 0) {
                    // 对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemHolder.TAG)
                            && (position != playPosition)) {
                        VideoTool.startPlay(viewPager2);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager2.post(() -> VideoTool.startPlay(viewPager2));
        pagerAdapter.initConcernIcon(viewPager2.getCurrentItem());
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoTool.stopPlay(viewPager2);
    }
}
