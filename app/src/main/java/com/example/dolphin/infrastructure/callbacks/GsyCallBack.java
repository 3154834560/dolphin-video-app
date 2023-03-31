package com.example.dolphin.infrastructure.callbacks;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author 王景阳
 * @date 2023/3/31 9:23
 */
public class GsyCallBack extends GSYSampleCallBack {
    private final StandardGSYVideoPlayer gsyVideoPlayer;

    public GsyCallBack(StandardGSYVideoPlayer gsyVideoPlayer) {
        this.gsyVideoPlayer = gsyVideoPlayer;
    }

    @Override
    public void onPrepared(String url, Object... objects) {
        super.onPrepared(url, objects);
        GSYVideoManager.instance().setNeedMute(false);
    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        super.onQuitFullscreen(url, objects);
        GSYVideoManager.instance().setNeedMute(false);
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        GSYVideoManager.instance().setNeedMute(false);
        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
    }
}
