package com.example.dolphin.infrastructure.callbacks;

import android.content.Context;

import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * 视频播放页（video_page.xml)的ViewPager2实例对象的页面更改回调方法类
 *
 * @author 王景阳
 * @date 2023/3/30 20:58
 */
public class VideoPageCallback extends ViewPager2.OnPageChangeCallback {

    private boolean first = true;

    private boolean upIsZero = true;

    private final VideoService videoService;

    private final Context context;

    private final ViewPager2 viewPager2;

    public VideoPageCallback(Context context, ViewPager2 viewPager2) {
        this.context = context;
        this.viewPager2 = viewPager2;
        videoService = new VideoService();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (!first && position + positionOffset + positionOffsetPixels == 0 && upIsZero) {
            VideoTool.stopPlay(FindFragment.getViewPager2());
            videoService.updateVideo(context);
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
            videoService.addVideo(context);
        } else if (playPosition >= 0) {
            // 对应的播放列表TAG
            if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemHolder.TAG)
                    && (position != playPosition)) {
                VideoTool.startPlay(viewPager2);
            }
        }
    }
}
