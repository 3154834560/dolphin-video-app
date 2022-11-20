package com.example.dolphin.infrastructure.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.ConcernIconListener;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Arrays;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
public class RecyclerItemHolder extends RecyclerView.ViewHolder {

    public final static String TAG = "RecyclerItemHolder";

    private final Context context;

    private final StandardGSYVideoPlayer gsyVideoPlayer;

    private final ImageView imageView;

    private final View v;

    private final GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public RecyclerItemHolder(Context context, View v) {
        super(v);
        this.context = context;
        this.v = v;
        this.gsyVideoPlayer = v.findViewById(R.id.video_item_player);
        this.imageView = new ImageView(context);
        this.gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void onBind(int position, Video video) {

        initData(video);

        preventDislocation(video.getUrl(), video.getIntroduction(), null, position);

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


    private void initData(Video video) {
        TextView author = v.findViewById(R.id.video_author);
        TextView introduction = v.findViewById(R.id.video_introduction);
        CircleImageView headPortrait = v.findViewById(R.id.video_author_head_portrait);
        UserService userService = new UserService();
        User user = userService.getBy(context, video.getAuthor());
        BaseTool.setTextTypeFace(Arrays.asList(author, introduction), context.getAssets());
        author.setText(video.getAuthor());
        introduction.setText(video.getIntroduction());
        Glide.with(context).load(video.getCoverUrl()).into(imageView);
        Glide.with(context).load(user.getHeadPortraitUrl()).into(headPortrait);

        initConcern(video);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initConcern(Video video) {
        ImageView concernIcon = v.findViewById(R.id.concern_image);
        if (StringPool.CURRENT_USER == null || StringPool.CONCERN == null || StringPool.CONCERN.size() == 0) {
            concernIcon.setBackground(context.getDrawable(R.drawable.icon_add));
        } else {
            concernIcon.setBackground(context.getDrawable(R.drawable.icon_concerned));
        }
        concernIcon.setOnClickListener(new ConcernIconListener(v.getContext(), video.getAuthor(), concernIcon));
    }

    /**
     * 防止错位，离开释放
     */
    private void preventDislocation(String videoUrl, String textContent, Map<String, String> header, int position) {
        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setThumbPlay(true)
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