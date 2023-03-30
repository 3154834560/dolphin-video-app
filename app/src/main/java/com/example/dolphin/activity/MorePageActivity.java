package com.example.dolphin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dolphin.R;
import com.example.dolphin.activity.tools.bmi.BMIActivity;
import com.example.dolphin.activity.tools.calculate.CalculateActivity;
import com.example.dolphin.activity.tools.guessNumber.GussNumberGame;
import com.example.dolphin.activity.tools.hamster.HamsterActivity;
import com.example.dolphin.activity.tools.minesweeper.MinesweeperActivity;
import com.example.dolphin.activity.tools.notepad.NotePadActivity;
import com.example.dolphin.activity.tools.translate.TranslateActivity;
import com.example.dolphin.application.service.UserService;
import com.example.dolphin.domain.entity.User;
import com.example.dolphin.infrastructure.adapter.ToolListViewAdapter;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.structs.ToolListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2022/11/19 19:45
 */
public class MorePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_page);
        initData();
        initListView();
    }

    private void initListView() {
        ListView listView = findViewById(R.id.more_function);
        List<ToolListView> dataList = new ArrayList<ToolListView>() {{
            add(new ToolListView().setImageId(R.drawable.calculator_icon).setToolName("计算器").setNextClass(CalculateActivity.class));
            add(new ToolListView().setImageId(R.drawable.translate_icon).setToolName("翻译").setNextClass(TranslateActivity.class));
            add(new ToolListView().setImageId(R.drawable.notpad_icon).setToolName("记事本").setNextClass(NotePadActivity.class));
            add(new ToolListView().setImageId(R.drawable.bmi_icon).setToolName("BMI").setNextClass(BMIActivity.class));
            add(new ToolListView().setImageId(R.drawable.hamster_icon).setToolName("打地鼠").setNextClass(HamsterActivity.class));
            add(new ToolListView().setImageId(R.drawable.minesweeper).setToolName("扫雷").setNextClass(MinesweeperActivity.class));
            add(new ToolListView().setImageId(R.drawable.guess_number_icon).setToolName("猜数").setNextClass(GussNumberGame.class));
        }};
        addAdapter(listView, dataList);
    }

    private void addAdapter(ListView listView, List<ToolListView> dataList) {
        List<Map<String, ToolListView>> listItems = new ArrayList<>();
        String[] itemNames = new String[]{"tool1", "tool2", "tool3"};
        int[] layoutIds = new int[]{R.id.tool_1, R.id.tool_2, R.id.tool_3};
        List<List<Integer>> childLayoutIds = new ArrayList<List<Integer>>() {{
            add(Arrays.asList(R.id.tool_image_1, R.id.tool_name_1));
            add(Arrays.asList(R.id.tool_image_2, R.id.tool_name_2));
            add(Arrays.asList(R.id.tool_image_3, R.id.tool_name_3));
        }};
        for (int i = 0; i < dataList.size(); i += 3) {
            Map<String, ToolListView> listItem = new HashMap<>(3);
            listItem.put(itemNames[0], dataList.get(i).setChildLayoutIds(childLayoutIds.get(0)));
            listItem.put(itemNames[1], i + 1 >= dataList.size() ? new ToolListView().setChildLayoutIds(childLayoutIds.get(1)) : dataList.get(i + 1).setChildLayoutIds(childLayoutIds.get(1)));
            listItem.put(itemNames[2], i + 2 >= dataList.size() ? new ToolListView().setChildLayoutIds(childLayoutIds.get(2)) : dataList.get(i + 2).setChildLayoutIds(childLayoutIds.get(2)));
            listItems.add(listItem);
        }
        ToolListViewAdapter listViewAdapter = new ToolListViewAdapter(this, listItems, R.layout.tool_list_view_page, itemNames, layoutIds);
        listView.setAdapter(listViewAdapter);
    }

    private void initData() {
        User user = StringPool.CURRENT_USER;
        LinearLayout linear = findViewById(R.id.more_linear);
        CircleImageView headPortrait = findViewById(R.id.more_head);
        TextView nick = findViewById(R.id.more_nick);
        Button logOut = findViewById(R.id.log_out);

        BaseTool.setTextTypeFace(nick, getAssets());
        BaseTool.setButtonTypeFace(logOut, getAssets());

        linear.setOnClickListener(v -> {
            startActivity(new Intent(MorePageActivity.this, user == null ? LoginPageActivity.class : MePageActivity.class));
            finish();
        });
        if (user != null) {
            Glide.with(this).load(BaseTool.toStaticImagesUrl(user.getHeadPortraitName())).into(headPortrait);
            nick.setText(user.getNick());
            logOut.setOnClickListener(v -> {
                UserService userService = new UserService();
                userService.quitLogin(MorePageActivity.this);
                StringPool.CURRENT_USER = null;
                StringPool.CONCERN_LIST = null;
                StringPool.COLLECTION_INPUT_LIST = null;
                finish();
            });
        } else {
            nick.setText(StringPool.NOT_LOGIN);
            logOut.setOnClickListener(v -> {
                BaseTool.shortToast(MorePageActivity.this, StringPool.CURRENT_NOT_LOGIN);
            });
        }
    }
}
