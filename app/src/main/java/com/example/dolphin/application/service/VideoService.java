package com.example.dolphin.application.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.dolphin.activity.fragment.FindFragment;
import com.example.dolphin.api.VideoApi;
import com.example.dolphin.application.dto.output.VideoOutput;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.UploadVideoListener;
import com.example.dolphin.infrastructure.rest.Result;
import com.example.dolphin.infrastructure.tool.ApiTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.VideoTool;
import com.example.dolphin.infrastructure.util.RetrofitUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author 王景阳
 * @date 2022/11/12 18:49
 */
public class VideoService {

    private final UserService userService = new UserService();

    private final VideoApi VIDEO_API = RetrofitUtils.create(VideoApi.class);


    public List<Video> getAll(Context context, String userName) {
        List<Video> videos = new ArrayList<>();
        try {
            Call<Result<List<Video>>> call = VIDEO_API.getAll(userName);
            Result<List<Video>> result = ApiTool.sendRequest(call);
            List<Video> data = result.getData();
            videos.addAll(data);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return videos;
    }

    public Video getBy(Context context, String videoId) {
        Video video = null;
        try {
            Call<Result<Video>> call = VIDEO_API.getBy(videoId);
            Result<Video> result = ApiTool.sendRequest(call);
            video = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return video;
    }

    public Boolean supportVideo(Context context, Video video) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = VIDEO_API.supportVideo(StringPool.CURRENT_USER.getUserName(), video.getId(), StringPool.ONE);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                video.setNumbers(video.getNumbers() + 1);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }

    public Boolean unSupportVideo(Context context, Video video) {
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = VIDEO_API.supportVideo(StringPool.CURRENT_USER.getUserName(), video.getId(), StringPool.NEGATIVE_ONE);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
            if (isSuccess) {
                video.setNumbers(video.getNumbers() - 1);
            }
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }

    public Boolean isSupport(Context context, String videoId) {
        if (StringPool.CURRENT_USER == null) {
            return false;
        }
        boolean isSuccess = false;
        try {
            Call<Result<Boolean>> call = VIDEO_API.isSupport(StringPool.CURRENT_USER.getUserName(), videoId);
            Result<Boolean> result = ApiTool.sendRequest(call);
            isSuccess = result.getData();
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.NOT_NETWORK);
        }
        return isSuccess;
    }

    public void uploadVideo(Context context, String introduction) {
        File video = StringPool.VIDEO;
        File cover = StringPool.COVER;
        String videoName = System.currentTimeMillis() + getSuffix(video);
        String coverName = cover == null ? null : System.currentTimeMillis() + getSuffix(cover);
        VideoOutput videoOutput = new VideoOutput(StringPool.CURRENT_USER.getUserName(), introduction, videoName, coverName);
        int length = 3 * 1024 * 1024;
        byte[] bytes = new byte[length];
        int len = 0;
        boolean first = true;
        try {
            MultipartBody.Part coverPart = null;
            if (cover != null) {
                RequestBody coverBody = RequestBody.create(cover, MediaType.parse("multipart/form-data"));
                coverPart = MultipartBody.Part.createFormData("cover", cover.getName(), coverBody);
            }
            FileInputStream videoInputStream = new FileInputStream(video);
            while ((len = videoInputStream.read(bytes)) > 0) {
                RequestBody videoBody = RequestBody.create(Arrays.copyOfRange(bytes, 0, len), MediaType.parse("multipart/form-data"));
                MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", video.getName(), videoBody);
                videoOutput.setEnd(len != length);
                Call<Result<Boolean>> resultCall = VIDEO_API.uploadVideo(JSON.toJSONString(videoOutput), videoPart, first ? coverPart : null);
                first = false;
                Result<Boolean> result = ApiTool.sendRequest(resultCall);
                if (!result.getData()) {
                    throw new Exception(StringPool.UPLOAD_FAIL);
                }
            }
            BaseTool.shortToast(context, StringPool.UPLOAD_SUCCESS);
        } catch (Exception e) {
            BaseTool.shortToast(context, StringPool.UPLOAD_FAIL);
        }
        StringPool.VIDEO = null;
        StringPool.COVER = null;
        UploadVideoListener.UPLOAD_STATUS.compareAndSet(true, false);
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


    private String getSuffix(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf(StringPool.DOT));
    }

}
