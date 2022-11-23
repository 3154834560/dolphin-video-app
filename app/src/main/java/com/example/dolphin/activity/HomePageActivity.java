package com.example.dolphin.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.activity.fragment.RewardFragment;
import com.example.dolphin.activity.fragment.VideoListViewFragment;
import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.HintLoginTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageTextListener;
import com.example.dolphin.infrastructure.listeners.JumpIconListener;
import com.example.dolphin.infrastructure.structs.LoginInfoJson;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.adapter.FragmentPagerAdapter;
import com.example.dolphin.infrastructure.listeners.SlideTextListener;
import com.example.dolphin.infrastructure.listeners.FindTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageViewListener;
import com.example.dolphin.infrastructure.listeners.RewardTextListener;
import com.example.dolphin.infrastructure.tool.FileTool;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
@SuppressLint("NonConstantResourceId")
public class HomePageActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private TextView hintLogin;

    private final VideoService videoService = new VideoService();

    private final UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initGlobalVariable();
        initVideoList();
        initCurrentData();
    }

    private void initCurrentData() {
        initTopData();
        initBottomData();
        initViewPager2();
    }

    private void initGlobalVariable() {
        StringPool.WORKING_PATH = getFilesDir().getAbsolutePath() + File.separator;
        StringPool.RESOURCE_PATH = "android.resource://" + getPackageName() + "/";
        StringPool.ALBUM_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        StringPool.LOGIN_INFO_FILE_PATH = StringPool.WORKING_PATH + StringPool.LOGIN_INFO_FILE_NAME;
        File loginFile = new File(StringPool.LOGIN_INFO_FILE_PATH);
        if (loginFile.exists()) {
            LoginInfoJson loginUserInfo = userService.getLoginUserInfo(this);
            if (loginUserInfo.getCurrentUser() != null) {
                User user = userService.getBy(this, loginUserInfo.getCurrentUser().getLoggedUser().getUserName());
                StringPool.INDEX = loginUserInfo.getCurrentUser().getIndex();
                userService.writeLoginInfo(this, user);
                if (user == null) {
                    StringPool.INDEX = 0;
                }
                StringPool.CURRENT_USER = user;
                ConcernService concernService = new ConcernService();
                concernService.getAllConcern(this);
            }
        } else {
            FileTool.createFile(StringPool.LOGIN_INFO_FILE_NAME);
        }

    }

    private void initVideoList() {
        List<Video> videos = videoService.randomGet(this, 0);
        if (videos.size() > 0) {
            StringPool.videos.addAll(videos);
        }
        //HomePageActivity.initVideos(StringPool.videos);
    }

    private void initTopData() {
        ImageView more = findViewById(R.id.more);
        ViewPager2 viewPager2 = findViewById(R.id.home_view_page2);
        hintLogin = findViewById(R.id.hint_login);
        hintLogin.setVisibility(StringPool.CURRENT_USER == null ? View.VISIBLE : View.INVISIBLE);
        List<TextView> topTexts = Arrays.asList(findViewById(R.id.reward), findViewById(R.id.concern)
                , findViewById(R.id.find), hintLogin);
        List<View.OnClickListener> topListeners = Arrays.asList(new RewardTextListener(viewPager2, 0)
                , new SlideTextListener(viewPager2, 1)
                , new FindTextListener(viewPager2, 2)
                , new HintLoginTextListener(this, LoginPageActivity.class));

        BaseTool.addOnClickListener(topTexts, topListeners);
        more.setOnClickListener(v -> startActivity(new Intent(HomePageActivity.this, MorePageActivity.class)));
        BaseTool.setTextTypeFace(topTexts, getAssets());
    }

    private void initBottomData() {
        TextView homePageText = findViewById(R.id.home_page);
        ImageView uploadImage = findViewById(R.id.upload_video_icon);
        TextView meText = findViewById(R.id.me);
        BaseTool.setTextTypeFace(homePageText, getAssets());
        BaseTool.setTextTypeFace(meText, getAssets());
        homePageText.setOnClickListener(new HomePageTextListener(this, HomePageActivity.class, HomePageActivity.class, findViewById(R.id.home_view_page2)));
        meText.setOnClickListener(new JumpIconListener(this, LoginPageActivity.class, MePageActivity.class));
        uploadImage.setOnClickListener(new JumpIconListener(this, LoginPageActivity.class, UploadPageActivity.class));
    }

    private void initViewPager2() {
        List<TextView> topTexts = Arrays.asList(findViewById(R.id.reward), findViewById(R.id.concern), findViewById(R.id.find));
        List<Fragment> fragments = Arrays.asList(new RewardFragment(), new VideoListViewFragment(50, StringPool.CONCERN, R.color.black), new FindFragment());
        ViewPager2 viewPager2 = findViewById(R.id.home_view_page2);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(this, fragments);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(topTexts.size() - 1, false);
        viewPager2.registerOnPageChangeCallback(new HomePageViewListener(getResources(), topTexts, R.drawable.underline1));
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoTool.stopPlay(FindFragment.getViewPager2());
    }

    @Override
    protected void onResume() {
        super.onResume();
        hintLogin.setVisibility(StringPool.CURRENT_USER == null ? View.VISIBLE : View.INVISIBLE);
        if (FindFragment.getViewPager2() != null && FindFragment.getPagerAdapter() != null) {
            FindFragment.getPagerAdapter().initIcon(FindFragment.getViewPager2().getCurrentItem());
        }
    }

    private int count = 0;

    private long upDownTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        count++;
        if (count == 1) {
            BaseTool.shortToast(this, "再按一次返回键退出 ");
            upDownTime = System.currentTimeMillis();
        } else {
            count = 0;
            if (System.currentTimeMillis() - upDownTime <= 500) {
                return super.onKeyDown(keyCode, event);
            } else {
                BaseTool.shortToast(this, "再按一次返回键退出 ");
                upDownTime = System.currentTimeMillis();
            }
        }
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        GSYVideoManager.instance().clearAllDefaultCache( this);
        StringPool.IS_END = false;
        VideoTool.destroyPlay(FindFragment.getViewPager2());
        GSYVideoManager.releaseAllVideos();
        FindFragment.getPagerAdapter().notifyDataSetChanged();
        BaseTool.clearCache(this);
        userService.writeLoginInfo(this, StringPool.CURRENT_USER);
    }

    /**
     * 模拟数据
     */
    public static void initVideos(List<Video> videos) {

        Video data1 = new Video();
        data1.setAuthor("@_彼岸雨敲窗_");
        data1.setIntroduction("蜂鸟计划 中国预告片：速度与金钱版 (中文字幕)");
        data1.setCoverUrl("https://img3.doubanio.com/img/trailer/medium/2631410731.jpg?1611566097");
        data1.setUrl("https://vt1.doubanio.com/202102020903/722442386dcd5076fd70c4ac2bf093bb/view/movie/M/402710160.mp4");
        videos.add(data1);

        Video data2 = new Video();
        data2.setAuthor("@_彼岸雨敲窗_");
        data2.setIntroduction("旺达幻视 预告片");
        data2.setCoverUrl("https://img1.doubanio.com/img/trailer/medium/2628042057.jpg?");
        data2.setUrl("https://vt1.doubanio.com/202102011621/94e560ba4d88c562e0768f6339822d99/view/movie/M/402690624.mp4");
        videos.add(data2);

        Video data3 = new Video();
        data3.setAuthor("@_彼岸雨敲窗_");
        data3.setIntroduction("无耻之徒(美版) 第十一季 预告片");
        data3.setCoverUrl("https://img1.doubanio.com/img/trailer/medium/2626877508.jpg?");
        data3.setUrl("https://vt1.doubanio.com/202101120940/a3e7ae32c21341710eaceba2d2e56039/view/movie/M/402680931.mp4");
        videos.add(data3);

        Video data4 = new Video();
        data4.setAuthor("@_彼岸雨敲窗_");
        data4.setIntroduction("发现女巫 第二季 预告片");
        data4.setCoverUrl("https://img9.doubanio.com/img/trailer/medium/2628112124.jpg?");
        data4.setUrl("https://vt1.doubanio.com/202101120938/d05ce0af6cefa6b88dd699e1f8150f2f/view/movie/M/402690672.mp4");
        videos.add(data4);

        Video data5 = new Video();
        data5.setAuthor("@_彼岸雨敲窗_");
        data5.setIntroduction("天国与地狱 预告片");
        data5.setCoverUrl("https://img2.doubanio.com/img/trailer/medium/2628313153.jpg?");
        data5.setUrl("https://vt1.doubanio.com/202102051113/07846ae6e7dd67089ff46a4d070b5f5d/view/movie/M/402690752.mp4");
        videos.add(data5);

    }
}