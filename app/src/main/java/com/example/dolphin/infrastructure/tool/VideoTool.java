package com.example.dolphin.infrastructure.tool;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;
import com.example.dolphin.infrastructure.util.RealPathFromUriUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    public static void getVideo(Context context, ImageView video, Uri uri) {
        String filePath = RealPathFromUriUtil.getFilePathByUri(context, uri);
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        try {
            @SuppressLint({"NewApi", "LocalSuppress"}) String s = Files.probeContentType(file.toPath());
            if (s.startsWith("image")){
                BaseTool.shortToast(context,"请上传视频！");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        video.setImageBitmap(FileTool.getBitmapFromUri(context, uri));
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        StringPool.VIDEO = MultipartBody.Part.createFormData("video", file.getName(), requestFile);
    }

    public static void getImage(Context context, ImageView video, Uri uri, String paramName) {
        video.setImageBitmap(FileTool.getBitmapFromUri(context, uri));
        String filePath = RealPathFromUriUtil.getFilePathByUri(context, uri);
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        StringPool.IMAGE = MultipartBody.Part.createFormData(paramName, file.getName(), requestFile);
    }

}
