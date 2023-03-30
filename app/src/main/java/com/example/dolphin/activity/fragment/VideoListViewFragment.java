package com.example.dolphin.activity.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dolphin.R;
import com.example.dolphin.application.dto.input.CollectionInput;
import com.example.dolphin.application.dto.input.ConcernInput;
import com.example.dolphin.application.dto.input.VideoInput;
import com.example.dolphin.application.service.CollectionService;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.VideoListView;
import com.example.dolphin.infrastructure.tool.AdapterTool;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 使用ListView的形式显示视频
 * type表示当前ListView显示的界面类型
 *
 * @author 王景阳
 * @date 2022/10/10 16:47
 */
public class VideoListViewFragment extends Fragment {

    private View inflate;

    /**
     * listview离顶部的距离
     */
    private final int marginTop;

    /**
     * 0 表示是“我的作品”
     * 1 表示是“关注”
     * 2 表示是“收藏”
     */
    private final int type;

    /**
     * listview背景颜色
     */
    private final int backgroundColor;

    /**
     * listview上面的文字
     */
    private final TextView textView;

    public VideoListViewFragment(TextView textView, int marginTop, int type, int backgroundColor) {
        this.textView = textView;
        this.marginTop = marginTop;
        this.type = type;
        this.backgroundColor = backgroundColor;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.video_list_video_page, container, false);
        initData();
        return inflate;
    }

    private ListView listView;
    /**
     * 保存listview数据
     */
    private final List<Map<String, VideoListView>> listItems = new ArrayList<>();

    @SuppressLint("NewApi")
    private void initData() {
        listView = inflate.findViewById(R.id.concern_list_view);
        listView.setBackgroundColor(inflate.getContext().getColor(backgroundColor));
        listView.setTranslationY(marginTop);
        TextView textView = inflate.findViewById(R.id.concern_not_logging);
        BaseTool.setTextTypeFace(textView, inflate.getContext().getAssets());
        if (StringPool.CURRENT_USER != null) {
            textView.setVisibility(View.INVISIBLE);
            AdapterTool.addVideoListViewAdapter(inflate.getContext(), listView, listItems, getData());
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint({"NewApi", "SetTextI18n"})
    private List<VideoListView> getData() {
        List<VideoListView> dataList = new ArrayList<>();
        if (type == StringPool.CONCERN) {
            addConcernTypeData(dataList);
        } else if (type == StringPool.WORKS) {
            addWorksTypeData(dataList);
        } else if (type == StringPool.COLLECTION) {
            addCollectionTypeData(dataList);
        }
        initTextView(textView, dataList);
        return dataList;
    }

    /**
     * 回调时刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        if (inflate != null) {
            listItems.clear();
            AdapterTool.videoListVideoInitData(listItems, getData());
            listView.invalidateViews();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initTextView(TextView textView, List<VideoListView> dataList) {
        if (textView != null) {
            String str = textView.getText().toString();
            if (str.contains(StringPool.SPACE)) {
                str = str.substring(0, str.lastIndexOf(StringPool.SPACE));
            }
            textView.setText(str + StringPool.SPACE + dataList.size());
        }
    }

    @SuppressLint("NewApi")
    private void addCollectionTypeData(List<VideoListView> dataList) {
        CollectionService collectionService = new CollectionService();
        VideoService videoService = new VideoService();
        List<CollectionInput> inputs = collectionService.getAllCollection(inflate.getContext());
        inputs.forEach(collection -> dataList.add(VideoListView.copy(inflate.getContext(), collection, videoService)));
    }

    @SuppressLint("NewApi")
    private void addWorksTypeData(List<VideoListView> dataList) {
        VideoService videoService = new VideoService();
        List<VideoInput> videos = videoService.getAll(inflate.getContext(), StringPool.CURRENT_USER.getUserName());
        videos.forEach(video -> dataList.add(VideoListView.copy(inflate.getContext(), video, videoService)));
    }

    @SuppressLint("NewApi")
    private void addConcernTypeData(List<VideoListView> dataList) {
        ConcernService concernService = new ConcernService();
        VideoService videoService = new VideoService();
        List<ConcernInput> concernList = concernService.getAllConcern(inflate.getContext());
        for (ConcernInput concern : concernList) {
            List<VideoInput> videos = videoService.getAll(inflate.getContext(), concern.getUserName());
            videos.forEach(video -> dataList.add(VideoListView.copy(inflate.getContext(), video, videoService)));
        }
    }
}
