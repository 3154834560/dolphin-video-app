package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.domain.entity.Video;
import com.example.dolphin.infrastructure.adapter.VideoListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.ConcernButtonListener;
import com.example.dolphin.infrastructure.listeners.VideoAndImageListener;
import com.example.dolphin.infrastructure.structs.VideoListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2022/11/28 23:10
 */
public class AuthorInfoActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static Activity upActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_info_page);
        if (upActivity != null) {
            upActivity.finish();
        }
        upActivity = this;
        Intent intent = getIntent();
        String author = intent.getStringExtra(StringPool.AUTHOR_ID);
        initData(author);
        addListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (upActivity == this) {
            upActivity = null;
        }
    }

    private void addListener() {
        findViewById(R.id.user_head_portrait).setOnClickListener(new VideoAndImageListener(this, StringPool.IMAGE_CODE, StringPool.IMAGE_TYPE, R.layout.dialog_album3));
    }

    private void initData(String author) {
        initTopData(author);
        initListView(author);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi"})
    private void initTopData(String author) {
        UserService userService = new UserService();
        ConcernService concernService = new ConcernService();
        User user = userService.getBy(this, author);
        CircleImageView headPortrait = findViewById(R.id.user_head_portrait);
        TextView nick = findViewById(R.id.me_page_nick2);
        TextView userName = findViewById(R.id.me_page_user_name2);
        TextView sex = findViewById(R.id.me_page_sex2);
        TextView birthday = findViewById(R.id.me_page_birthday2);
        TextView introduction = findViewById(R.id.author_introduction);
        Button concernButton = findViewById(R.id.concern_button);
        BaseTool.setTextTypeFace(Arrays.asList(nick, userName, sex, birthday, introduction,
                findViewById(R.id.me_page_nick1), findViewById(R.id.me_page_sex1),
                findViewById(R.id.me_page_user_name1), findViewById(R.id.me_page_birthday1),
                findViewById(R.id.works)), getAssets());
        BaseTool.setButtonTypeFace(concernButton, getAssets());
        Glide.with(this).load(BaseTool.toStaticImagesUrl(user.getHeadPortraitName())).into(headPortrait);
        nick.setText(user.getNick());
        sex.setText(user.getSex().getSex());
        userName.setText(user.getUserName());
        if (user.getBirthday().toEpochSecond(ZoneOffset.ofHours(StringPool.EIGHT)) != StringPool.ZERO) {
            birthday.setText(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(user.getBirthday()));
        }
        if (user.getIntroduction() != null && !user.getIntroduction().trim().isEmpty()) {
            introduction.setText(user.getIntroduction());
        }
        if (concernService.isConcern(author)) {
            concernButton.setText(StringPool.FOLLOWED);
            concernButton.setBackground(getDrawable(R.drawable.shape_8));
        } else {
            concernButton.setText(StringPool.FOLLOW);
            concernButton.setBackground(getDrawable(R.drawable.shape_2));
        }
        concernButton.setOnClickListener(new ConcernButtonListener(this, concernButton, author));
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initListView(String author) {
        List<VideoListView> dataList = getVideos(author);
        TextView worksText = findViewById(R.id.works);
        worksText.setText(worksText.getText().toString() + StringPool.SPACE + dataList.size());
        ListView works = findViewById(R.id.author_works);
        works.setBackground(getDrawable(R.color.grey));
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
        VideoListViewAdapter listViewAdapter = new VideoListViewAdapter(this, listItems, R.layout.video_list_view_page, itemNames, layoutIds, true);
        works.setAdapter(listViewAdapter);
    }

    @SuppressLint("NewApi")
    private List<VideoListView> getVideos(String author) {
        VideoService videoService = new VideoService();
        List<VideoListView> dataList = new ArrayList<>();
        List<Video> videos = videoService.getAll(this, author);
        videos.forEach(video -> dataList.add(VideoListView.copy(this, video, videoService)));
        return dataList;
    }
}
