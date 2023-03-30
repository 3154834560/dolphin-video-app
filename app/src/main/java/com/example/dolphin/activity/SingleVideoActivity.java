package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.application.service.CollectionService;
import com.example.dolphin.application.service.CommentService;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.DownloadService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.model.User;
import com.example.dolphin.domain.model.Video;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.CollectionListener;
import com.example.dolphin.infrastructure.listeners.CommentListener;
import com.example.dolphin.infrastructure.listeners.ConcernIconListener;
import com.example.dolphin.infrastructure.listeners.SupportListener;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2022/11/26 16:19
 */
public class SingleVideoActivity extends AppCompatActivity {

    private final VideoService videoService = new VideoService();

    private final UserService userService = new UserService();

    private StandardGSYVideoPlayer gsyVideoPlayer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewpager2_item);
        Intent intent = getIntent();
        String videoId = intent.getStringExtra(StringPool.VIDEO_ID);
        Video video = videoService.getBy(this, videoId);
        User user = userService.getBy(this, video.getAuthor());
        initData(video, user);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData(Video video, User user) {
        initVideoPlayer(video);
        initHeadPortrait(video, user);
        initSupport(video);
        initComment(video);
        initCollection(video);
        initIntroduction(video);
        initDown(video);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gsyVideoPlayer != null) {
            gsyVideoPlayer.onVideoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gsyVideoPlayer != null) {
            gsyVideoPlayer.onVideoResume();
        }
    }

    private void initVideoPlayer(Video video) {
        gsyVideoPlayer = findViewById(R.id.video_item_player);
        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(v -> {
            //全屏幕按键处理
            gsyVideoPlayer.startWindowFullscreen(SingleVideoActivity.this, true, true);
        });
        preventDislocation(video, gsyVideoPlayer);
        gsyVideoPlayer.startPlayLogic();
    }

    /**
     * 防止错位，离开释放
     */
    private void preventDislocation(Video video, StandardGSYVideoPlayer gsyVideoPlayer) {
        ImageView coverImage = new ImageView(this);
        Glide.with(this).load(BaseTool.toStaticImagesUrl(video.getCoverName())).into(coverImage);
        new GSYVideoOptionBuilder()
                .setIsTouchWiget(false)
                .setThumbImageView(coverImage)
                .setThumbPlay(true)
                .setUrl(BaseTool.toStaticVideosUrl(video.getVideoName()))
                .setVideoTitle(video.getIntroduction())
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag("singleVideo")
                .setPlayPosition(0)
                .setShowFullAnimation(true)
                .setLooping(true)
                .setNeedLockFull(true)
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initHeadPortrait(Video video, User user) {
        ConcernService concernService = new ConcernService();
        CircleImageView headPortrait = findViewById(R.id.video_author_head_portrait);
        headPortrait.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(SingleVideoActivity.this, AuthorInfoActivity.class);
            intent.putExtra(StringPool.AUTHOR_ID, user.getUserName());
            startActivity(intent);
        });
        Glide.with(this).load(BaseTool.toStaticImagesUrl(user.getHeadPortraitName())).into(headPortrait);
        ImageView concernIcon = findViewById(R.id.concern_image);
        if (concernService.isConcern(video.getAuthor())) {
            concernIcon.setBackground(getDrawable(R.drawable.icon_concerned));
        } else {
            concernIcon.setBackground(getDrawable(R.drawable.icon_add));
        }
        concernIcon.setOnClickListener(new ConcernIconListener(this, video.getAuthor(), concernIcon));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initSupport(Video video) {
        ImageView supportIcon = findViewById(R.id.support_image);
        TextView supportNumber = findViewById(R.id.support_number);
        Boolean isSupport = videoService.isSupport(this, video.getId());
        if (isSupport) {
            supportIcon.setBackground(getDrawable(R.drawable.icon_support));
        } else {
            supportIcon.setBackground(getDrawable(R.drawable.icon_un_support1));
        }
        @SuppressLint("DefaultLocale") String number = String.format("%.2f万", video.getNumbers() / 10000.0);
        supportNumber.setText(video.getNumbers() >= 10000 ? number : video.getNumbers() + "");
        supportIcon.setOnClickListener(new SupportListener(this, video, supportIcon, supportNumber));
    }

    private void initComment(Video video) {
        ImageView commentIcon = findViewById(R.id.comment_icon);
        TextView commentNumber = findViewById(R.id.comment_number);
        CommentService service = new CommentService();
        service.updateCommentBy(this, video.getId());
        commentIcon.setOnClickListener(new CommentListener(this, R.layout.comment_list, video.getId()));
        Integer commentCount = service.getCommentCount(this, video.getId());
        commentNumber.setText(BaseTool.numberToString(commentCount));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initCollection(Video video) {
        ImageView collectionIcon = findViewById(R.id.collection_icon);
        CollectionService collectionService = new CollectionService();
        Boolean isCollection = collectionService.isCollection(video.getId());
        if (isCollection) {
            collectionIcon.setBackground(getDrawable(R.drawable.collection_icon));
        } else {
            collectionIcon.setBackground(getDrawable(R.drawable.un_collection_icon));
        }
        collectionIcon.setOnClickListener(new CollectionListener(this, video.getId(), collectionIcon));
    }

    public void initDown(Video video) {
        ImageView downIcon = findViewById(R.id.down_icon);
        downIcon.setOnClickListener(v -> {
            if (DownloadService.DOWN_STATUS.get()) {
                BaseTool.shortToast(v.getContext(), "已有视频在下载！");
                return;
            }
            DownloadService downloadService = new DownloadService();
            downloadService.downloadFile(SingleVideoActivity.this, StringPool.VIDEOS, video.getVideoName());
        });
    }

    private void initIntroduction(Video video) {
        TextView author = findViewById(R.id.video_author);
        TextView introduction = findViewById(R.id.video_introduction);
        BaseTool.setTextTypeFace(Arrays.asList(author, introduction), getAssets());
        author.setText(video.getAuthor());
        introduction.setText(video.getIntroduction());
    }
}
