package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.application.dto.input.VideoInput;
import com.example.dolphin.application.service.ConcernService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.application.service.VideoService;
import com.example.dolphin.domain.model.User;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.ConcernButtonListener;
import com.example.dolphin.infrastructure.listeners.VideoAndImageListener;
import com.example.dolphin.infrastructure.structs.VideoListView;
import com.example.dolphin.infrastructure.tool.AdapterTool;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.DateTimeTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者信息页面
 *
 * @author 王景阳
 * @date 2022/11/28 23:10
 */
public class AuthorInfoActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static Activity upActivity = null;

    private String author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_info_page);
        closeUpPage();
        author = getIntent().getStringExtra(StringPool.AUTHOR_ID);
        initData();
        addListener();
    }

    private void closeUpPage() {
        if (upActivity != null) {
            upActivity.finish();
        }
        upActivity = this;
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

    private void initData() {
        initTopData();
        initListView();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "NewApi"})
    private void initTopData() {
        UserService userService = new UserService();
        ConcernService concernService = new ConcernService();
        User user = userService.getBy(this, author);
        CircleImageView headPortrait = findViewById(R.id.user_head_portrait);
        Glide.with(this).load(BaseTool.toStaticImagesUrl(user.getHeadPortraitName())).into(headPortrait);
        setTextTypeFace();
        setTextContent(user);
        initConcernButton(concernService, author);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initConcernButton(ConcernService concernService, String author) {
        Button concernButton = findViewById(R.id.concern_button);
        BaseTool.setButtonTypeFace(concernButton, getAssets());
        if (concernService.isConcern(author)) {
            concernButton.setText(StringPool.FOLLOWED);
            concernButton.setBackground(getDrawable(R.drawable.shape_8));
        } else {
            concernButton.setText(StringPool.FOLLOW);
            concernButton.setBackground(getDrawable(R.drawable.shape_2));
        }
        concernButton.setOnClickListener(new ConcernButtonListener(this, concernButton, author));
    }

    private void setTextTypeFace() {
        TextView nick = findViewById(R.id.me_page_nick2);
        TextView userName = findViewById(R.id.me_page_user_name2);
        TextView sex = findViewById(R.id.me_page_sex2);
        TextView birthday = findViewById(R.id.me_page_birthday2);
        TextView introduction = findViewById(R.id.author_introduction);
        BaseTool.setTextTypeFace(Arrays.asList(nick, userName, sex, birthday, introduction,
                findViewById(R.id.me_page_nick1), findViewById(R.id.me_page_sex1),
                findViewById(R.id.me_page_user_name1), findViewById(R.id.me_page_birthday1),
                findViewById(R.id.works)), getAssets());

    }

    private void setTextContent(User user) {
        TextView nick = findViewById(R.id.me_page_nick2);
        TextView userName = findViewById(R.id.me_page_user_name2);
        TextView sex = findViewById(R.id.me_page_sex2);
        TextView birthday = findViewById(R.id.me_page_birthday2);
        TextView introduction = findViewById(R.id.author_introduction);
        nick.setText(user.getNick());
        sex.setText(user.getSex().getSex());
        userName.setText(user.getUserName());
        if (DateTimeTool.toLong(user.getBirthday()) != StringPool.ZERO) {
            birthday.setText(DateTimeTool.dateToString(user.getBirthday(), DateTimeTool.DateFormat.SIX));
        }
        if (user.getIntroduction() != null && !user.getIntroduction().trim().isEmpty()) {
            introduction.setText(user.getIntroduction());
        }
    }


    private ListView works;
    /**
     * 保存listview数据
     */
    private final List<Map<String, VideoListView>> listItems = new ArrayList<>();

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initListView() {
        List<VideoListView> dataList = getVideos(author);
        TextView worksText = findViewById(R.id.works);
        worksText.setText(worksText.getText().toString() + StringPool.SPACE + dataList.size());
        works = findViewById(R.id.author_works);
        works.setBackground(getDrawable(R.color.grey));
        AdapterTool.addVideoListViewAdapter(this, works, listItems, dataList);
    }

    /**
     * 回调时刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        listItems.clear();
        AdapterTool.videoListVideoInitData(listItems, getVideos(author));
        works.invalidateViews();
    }

    @SuppressLint("NewApi")
    private List<VideoListView> getVideos(String author) {
        VideoService videoService = new VideoService();
        List<VideoInput> videos = videoService.getAll(this, author);
        return videos.stream().map(video -> VideoListView.copy(this, video, videoService)).collect(Collectors.toList());
    }
}
