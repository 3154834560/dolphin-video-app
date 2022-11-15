package com.example.dolphin.activity;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dolphin.R;
import com.example.dolphin.api.UserApi;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.activity.fragment.RewardFragment;
import com.example.dolphin.activity.fragment.ConcernFragment;
import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.HintLoginTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageTextListener;
import com.example.dolphin.infrastructure.listeners.MeTextListener;
import com.example.dolphin.infrastructure.listeners.UploadIconListener;
import com.example.dolphin.infrastructure.structs.LoginInfoJson;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.adapter.FragmentPagerAdapter;
import com.example.dolphin.infrastructure.adapter.ViewPagerAdapter;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;
import com.example.dolphin.infrastructure.listeners.ConcernTextListener;
import com.example.dolphin.infrastructure.listeners.FindTextListener;
import com.example.dolphin.infrastructure.listeners.HomeViewPageListener;
import com.example.dolphin.infrastructure.listeners.RewardTextListener;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.FileTool;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
@SuppressLint("NonConstantResourceId")
public class HomePageActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private TextView hintLogin;

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
        StringPool.ALBUM_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        StringPool.LOGIN_INFO_FILE_PATH = StringPool.WORKING_PATH + StringPool.LOGIN_INFO_FILE_NAME;
        File loginFile = new File(StringPool.LOGIN_INFO_FILE_PATH);
        if (loginFile.exists()) {
            LoginInfoJson loginUserInfo = UserService.getLoginUserInfo(this);
            if (loginUserInfo.getCurrentUser() != null) {
                User user = UserService.getBy(this, loginUserInfo.getCurrentUser().getLoggedUser().getUserName());
                StringPool.INDEX = loginUserInfo.getCurrentUser().getIndex();
                UserService.writeLoginInfo(this, user);
                if (user == null) {
                    StringPool.INDEX = 0;
                }
                StringPool.CURRENT_USER = user;
            }
        } else {
            FileTool.createFile(StringPool.LOGIN_INFO_FILE_NAME);
        }

    }

    private void initVideoList() {
        List<Video> videos = VideoService.randomGet(this, 0);
        if (videos.size() > 0) {
            StringPool.videos.addAll(videos);
        }
        //HomePageActivity.initVideos(StringPool.videos);
    }

    private void initTopData() {
        ViewPager2 viewPager2 = findViewById(R.id.home_view_page2);
        hintLogin = findViewById(R.id.hint_login);
        hintLogin.setVisibility(StringPool.CURRENT_USER == null ? View.VISIBLE : View.INVISIBLE);
        List<TextView> topTexts = Arrays.asList(findViewById(R.id.reward), findViewById(R.id.concern)
                , findViewById(R.id.find), hintLogin);
        List<View.OnClickListener> topListeners = Arrays.asList(new RewardTextListener(viewPager2, 0)
                , new ConcernTextListener(viewPager2, 1)
                , new FindTextListener(viewPager2, 2)
                , new HintLoginTextListener(this, LoginPageActivity.class));

        BaseTool.addOnClickListener(topTexts, topListeners);
        BaseTool.setTextTypeFace(topTexts, getAssets());
    }

    private void initBottomData() {
        TextView homePageText = findViewById(R.id.home_page);
        ImageView uploadImage = findViewById(R.id.upload_video_icon);
        TextView meText = findViewById(R.id.me);
        BaseTool.setTextTypeFace(homePageText, getAssets());
        BaseTool.setTextTypeFace(meText, getAssets());
        homePageText.setOnClickListener(new HomePageTextListener(getResources(), homePageText, meText));
        meText.setOnClickListener(new MeTextListener(getResources(), homePageText, meText));

        uploadImage.setOnClickListener(new UploadIconListener(this, LoginPageActivity.class, UploadPageActivity.class));
    }

    private void initViewPager2() {
        List<TextView> topTexts = Arrays.asList(findViewById(R.id.reward), findViewById(R.id.concern), findViewById(R.id.find));
        List<Fragment> fragments = Arrays.asList(new RewardFragment(), new ConcernFragment(), new FindFragment());
        ViewPager2 viewPager2 = findViewById(R.id.home_view_page2);

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(this, fragments);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setCurrentItem(topTexts.size() - 1, false);
        viewPager2.registerOnPageChangeCallback(new HomeViewPageListener(getResources(), topTexts));
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
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        GSYVideoManager.instance().clearAllDefaultCache( this);
        VideoTool.destroyPlay(FindFragment.getViewPager2());
        GSYVideoManager.releaseAllVideos();
        FindFragment.getPagerAdapter().notifyDataSetChanged();
        BaseTool.clearCache(this);
        UserService.writeLoginInfo(this, StringPool.CURRENT_USER);
    }

    private void initButton() {
/*        Button button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
                infoList.clear();
                initInfoList(infoList);
                RecyclerView.Adapter adapter = viewPager2.getAdapter();
                adapter.notifyDataSetChanged();
                viewPager2.setCurrentItem(0);
                viewPager2.post(() -> playPosition(viewPager2, 0));
            }
        });*/

    }

/*    private void initViewPager() {
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        initInfoList(infoList);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, infoList);
        addPagerAdapter(viewPager2, pagerAdapter);
    }*/

    private void addPagerAdapter(ViewPager2 viewPager2, ViewPagerAdapter pagerAdapter) {
        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 大于0说明有播放
                int playPosition = GSYVideoManager.instance().getPlayPosition();
                if (playPosition >= 0) {
                    // 对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(RecyclerItemHolder.TAG)
                            && (position != playPosition)) {
                        playPosition(viewPager2, position);
                    }
                }
            }
        });
        //进入首页时自动播放第一个视频
        viewPager2.post(() -> playPosition(viewPager2, 0));
    }

    /**
     * 播放视频
     */
    private void playPosition(ViewPager2 viewPager2, int position) {
        int childCount = viewPager2.getChildCount();
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            RecyclerItemHolder recyclerItemNormalHolder = (RecyclerItemHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().startPlayLogic();
        }
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


    private void testHttp() {
        UserApi service = RetrofitUtils.getInstance().getRetrofit().create(UserApi.class);
        Call<Result<Object>> call = service.test("aa");
        //  同步调用
        // 不能在主线程上发送http请求
        try {
            Thread s = new Thread(() -> {
                try {
                    Response<Result<Object>> response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            s.start();
            s.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void testSave() {
        try {
            File dir = getFilesDir();
            File data = getDataDir();
            File cache = getCacheDir();
            System.out.println("dir: ");
            output(dir);
            System.out.println("data: ");
            output(data);
            System.out.println("cache: ");
            output(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void output(File file) {
        System.out.println(file.getAbsolutePath() + "------>" + Arrays.toString(file.list()));
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                output(f);
            }
        }
    }
}