package com.example.dolphin.infrastructure.tool;

import android.content.Context;
import android.widget.ListView;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.adapter.VideoListViewAdapter;
import com.example.dolphin.infrastructure.structs.VideoListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王景阳
 * @date 2023/3/30 21:17
 */
public class AdapterTool {

    public static void addVideoListViewAdapter(Context context, ListView listView, List<Map<String, VideoListView>> listItems, List<VideoListView> dataList) {
        String[] itemNames = new String[]{"video1", "video2", "video3"};
        int[] layoutIds = new int[]{R.id.concern_video_1, R.id.concern_video_2, R.id.concern_video_3};
        videoListVideoInitData(listItems, dataList);
        VideoListViewAdapter listViewAdapter = new VideoListViewAdapter(context, listItems, R.layout.video_list_view_page, itemNames, layoutIds);
        listView.setAdapter(listViewAdapter);
    }

    public static void videoListVideoInitData(List<Map<String, VideoListView>> listItems, List<VideoListView> dataList) {
        String[] itemNames = new String[]{"video1", "video2", "video3"};
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
    }
}
