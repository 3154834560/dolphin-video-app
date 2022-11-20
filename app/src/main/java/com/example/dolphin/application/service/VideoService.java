package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.api.VideoApi;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/12 18:49
 */
public class VideoService {

    private final UserService userService = new UserService();

    private final VideoApi VIDEO_API = RetrofitUtils.getInstance().getRetrofit().create(VideoApi.class);

    public Boolean uploadVideo(Context context, String introduction) {
        if (StringPool.VIDEO == null) {
            return false;
        }
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = VIDEO_API.uploadVideo(StringPool.CURRENT_USER.getUserName(), introduction, StringPool.VIDEO, StringPool.IMAGE);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getCode().equals(StringPool.SUCCESS);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        StringPool.VIDEO = null;
        StringPool.IMAGE = null;
        return isSuccess;
    }

    public List<Video> randomGet(Context context, int n) {
        List<Video> videos = new ArrayList<>();
        if (n == StringPool.THREE) {
            BaseTool.shortToast(context, StringPool.NOT_VIDEOS);
            return videos;
        }
        try {
            Call<Result<List<Video>>> call = VIDEO_API.randomGet(StringPool.INDEX);
            Result<List<Video>> result = ApiTool.sendRequest(call);
            List<Video> data = result.getData();
            if (data.size() == 0) {
                StringPool.INDEX = 0;
                return randomGet(context, n + 1);
            }
            videos.addAll(data);
            StringPool.INDEX++;
            userService.writeLoginInfo(context, StringPool.CURRENT_USER);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return videos;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addVideo(Context context) {
        List<Video> videos = randomGet(context, 0);
        if (videos.size() > 0) {
            StringPool.videos.addAll(videos);
            FindFragment.getPagerAdapter().notifyDataSetChanged();
            FindFragment.getViewPager2().post(() -> VideoTool.startPlay(FindFragment.getViewPager2()));
        }
/*        HomePageActivity.initVideos(StringPool.videos);
        ViewPagerAdapter pagerAdapter = FindFragment.getPagerAdapter();
        FindFragment.getViewPager2().post(() -> VideoTool.startPlay(FindFragment.getViewPager2()));*/
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateVideo(Context context) {
        int upVideosNum = StringPool.videos.size();
        StringPool.videos.clear();
        List<Video> videos = randomGet(context, 0);
        if (upVideosNum == 0 && videos.size() == 0) {
            return;
        }
        StringPool.videos.addAll(videos);
        FindFragment.getPagerAdapter().notifyDataSetChanged();
        if (StringPool.videos.size() > 0) {
            FindFragment.getViewPager2().setCurrentItem(0, false);
            FindFragment.getViewPager2().post(() -> VideoTool.startPlay(FindFragment.getViewPager2()));
        }
    }

}
