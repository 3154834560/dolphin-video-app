package com.example.dolphin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.activity.fragment.VideoListViewFragment;
import com.example.dolphin.application.service.ImageService;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.adapter.FragmentPagerAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.HomePageTextListener;
import com.example.dolphin.infrastructure.listeners.HomePageViewListener;
import com.example.dolphin.infrastructure.listeners.JumpIconListener;
import com.example.dolphin.infrastructure.listeners.SlideTextListener;
import com.example.dolphin.infrastructure.listeners.VideoAndImageListener;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.util.RealPathFromUriUtil;

import java.io.File;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.SneakyThrows;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 王景阳
 * @date 2022/11/16 19:51
 */
public class MePageActivity extends AppCompatActivity {

    private final ImageService imageService = new ImageService();

    private final UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_page);
        initData();
        addListener();
    }


    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case StringPool.IMAGE_CODE * StringPool.ALBUM_CODE:
                updateHeadPortraitUrl(data.getData());
                break;
            case StringPool.IMAGE_CODE * StringPool.CAMERA_CODE:
                updateHeadPortraitUrl(data.getData());
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    private void updateHeadPortraitUrl(Uri uri) {
        String filePath = RealPathFromUriUtil.getFilePathByUri(this, uri);
        if (filePath == null) {
            BaseTool.shortToast(this, StringPool.UPLOAD_FAIL);
            return;
        }
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        String msg = imageService.uploadImage(this, image);
        StringPool.CURRENT_USER = userService.getBy(this, StringPool.CURRENT_USER.getUserName());
        userService.writeLoginInfo(this, StringPool.CURRENT_USER);
        CircleImageView headPortrait = findViewById(R.id.user_head_portrait);
        Glide.with(this).load(StringPool.CURRENT_USER.getHeadPortraitUrl()).into(headPortrait);
        BaseTool.shortToast(this, msg);
    }

    private void initData() {
        initTopData();
        initBottomData();
        initViewPager2();
    }

    private void addListener() {
        findViewById(R.id.user_head_portrait).setOnClickListener(new VideoAndImageListener(this, StringPool.IMAGE_CODE, StringPool.IMAGE_TYPE, R.layout.dialog_album2));
        findViewById(R.id.me_page_introduction).setOnClickListener(v -> startActivity(new Intent(MePageActivity.this, PersonIntroPage.class)));
        findViewById(R.id.modify_info).setOnClickListener(v -> startActivity(new Intent(MePageActivity.this, RegisterPageActivity.class).putExtra(StringPool.TYPE, StringPool.UPDATE)));
    }

    private void initViewPager2() {
        ViewPager2 viewPager2 = findViewById(R.id.me_view_pager2);
        List<TextView> texts = Arrays.asList(findViewById(R.id.works), findViewById(R.id.follow), findViewById(R.id.collection));
        List<View.OnClickListener> listeners = Arrays.asList(new SlideTextListener(viewPager2, 0), new SlideTextListener(viewPager2, 1), new SlideTextListener(viewPager2, 2));
        List<Fragment> fragments = Arrays.asList(new VideoListViewFragment(texts.get(0), 0, StringPool.WORKS, R.color.grey), new VideoListViewFragment(texts.get(1), 0, StringPool.CONCERN, R.color.grey), new VideoListViewFragment(texts.get(2), 0, StringPool.COLLECTION, R.color.grey));
        BaseTool.addOnClickListener(texts, listeners);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(this, fragments);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.registerOnPageChangeCallback(new HomePageViewListener(getResources(), texts, R.drawable.underline3));
    }

    @SuppressLint("NewApi")
    private void initTopData() {
        User user = StringPool.CURRENT_USER;
        CircleImageView headPortrait = findViewById(R.id.user_head_portrait);
        TextView nick = findViewById(R.id.me_page_nick2);
        TextView userName = findViewById(R.id.me_page_user_name2);
        TextView sex = findViewById(R.id.me_page_sex2);
        TextView birthday = findViewById(R.id.me_page_birthday2);
        TextView phone = findViewById(R.id.me_page_phone2);
        TextView introduction = findViewById(R.id.me_page_introduction);
        BaseTool.setTextTypeFace(Arrays.asList(nick, userName, sex, birthday, introduction, phone,
                findViewById(R.id.me_page_nick1), findViewById(R.id.me_page_sex1),
                findViewById(R.id.me_page_user_name1), findViewById(R.id.me_page_birthday1),
                findViewById(R.id.works), findViewById(R.id.follow), findViewById(R.id.collection),
                findViewById(R.id.me_page_phone1)), getAssets());
        BaseTool.setButtonTypeFace((Button) findViewById(R.id.modify_info), getAssets());
        Glide.with(this).load(user.getHeadPortraitUrl()).into(headPortrait);
        nick.setText(user.getNick());
        sex.setText(user.getSex().getSex());
        userName.setText(user.getUserName());
        if (user.getBirthday().toEpochSecond(ZoneOffset.ofHours(StringPool.EIGHT)) != StringPool.ZERO) {
            birthday.setText(DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(user.getBirthday()));
        }
        if (user.getIntroduction() != null && !user.getIntroduction().trim().isEmpty()) {
            introduction.setText(user.getIntroduction());
        }
        phone.setText(user.getPhone());
    }

    private void initBottomData() {
        TextView homePageText = findViewById(R.id.home_page);
        ImageView uploadImage = findViewById(R.id.upload_video_icon);
        TextView meText = findViewById(R.id.me);
        BaseTool.setTextTypeFace(homePageText, getAssets());
        BaseTool.setTextTypeFace(meText, getAssets());
        homePageText.setOnClickListener(new HomePageTextListener(this, MePageActivity.class, HomePageActivity.class, null));
        meText.setOnClickListener(new JumpIconListener(this, MePageActivity.class, MePageActivity.class));
        uploadImage.setOnClickListener(new JumpIconListener(this, LoginPageActivity.class, UploadPageActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTopData();
    }
}
