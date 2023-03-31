package com.example.dolphin.infrastructure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dolphin.R;
import com.example.dolphin.domain.model.Video;
import com.example.dolphin.infrastructure.holder.RecyclerItemHolder;

import java.util.List;

/**
 * 滑动播放视频（即viewPager2）的适配器
 *
 * @author 王景阳
 * @date 2022/10/27 16:28
 */
public class ViewPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = "ViewPagerAdapter";

    @SuppressLint("StaticFieldLeak")
    private RecyclerItemHolder recyclerItemHolder;

    private List<Video> videos = null;

    private Context context = null;

    public ViewPagerAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_viewpager2_item, parent, false);
        recyclerItemHolder = new RecyclerItemHolder(context, v);
        return recyclerItemHolder;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerItemHolder recyclerItemHolder = (RecyclerItemHolder) holder;
        recyclerItemHolder.onBind(position, videos.get(position));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initIcon(int position) {
        if (recyclerItemHolder != null) {
            recyclerItemHolder.initConcern(videos.get(position));
            recyclerItemHolder.initSupport(videos.get(position));
            recyclerItemHolder.initComment(videos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

}
