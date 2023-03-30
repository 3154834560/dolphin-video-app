package com.example.dolphin.activity.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.adapter.ViewPagerAdapter;
import com.example.dolphin.infrastructure.callbacks.VideoPageCallback;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.tool.VideoTool;

/**
 * 视频滑动播放页面
 *
 * @author 王景阳
 * @date 2022/10/10 16:47
 */

public class FindFragment extends Fragment {

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
        addPagerAdapter(context, viewPager2, pagerAdapter);
    }

    private void addPagerAdapter(Context context, ViewPager2 viewPager2, ViewPagerAdapter pagerAdapter) {
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.registerOnPageChangeCallback(new VideoPageCallback(context, viewPager2));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        viewPager2.post(() -> VideoTool.startPlay(viewPager2));
        pagerAdapter.initIcon(viewPager2.getCurrentItem());
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoTool.stopPlay(viewPager2);
    }
}
