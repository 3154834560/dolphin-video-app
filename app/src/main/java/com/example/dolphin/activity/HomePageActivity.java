package com.example.dolphin.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.R;
import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.activity.fragment.RewardFragment;
import com.example.dolphin.activity.fragment.VideoListViewFragment;
import com.example.dolphin.application.service.CollectionService;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.adapter.FragmentPagerAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.FindTextListener;
import com.example.dolphin.infrastructure.listeners.HintLoginTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageViewListener;
import com.example.dolphin.infrastructure.listeners.JumpIconListener;
import com.example.dolphin.infrastructure.listeners.RewardTextListener;
import com.example.dolphin.infrastructure.listeners.SlideTextListener;
import com.example.dolphin.infrastructure.listeners.UploadVideoListener;
import com.example.dolphin.infrastructure.structs.LoginInfoJson;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.FileTool;
import com.example.dolphin.infrastructure.tool.PermissionTool;
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
        PermissionTool.checkPermission(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initGlobalVariable();
        initVideoList();
        initData();
    }

    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionTool.PERMISSION_REQUEST:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


    private void initData() {
        initTopData();
        initBottomData();
        initViewPager2();
    }

    private void initGlobalVariable() {
        StringPool.WORKING_PATH = getFilesDir().getAbsolutePath() + File.separator;
        StringPool.RESOURCE_PATH = "android.resource://" + getPackageName() + "/";
        StringPool.COM_EXAMPLE_DOLPHIN_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.example.dolphin";
        File file = new File(StringPool.COM_EXAMPLE_DOLPHIN_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
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
                CollectionService collectionService = new CollectionService();
                collectionService.getAllCollection(this);
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
        uploadImage.setOnClickListener(new UploadVideoListener(this, LoginPageActivity.class, UploadPageActivity.class));
    }

    private void initViewPager2() {
        List<TextView> topTexts = Arrays.asList(findViewById(R.id.reward), findViewById(R.id.concern), findViewById(R.id.find));
        List<Fragment> fragments = Arrays.asList(new RewardFragment(), new VideoListViewFragment(null, 0, StringPool.CONCERN, R.color.black), new FindFragment());
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                count = 1;
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
        GSYVideoManager.releaseAllVideos();
        GSYVideoManager.instance().clearAllDefaultCache(this);
/*        VideoTool.destroyPlay(FindFragment.getViewPager2());
        FindFragment.getPagerAdapter().notifyDataSetChanged();
        BaseTool.clearCache(this);*/
        userService.writeLoginInfo(this, StringPool.CURRENT_USER);
    }
}