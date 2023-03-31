package com.example.dolphin.infrastructure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dolphin.activity.SingleVideoActivity;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.VideoListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.List;
import java.util.Map;

/**
 * 视频ListView的适配器
 *
 * @author 王景阳
 * @date 2022/11/23 19:32
 */
public class VideoListViewAdapter extends SimpleAdapter {

    private final LayoutInflater mInflater;

    private final List<Map<String, VideoListView>> mData;

    private final int mResource;

    private final String[] mFrom;

    private final int[] mTo;

    public VideoListViewAdapter(Context context, List<Map<String, VideoListView>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mData = data;
        this.mResource = resource;
        this.mFrom = from;
        this.mTo = to;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView,
                                        ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = inflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }

        bindView(position, v);

        return v;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void bindView(int position, View view) {
        Map<String, VideoListView> dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        int count = mTo.length;
        for (int i = 0; i < count; i++) {
            View v = view.findViewById(mTo[i]);
            VideoListView videoListView = dataSet.get(mFrom[i]);
            v.setOnClickListener(v1 -> {
                Intent intent = new Intent(view.getContext(), SingleVideoActivity.class);
                intent.putExtra(StringPool.VIDEO_ID, videoListView.getVideoId());
                view.getContext().startActivity(intent);
            });
            initChildLayout(videoListView, v, view);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initChildLayout(VideoListView videoListView, View v, View view) {
        List<Integer> childLayoutIds = videoListView.getChildLayoutIds();
        ImageView cover = v.findViewById(childLayoutIds.get(0));
        ImageView support = v.findViewById(childLayoutIds.get(1));
        TextView number = v.findViewById(childLayoutIds.get(2));
        if (videoListView.getCoverUrl() == null) {
            cover.setVisibility(View.INVISIBLE);
            support.setVisibility(View.INVISIBLE);
            number.setVisibility(View.INVISIBLE);
            return;
        }
        Glide.with(view.getContext()).load(videoListView.getCoverUrl()).into(cover);
        support.setBackground(view.getContext().getDrawable(videoListView.getSupportResource()));
        BaseTool.setTextTypeFace(number, view.getContext().getAssets());
        number.setText(videoListView.getNumbers());
    }
}
