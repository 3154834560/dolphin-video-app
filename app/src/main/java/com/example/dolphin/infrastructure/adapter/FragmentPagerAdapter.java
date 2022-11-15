package com.example.dolphin.infrastructure.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.List;

/**
 * @author 王景阳
 * @date 2022/10/16 11:23
 */
public class FragmentPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragments;


    public FragmentPagerAdapter(FragmentActivity activity, List<Fragment> fragments) {
        super(activity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}