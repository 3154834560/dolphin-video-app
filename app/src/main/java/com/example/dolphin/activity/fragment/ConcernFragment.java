package com.example.dolphin.activity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dolphin.R;


/**
 * @author 王景阳
 * @date 2022/10/10 16:47
 */
public class ConcernFragment extends Fragment {
    private VideoView mVideoView;
    private Button playBtn, stopBtn;
    MediaController mMediaController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.concern_page, container, false);
        mVideoView = (VideoView) inflate.findViewById(R.id.videos);
        mMediaController = new MediaController(inflate.getContext());
        playBtn = (Button)inflate. findViewById(R.id.playbutton);
        stopBtn = (Button) inflate.findViewById(R.id.stopbutton);
        playBtn.setOnClickListener(new mClick());
        stopBtn.setOnClickListener(new mClick());
        return   inflate;
    }

    class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String uri = "http://43.139.159.32:8090/dolphin/video/1668601616162.mp4";
            mVideoView.setVideoURI(Uri.parse(uri));
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mMediaController);
            if (v == playBtn) {
                mVideoView.start();
            } else if (v == stopBtn) {
                mVideoView.stopPlayback();
            }
        }
    }
}
