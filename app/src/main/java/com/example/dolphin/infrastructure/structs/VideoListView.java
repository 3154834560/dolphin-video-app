package com.example.dolphin.infrastructure.structs;

import android.content.Context;

import com.example.dolphin.R;
import com.example.dolphin.application.dto.input.CollectionInput;
import com.example.dolphin.application.dto.input.VideoInput;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2022/11/23 19:41
 */
@EqualsAndHashCode
@Data
@Accessors(chain = true)
public class VideoListView {

    private String videoId;

    private String coverUrl;

    private int supportResource;

    private String numbers;

    private List<Integer> childLayoutIds;

    public static VideoListView copy(Context context, VideoInput video, VideoService videoService) {
        Boolean support = videoService.isSupport(context, video.getId());
        VideoListView videoListView = new VideoListView();
        videoListView.setVideoId(video.getId());
        videoListView.setCoverUrl(BaseTool.toStaticImagesUrl(video.getCoverName()));
        videoListView.setSupportResource(support ? R.drawable.icon_support : R.drawable.icon_un_support2);
        videoListView.setNumbers(BaseTool.numberToString(video.getNumbers()));
        return videoListView;
    }

    public static VideoListView copy(Context context, CollectionInput input, VideoService videoService) {
        Boolean support = videoService.isSupport(context, input.getVideoId());
        VideoListView videoListView = new VideoListView();
        videoListView.setVideoId(input.getVideoId());
        videoListView.setCoverUrl(BaseTool.toStaticImagesUrl(input.getCoverName()));
        videoListView.setSupportResource(support ? R.drawable.icon_support : R.drawable.icon_un_support2);
        videoListView.setNumbers(BaseTool.numberToString(input.getNumbers()));
        return videoListView;
    }
}
