package com.example.dolphin.infrastructure.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * @author 王景阳
 * @date 2022/11/13 21:21
 */
public class HintLoginTextListener implements View.OnClickListener {

    private final Context context;

    private final Class<?> nextClass;



    public HintLoginTextListener(Context context,Class<?> nextClass) {
        this.context = context;
        this.nextClass=nextClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,nextClass);
        context.startActivity(intent);
    }

}
