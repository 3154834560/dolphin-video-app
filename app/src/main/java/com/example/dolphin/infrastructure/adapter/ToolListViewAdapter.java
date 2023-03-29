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

import com.example.dolphin.infrastructure.structs.ToolListView;
import com.example.dolphin.infrastructure.tool.BaseTool;

import java.util.List;
import java.util.Map;

/**
 * @author 王景阳
 * @date 2022/11/23 19:32
 */
public class ToolListViewAdapter extends SimpleAdapter {

    private final LayoutInflater mInflater;

    private final List<Map<String, ToolListView>> mData;

    private final int mResource;

    private final String[] mFrom;

    private final int[] mTo;

    public ToolListViewAdapter(Context context, List<Map<String, ToolListView>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mData = data;
        this.mResource = resource;
        this.mFrom = from;
        this.mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        Map<String, ToolListView> dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        int count = mTo.length;
        for (int i = 0; i < count; i++) {
            View v = view.findViewById(mTo[i]);
            ToolListView toolListView = dataSet.get(mFrom[i]);
            List<Integer> childLayoutIds = toolListView.getChildLayoutIds();
            ImageView imageView = v.findViewById(childLayoutIds.get(0));
            TextView textView = v.findViewById(childLayoutIds.get(1));
            if (toolListView.getNextClass() == null) {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                continue;
            }
            imageView.setBackground(view.getContext().getDrawable(toolListView.getImageId()));
            textView.setText(toolListView.getToolName());
            BaseTool.setTextTypeFace(textView, view.getContext().getAssets());
            v.setOnClickListener(vv -> v.getContext().startActivity(new Intent(v.getContext(), toolListView.getNextClass())));
        }
    }
}
