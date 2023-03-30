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
import com.example.dolphin.infrastructure.adapter.VideoListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.VideoListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 王景阳
 * @date 2022/10/10 16:47
 */
public class VideoListViewFragment extends Fragment {

    private View inflate;

    private final int marginTop;

    private final int type;

    private final int backgroundColor;

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

    @SuppressLint("NewApi")
    private void initData() {
        ListView listView = inflate.findViewById(R.id.concern_list_view);
        listView.setBackgroundColor(inflate.getContext().getColor(backgroundColor));
        listView.setTranslationY(marginTop);
        TextView textView = inflate.findViewById(R.id.concern_not_logging);
        BaseTool.setTextTypeFace(textView, inflate.getContext().getAssets());
        if (StringPool.CURRENT_USER != null) {
            textView.setVisibility(View.INVISIBLE);
            addAdapter(listView);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void addAdapter(ListView listView) {
        List<VideoListView> dataList = getData();
        List<Map<String, VideoListView>> listItems = new ArrayList<>();
        String[] itemNames = new String[]{"video1", "video2", "video3"};
        int[] layoutIds = new int[]{R.id.concern_video_1, R.id.concern_video_2, R.id.concern_video_3};
        List<List<Integer>> childLayoutIds = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(R.id.concern_video_image_1, R.id.concern_support_image_1, R.id.concern_support_number_1));
            add(Arrays.asList(R.id.concern_video_image_2, R.id.concern_support_image_2, R.id.concern_support_number_2));
            add(Arrays.asList(R.id.concern_video_image_3, R.id.concern_support_image_3, R.id.concern_support_number_3));
        }};
        for (int i = 0; i < dataList.size(); i += 3) {
            Map<String, VideoListView> listItem = new HashMap<>(3);
            listItem.put(itemNames[0], dataList.get(i).setChildLayoutIds(childLayoutIds.get(0)));
            listItem.put(itemNames[1], i + 1 >= dataList.size() ? new VideoListView().setChildLayoutIds(childLayoutIds.get(1)) : dataList.get(i + 1).setChildLayoutIds(childLayoutIds.get(1)));
            listItem.put(itemNames[2], i + 2 >= dataList.size() ? new VideoListView().setChildLayoutIds(childLayoutIds.get(2)) : dataList.get(i + 2).setChildLayoutIds(childLayoutIds.get(2)));
            listItems.add(listItem);
        }
        VideoListViewAdapter listViewAdapter = new VideoListViewAdapter(inflate.getContext(), listItems, R.layout.video_list_view_page, itemNames, layoutIds, false);
        listView.setAdapter(listViewAdapter);
    }

    @SuppressLint({"NewApi", "SetTextI18n"})
    private List<VideoListView> getData() {
        List<VideoListView> dataList = new ArrayList<>();
        VideoService videoService = new VideoService();
        ConcernService concernService = new ConcernService();
        CollectionService collectionService = new CollectionService();
        if (type == StringPool.CONCERN) {
            List<ConcernInput> concernList = concernService.getAllConcern(inflate.getContext());
            for (ConcernInput concern : concernList) {
                List<VideoInput> videos = videoService.getAll(inflate.getContext(), concern.getUserName());
                videos.forEach(video -> dataList.add(VideoListView.copy(inflate.getContext(), video, videoService)));
            }
        } else if (type == StringPool.WORKS) {
            List<VideoInput> videos = videoService.getAll(inflate.getContext(), StringPool.CURRENT_USER.getUserName());
            videos.forEach(video -> dataList.add(VideoListView.copy(inflate.getContext(), video, videoService)));
        } else if (type == StringPool.COLLECTION) {
            List<CollectionInput> inputs = collectionService.getAllCollection(inflate.getContext());
            inputs.forEach(collection -> dataList.add(VideoListView.copy(inflate.getContext(), collection, videoService)));
        }
        if (textView != null) {
            String str = textView.getText().toString();
            if (str.contains(StringPool.SPACE)) {
                str = str.substring(0, str.lastIndexOf(StringPool.SPACE));
            }
            textView.setText(str + StringPool.SPACE + dataList.size());
        }
        return dataList;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (inflate != null) {
            initData();
        }
    }

}
