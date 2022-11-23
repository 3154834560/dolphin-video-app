package com.example.dolphin.infrastructure.structs;

import android.content.Context;

import com.example.dolphin.R;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.Video;

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

    private String coverUrl;

    private int supportResource;

    private String numbers;

    private List<Integer> childLayoutIds;

    public static VideoListView copy(Context context, Video video, VideoService videoService) {
        Boolean support = videoService.isSupport(context, video.getId());
        return new VideoListView()
                .setCoverUrl(video.getCoverUrl())
                .setSupportResource(support ? R.drawable.icon_support : R.drawable.icon_un_support2)
                .setNumbers(video.getNumbers()+"");
    }
}
