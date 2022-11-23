package com.example.dolphin.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.tool.BaseTool;

/**
 * @author 王景阳
 * @date 2022/11/23 20:34
 */
public class CollectionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.collection_page, container, false);
        TextView textView = inflate.findViewById(R.id.collection_text);
        BaseTool.setTextTypeFace(textView, inflate.getContext().getAssets());
        return inflate;
    }
}
