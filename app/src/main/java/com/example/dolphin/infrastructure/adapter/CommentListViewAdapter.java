package com.example.dolphin.infrastructure.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.dolphin.infrastructure.consts.StringPool;
import com.example.dolphin.infrastructure.listeners.CommentListener;
import com.example.dolphin.infrastructure.structs.CommentListView;
import com.example.dolphin.infrastructure.tool.BaseTool;
import com.example.dolphin.infrastructure.tool.DateTimeTool;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author 王景阳
 * @date 2023/3/28 21:54
 */
public class CommentListViewAdapter extends SimpleAdapter {

    private final LayoutInflater mInflater;

    private List<Map<String, CommentListView>> mData;

    private final int mResource;

    private final String[] mFrom;

    private final Context context;

    private final int[] mTo;

    private final String videoId;

    public CommentListViewAdapter(Context context, List<Map<String, CommentListView>> data, int resource, String[] from, int[] to, String videoId) {
        super(context, data, resource, from, to);
        this.context = context;
        this.mData = data;
        this.mResource = resource;
        this.mFrom = from;
        this.mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.videoId = videoId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void notifyDataSetChanged() {
        //刷新数据源
        mData.clear();
        ;
        mData.addAll(CommentListener.getCommentData(context, videoId));
        //通知 ListView 刷新内容
        super.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    private void bindView(int position, View view) {
        Map<String, CommentListView> dataSet = mData.get(position);
        if (dataSet == null) {
            return;
        }
        for (Map.Entry<String, CommentListView> m : dataSet.entrySet()) {
            CommentListView commentListView = m.getValue();
            CircleImageView headPortrait = view.findViewById(mTo[0]);
            Glide.with(context).load(commentListView.getHeadPortraitUrl()).into(headPortrait);
            TextView nick = view.findViewById(mTo[1]);
            BaseTool.setTextTypeFace(nick, context.getAssets());
            nick.setText(commentListView.getNick());
            TextView createAt = view.findViewById(mTo[2]);
            BaseTool.setTextTypeFace(createAt, context.getAssets());
            createAt.setText(DateTimeTool.dateToString(commentListView.getCreateAt(), DateTimeTool.DateFormat.FOUR));
            TextView content = view.findViewById(mTo[3]);
            BaseTool.setTextTypeFace(content, context.getAssets());
            content.setText(commentListView.getContent());
            Intent intent = new Intent(context, commentListView.getNextClass());
            intent.putExtra(StringPool.AUTHOR_ID, commentListView.getUserName());
            headPortrait.setOnClickListener(v -> context.startActivity(intent));
            nick.setOnClickListener(v -> context.startActivity(intent));
        }
    }
}
