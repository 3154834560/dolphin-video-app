package com.example.dolphin.infrastructure.tool;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;
import com.example.dolphin.infrastructure.util.RealPathFromUriUtil;

import java.io.File;

/**
 * @author 王景阳
 * @date 2022/11/12 21:57
 */
public class VideoTool {

    /**
     * 播放视频
     */
    public static void startPlay(ViewPager2 viewPager2) {
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(viewPager2.getCurrentItem());
        if (viewHolder != null) {
            RecyclerItemHolder recyclerItemNormalHolder = (RecyclerItemHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().startPlayLogic();
        }
    }

    public static void startPlay(ViewPager2 viewPager2, int playPosition) {
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(playPosition);
        if (viewHolder != null) {
            RecyclerItemHolder recyclerItemNormalHolder = (RecyclerItemHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().startPlayLogic();
        }
    }

    public static void stopPlay(ViewPager2 viewPager2) {
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(viewPager2.getCurrentItem());
        if (viewHolder != null) {
            RecyclerItemHolder recyclerItemNormalHolder = (RecyclerItemHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().onVideoPause();
        }
    }

    public static void destroyPlay(ViewPager2 viewPager2) {
        RecyclerView.ViewHolder viewHolder = ((RecyclerView) viewPager2.getChildAt(0)).findViewHolderForAdapterPosition(viewPager2.getCurrentItem());
        if (viewHolder != null) {
            RecyclerItemHolder recyclerItemNormalHolder = (RecyclerItemHolder) viewHolder;
            recyclerItemNormalHolder.getPlayer().clearCurrentCache();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public static void getVideo(Context context, VideoView video, Uri uri) {
        String filePath = RealPathFromUriUtil.getFilePathByUri(context, uri);
        if (filePath == null) {
            return;
        }
        video.setVideoURI(uri);
        video.start();
        File file = new File(filePath);
        if (file.length() > StringPool.MAX_VIDEO_SIZE) {
            BaseTool.shortToast(context, StringPool.VIDEO_TOO_LARGE);
            return;
        }
        StringPool.VIDEO = new File(filePath);
    }

    public static void getImage(Context context, ImageView video, Uri uri) {
        video.setImageBitmap(FileTool.getBitmapFromUri(context, uri));
        String filePath = RealPathFromUriUtil.getFilePathByUri(context, uri);
        if (filePath == null) {
            return;
        }
        StringPool.COVER = new File(filePath);
    }

//---------------------------------------------------------------------------------------------------
//    大文件上传

}
