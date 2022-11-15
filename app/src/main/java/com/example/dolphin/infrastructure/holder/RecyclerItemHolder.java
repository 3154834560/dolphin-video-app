package com.example.dolphin.infrastructure.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.domain.entity.Video;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Map;

/**
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
public class RecyclerItemHolder extends RecyclerView.ViewHolder {

    public final static String TAG = "RecyclerItemHolder";

    private final Context context;

    private final StandardGSYVideoPlayer gsyVideoPlayer;

    private final TextView author;

    private final TextView introduction;

    private final ImageView imageView;

    private final GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public RecyclerItemHolder(Context context, View v) {
        super(v);
        this.context = context;
        this.gsyVideoPlayer = v.findViewById(R.id.video_item_player);
        this.author = v.findViewById(R.id.video_author);
        this.introduction = v.findViewById(R.id.video_introduction);
        this.imageView = new ImageView(context);
        this.gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void onBind(int position, Video video) {

        String videoUrl = video.getUrl();
        String videoIntroduction = video.getIntroduction();

        Glide.with(context).load(video.getCoverUrl()).into(imageView);

        author.setText(video.getAuthor());
        introduction.setText(videoIntroduction);

        preventDislocation(videoUrl, videoIntroduction, null, position);

        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //全屏幕按键处理
                gsyVideoPlayer.startWindowFullscreen(context, true, true);
            }
        });
    }

    /**
     * 防止错位，离开释放
     */
    private void preventDislocation(String videoUrl, String textContent, Map<String, String> header, int position) {
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(videoUrl)
                .setVideoTitle(textContent)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setLooping(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setVideoAllCallBack(new GSYSampleCallBack() {
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
                }).build(gsyVideoPlayer);
    }

    public StandardGSYVideoPlayer getPlayer() {
        return gsyVideoPlayer;
    }

}