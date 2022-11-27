package com.example.dolphin.activity.tools.hamster;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;

import java.util.Random;

/**
 * @author 王景阳
 * @date 2022/11/24 20:32
 */
public class HamsterActivity extends AppCompatActivity {
    private int i = 0;
    private ImageView mouse;
    private TextView info1;
    private Handler handler;
    public int[][] position = new int[][]{
            {256, 178}, {549, 178}, {860, 178},
            {206, 316}, {556, 316}, {871, 316},
            {195, 466}, {545, 466}, {904, 466}
    };

    //位置的坐标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamster_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mouse = (ImageView) findViewById(R.id.imageView1);
        info1 = findViewById(R.id.info);
        info1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getRawX();
                        float y = event.getRawY();
                        Log.i("x:" + x, "y:" + y);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

                int index;
                if (msg.what == 0x101) {
                    index = msg.arg1;
                    mouse.setX(position[index][0]);
                    mouse.setY(position[index][1]);
                    mouse.setVisibility(View.VISIBLE);
                }
                super.handleMessage(msg);
            }
        };
        // 创建线程
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    index = new Random().nextInt(position.length);
                    Message m = handler.obtainMessage();
                    m.what = 0x101;
                    m.arg1 = index;
                    handler.sendMessage(m);
                    try {
                        Thread.sleep(new Random().nextInt(500) + 500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();

        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE);
                i++;
                Toast.makeText(HamsterActivity.this, "打到[ " + i + " ]只地鼠！",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}