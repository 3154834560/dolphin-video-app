package com.example.dolphin.activity.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dolphin.R;
import com.example.dolphin.infrastructure.consts.StringPool;


/**
 * @author 王景阳
 * @date 2022/10/10 16:47
 */
public class RewardFragment extends Fragment {

    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.reward_page, container, false);
        videoView = inflate.findViewById(R.id.load_animation);
        MediaController mediaController = new MediaController(inflate.getContext());
        String uri = StringPool.RESOURCE_PATH + R.raw.load;
        videoView.setVideoURI(Uri.parse(uri));
//        mediaController.setMediaPlayer(videoView);
//        videoView.setMediaController(mediaController);
        addListener();
        return inflate;
    }

    private void addListener() {
        videoView.stopPlayback();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 通过MediaPlayer设置循环播放
                mp.setLooping(true);
                // OnPreparedListener中的onPrepared方法是在播放源准备完成后回调的，所以可以在这里开启播放
                mp.start();
            }
        });
    }
}
