package com.example.dolphin.infrastructure.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolphin.infrastructure.consts.StringPool;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王景阳
 * @date 2022/11/12 16:40
 */
public class BaseTool {

    public static void shortToast(Context context, String msg) {
        Activity activity = (Activity) context;
        activity.runOnUiThread(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }

    public static void longToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void setTextTypeFace(TextView textView, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        textView.setTypeface(typeface);
    }

    @SuppressLint("NewApi")
    public static void setTextTypeFace(List<TextView> textViews, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        textViews.forEach(textView -> textView.setTypeface(typeface));
    }

    public static void setEditTextTypeFace(EditText editText, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        editText.setTypeface(typeface);
    }

    @SuppressLint("NewApi")
    public static void setEditTextTypeFace(List<EditText> editText, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        editText.forEach(textView -> textView.setTypeface(typeface));
    }

    public static void setButtonTypeFace(Button button, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        button.setTypeface(typeface);
    }

    @SuppressLint("NewApi")
    public static void setButtonTypeFace(List<Button> buttons, AssetManager manager) {
        Typeface typeface = Typeface.createFromAsset(manager, StringPool.FONT_TYPE_FACE);
        buttons.forEach(textView -> textView.setTypeface(typeface));
    }

    public static void addOnClickListener(TextView textView, View.OnClickListener listener) {
        textView.setOnClickListener(listener);
    }

    public static void addOnClickListener(List<TextView> textViews, List<View.OnClickListener> listeners) {
        int len = Math.min(textViews.size(), listeners.size());
        for (int i = 0; i < len; i++) {
            textViews.get(i).setOnClickListener(listeners.get(i));
        }
    }

    @SuppressLint("NewApi")
    public static void clearCache(Context context) {
        File[] cacheFiles = context.getCacheDir().listFiles();
        if (cacheFiles == null) {
            return;
        }
        for (File file : cacheFiles) {
            File[] files = file.listFiles();
            if (files != null) {
                Arrays.stream(files).forEach(f -> f.delete());
            }
        }
    }

}
